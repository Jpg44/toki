<script setup>
import { computed, onMounted, onUnmounted, reactive, ref,h,shallowReactive,watch } from 'vue'
import SvgIcon from '@jamescoyle/vue-icon'
import { mdiMicrophone, mdiVideo, mdiMonitorShare, mdiRadioboxMarked, mdiEmoticonOutline, mdiCog, mdiChatProcessingOutline } from '@mdi/js'
import RoomGameModal from '../modal/RoomGameModal.vue'
import RoomChatComponent from '@/components/room_components/RoomChatComponent.vue'
import { useWebSocket } from '@vueuse/core'
import kurentoUtil from 'kurento-utils';
import RoomUserView from '@/components/room_components/RoomUserView.vue'
import { toRaw } from 'vue'
// import { useTokiRoomStore } from '@/stores/tokiroom'
//import user from '@/stores/user'
import { watchEffect } from 'vue'
import { watchPostEffect } from 'vue'
import ScreenRecord from '@/components/screenRecord/ScreenRecord.vue'
import * as Tone from 'tone';
////
// const testRooms = useTokiRoomStore();
//JiHoon Jung <mudokja@gmail.com>
const props = defineProps({
  userInfo: Object,
  roomInfo: Object,
  ifnum:String
})
const userInfo = ref(props.userInfo)
const roomInfo = ref(props.roomInfo)

const recordedVideoElement = ref(null);
const previewCanvas = ref(null); // 미리 보기용 캔버스 요소의 참조
let previewCtx=ref('');


const tokiRoomMembers = ref([])
const tokiRoomVideo=ref({})
const wsUrl='wss://i10b205.p.ssafy.io/ws/room'
// const wsUrl='ws://localhost:8081/ws/room'
const { status, data, send, open, close } = useWebSocket(wsUrl, {
  heartbeat: {
    message: JSON.stringify({id:"ping",value:"ping"}),
    interval: 50000,
    pongTimeout:30000,
  },
  onConnected:()=> {
    console.log("연결됨")
  },
  onMessage: (ws,message) => {
    let parsedMessage = JSON.parse(message.data);
	switch (parsedMessage.id) {
    case 'existingParticipants':{
      
      createMember(userInfo.value.name);
      const receive=watchPostEffect(()=>{
        if(tokiRoomVideo.value[userInfo.value.name]){
          onExistingParticipants(parsedMessage)
        }
      })
      receive();
    }
      
		break;
    case 'newParticipantArrived':
		onNewParticipant(parsedMessage);
		break;
	case 'participantLeft':
		onParticipantLeft(parsedMessage);
		break;
	case 'receiveVideoAnswer':
		receiveVideoResponse(parsedMessage)
      break;
  case 'pong':
      break;

	case 'iceCandidate':
		toRaw(tokiRoomMembers.value.find(v=>v.name===parsedMessage.name)).rtcPeer.addIceCandidate(parsedMessage.candidate, function (error) {
      if (error) {
        console.error("Error adding candidate: " + error);
        return;
        }
    });
    break;
	default:
		console.error('Unrecognized message', parsedMessage);
	}
  }
})
const sendMessage=(message)=>{
    const msg=JSON.stringify(message)
    // console.dir(`message : ${msg}`)
    send(msg)
}

function register() {//room관련 정보 가져옴

	let message = {
		id : 'joinRoom',
		name : userInfo.value.name,
		room : roomInfo.value.roomPk,
	}
	sendMessage(message);
}
const start=()=>{
    register()
}
// watch(tokiRoomVideo, (newVal) => {
//   console.log("변화!",newVal.value)
// })


function onParticipantLeft(request) {//나머지 참가자 내보내기
	console.log('Participant ' + request.name + ' left');
	const participant =tokiRoomMembers.value.find(v=>v.name===request.name);//참가자 배열 받기
  participant.rtcPeer.dispose();
  tokiRoomMembers.value=tokiRoomMembers.value.filter(v => v.name !== request.name)
  console.log(tokiRoomMembers.value)
  delete tokiRoomVideo.value[request.name]
}

function receiveVideoResponse(result) {//참가자 비디오 응답 오는지 확인
  // console.log("받음")
  // console.dir(toRaw(tokiRoomMembers.value.find(v=>v.name===result.name).rtcPeer.processAnswer))
	toRaw(tokiRoomMembers.value.find(v=>v.name===result.name).rtcPeer).processAnswer (result.sdpAnswer, function (error) {
		if (error) {
      console.log("응답에러")
      return console.error (error);
    }
	});
}

function receiveVideo(sender) {
	let video = toRaw(tokiRoomVideo.value[sender]);
  const participant = tokiRoomMembers.value.find(v => v.name === sender)
    participant.video=video
    
	let options = {
    remoteVideo: video,
      onicecandidate:participant.onicecandidate.bind(participant)
      }
	participant.rtcPeer =new kurentoUtil.WebRtcPeer.WebRtcPeerRecvonly(options,
		function (error) {
            if(error) {
                return console.error(error);
            }
            this.generateOffer (participant.offerToReceiveVideo.bind(toRaw(participant)));
	});
}
const mic =ref();
// 마이크 입력을 처리하고 피치 쉬프트를 적용하는 함수
const startAudioProcessing=async()=> {
  await Tone.start(); // 사용자의 상호작용에 응답하여 Tone.js 오디오 컨텍스트를 시작합니다.


  // PitchShift 인스턴스를 생성하고 마이크 입력에 연결합니다.
  const pitchShift = new Tone.PitchShift({
    pitch: 12, // 피치를 올릴 반음의 수, 예: 12는 한 옥타브 상승을 의미합니다.
  }).toDestination();

  // 마이크의 출력을 PitchShift로 라우팅합니다.
  // 노이즈 필터링을 위한 저역 통과 필터 설정
const lowPassFilter = new Tone.Filter({
  frequency: 1500, // 저역 통과 필터의 컷오프 주파수를 설정합니다. 이 값은 조정 가능합니다.
  type: 'lowpass', // 필터 유형을 'lowpass'로 설정하여 고주파수 노이즈를 줄입니다.
}).toDestination();

// 마이크 입력을 저역 통과 필터에 연결한 후 피치 쉬프트 처리를 합니다.
mic.value.connect(lowPassFilter).connect(pitchShift);
}

let stream =ref();
let cha =ref('1');
const newStream=async()=>{
  if(cha.value=='1'){
stream.value=await navigator.mediaDevices.getUserMedia({ video: true , audio:true }) 
  }
  else if(cha.value=='2'){
    stream.value=await navigator.mediaDevices.getDisplayMedia({ video: true }) 
  }
}

const onExistingParticipants = async(msg) => {
    const constraints = {
    audio: true,
    video: {
      mandatory: {
        maxWidth: 320,
        maxFrameRate: 20,
        minFrameRate: 15
      }
    }
  }
  console.log(userInfo.value.name + " registered in room " + roomInfo.value.roomPk);
await newStream();
  const participant = tokiRoomMembers.value.find(v=>v.name===userInfo.value.name)
  participant.video=tokiRoomVideo.value[userInfo.value.name]
	let video = participant.video;
  /////////////////////////////////////////////////////////
 //await navigator.mediaDevices.getUserMedia({ video: true , audio:true }) // 다른 비디오 소스로 변경하려면 적절한 constraints를 전달합니다.
    const videoElement = document.createElement('video');
    videoElement.srcObject = stream.value; 
    // 마이크 입력을 생성합니다.
   mic.value = new Tone.UserMedia()
  
  // 마이크 입력을 활성화합니다.
   mic.value.open().then(()=>{
    console.dir(mic.value.stream);
   }).catch(()=>{
    console.log("sasf");
   });
     
  //  let audioStream =await navigator.mediaDevices.getUserMedia({ audio: true })
  //       // 오디오 스트림을 얻었습니다. 이제 이를 사용할 수 있습니다.
  //       console.log("오디오 스트림을 얻었습니다:", audioStream);

  //       // Web Audio API를 사용하여 오디오 스트림의 볼륨을 조절합니다.
  //       const audioContext = new AudioContext();
  //       const source = audioContext.createMediaStreamSource(audioStream);
  //       const gainNode = audioContext.createGain();
  //       const filter = audioContext.createBiquadFilter();//필터
  //       const highpassFilter = audioContext.createBiquadFilter();
        // 필터 유형을 피치 이동으로 설정
        //filter.type = "peaking";1
        // 중심 주파수 설정 (1000Hz를 기준으로 피치를 변경)
       // filter.frequency.value = 3000;
 
        // 피치 조절 (음성의 높낮이를 변경)
        //filter.detune.value = -100; // 100 cents (1 반음)만큼 올림
        // filter.detune.value = -100; // 100 cents (1 반음)만큼 내림

        // 오디오 소스와 필터 연결
        // source.connect(audioContext.destination);
        //filter.connect(gainNode);

        // ES 모듈 시스템을 사용하는 경우
        // 오디오 스트림에 gainNode 연결
        // source.connect(highpassFilter);
        // highpassFilter.connect(gainNode);
        // gainNode.connect(audioContext.destination);
        
        // highpassFilter.type="highpass"
        // filter.type = "peaking";
        // console.log(filter.frequency.maxValue,"___",filter.frequency.minValue,"____",filter.frequency.defaultValue);
        
        // console.log(highpassFilter.frequency.maxValue,"___",highpassFilter.frequency.minValue,"____",highpassFilter.frequency.defaultValue);
        // highpassFilter.frequency.value=7000;
        // //filter.frequency.setValueAtTime(1000, audioCtx.currentTime);
        // //filter.gain.setValueAtTime(0, audioCtx.currentTime);
        
        // // 볼륨 조절 
        // gainNode.gain.value = 120;
        // console.log(gainNode.gain.maxValue,"___",gainNode.gain.minValue,"____",gainNode.gain.defaultValue);
         
    // 캔버스 크기 설정
    // previewCanvas.value.width = stream.getVideoTracks()[0].getSettings().width;
    // previewCanvas.value.height = stream.getVideoTracks()[0].getSettings().height;

    // // 캔버스에 비디오 프레임 그리기
    // previewCtx.value = previewCanvas.value.getContext('2d');
    // // 미리 보기용 캔버스에 스트림 프레임을 그립니다.
    
    // videoElement.play();
    // videoElement.onplay = () => {
    //   const drawFrame = () => {
    //     previewCtx.value.drawImage(videoElement, 0, 0, previewCanvas.value.width, previewCanvas.value.height);
        
    //       requestAnimationFrame(drawFrame);
        
    //   };
    //   drawFrame();
    // };
    // recordedVideoElement.value.srcObject = stream; // 미리 보기 비디오 요소에 스트림 설정
    // recordedVideoElement.value.play(); // 비디오 재생
      console.dir(toRaw(mic.value));
////////////////////////////////////////////////////////////////

        const options = {
            localVideo: video,
            videoStream: stream.value, // 새로운 비디오 스트림을 전달합니다.
            //audioStream: stream,
            //constraints ,
            onicecandidate: participant.onicecandidate.bind(participant)
        };

        // WebRTC 피어 생성
        participant.rtcPeer = new kurentoUtil.WebRtcPeer.WebRtcPeerSendonly(options,
            function (error) {
                if (error) {
                    return console.error(error);
                }
                console.log("참가자", participant);
                this.generateOffer(participant.offerToReceiveVideo.bind(toRaw(participant)));
            });
    

	msg.data.forEach(participantBatch);
}
const onNewParticipant = (request) => {
  createMember(request.name)
  const receive=watchPostEffect(()=>{
    if(tokiRoomVideo.value[request.name]){
      receiveVideo(request.name)
    }
  })
  receive();
}
const participantBatch = (sender) => {
  createMember(sender)
  const receive=watchPostEffect(()=>{
    if(tokiRoomVideo.value[sender]){
      receiveVideo(sender)
    }
    receive();
})
}

const createMember = (userName) => {
  const tokiRoomMember =ref({
    name: userName,
    video: null,
    rtcPeer: null,
    onicecandidate: function(candidate){
            console.log("Local candidate" + JSON.stringify(candidate));
            const message = {
            id: 'onIceCandidate',
            candidate: candidate,
            name: userName
      };
            sendMessage(message);
    },
    offerToReceiveVideo:function(error, offerSdp){
		if (error) return console.error ("sdp offer error")
		const msg =  { id : "receiveVideoFrom",
				sender : userName,
				sdpOffer : offerSdp
			};
		sendMessage(msg);
	}
  })
  tokiRoomMembers.value= [...tokiRoomMembers.value,tokiRoomMember.value]
  
}
////



// 아이콘 변수
// const pathMicrophone = ref(mdiMicrophone )
const pathVideo = ref(mdiVideo)
const pathMonitorShare = ref(mdiMonitorShare)
const pathRadioboxMarked = ref(mdiRadioboxMarked)
const pathEmoticonOutline = ref(mdiEmoticonOutline)
const pathCog = ref(mdiCog)
const pathChatProcessingOutline  = ref(mdiChatProcessingOutline)

// 채팅 확장
const chatBox = ref(false)


const menuMicOpen = ref(false)
const menuVideoOpen = ref(false)
const menuShareOpen = ref(false)
const menuRecordOpen = ref(false)
const menuEmoticonOpen = ref(false)
const menuSettingOpen = ref(false)
const menuChatOpen = ref(false)

const roomGameDialog = ref(false)

const openGameDialog = () => {
  roomGameDialog.value = !roomGameDialog.value
}





const recording = ref(false); // 녹화 중인지 여부를 나타내는 상태 변수
const url = ref(''); // 녹화된 비디오의 URL
const blob = ref(''); // Blob 객체를 저장하는 상태 변수
const recordedChunks = ref([]); // 녹화된 데이터 청크를 저장하는 배열

let mediaRecorder; // 미디어 레코더 객체
// 녹화 시작/중지 토글 함수
const toggleRecording = async () => {
  if (recording.value) {
    stopRecording();
  } else {
    startRecording();
  }
};
// const updateVideoStream = async () => {
//   // 1. 새로운 비디오 스트림 얻기
//   const newStream = await navigator.mediaDevices.getUserMedia({ video: true, audio: true });

//   // 2. 기존 참가자의 비디오 스트림 업데이트
//   tokiRoomMembers.value.forEach(participant => {
//     participant.video = newStream;
//     if (participant.rtcPeer) {
//       // WebRTC 연결 재설정
//       participant.rtcPeer.dispose(); // 기존 WebRTC 피어 삭제
//       const options = {
//         localVideo: participant.video,
//         videoStream: newStream,
//         onicecandidate: participant.onicecandidate.bind(participant)
//       };
//       participant.rtcPeer = new kurentoUtil.WebRtcPeer.WebRtcPeerSendonly(options, function (error) {
//         if (error) {
//           return console.error(error);
//         }
//         console.log("참가자", participant);
//         this.generateOffer(participant.offerToReceiveVideo.bind(toRaw(participant)));
//       });
//     }
//   });
// };
// 녹화 시작 함수
const startRecording = async () => {
  try {
    cha.value='2';
    //if (participant.rtcPeer) {
      // WebRTC 연결 재설정
     // participant.rtcPeer.dispose();}
    //participant.rtcPeer.dispose(); 
    //onExistingParticipants();
    //updateVideoStream();
    //stream = await navigator.mediaDevices.getDisplayMedia({ video: true, audio: true });

    // // 캔버스 크기 설정
    // previewCanvas.value.width = stream.getVideoTracks()[0].getSettings().width;
    // previewCanvas.value.height = stream.getVideoTracks()[0].getSettings().height;

    // // 캔버스에 비디오 프레임 그리기
    // previewCtx.value = previewCanvas.value.getContext('2d');
    // previewCtx.value.drawImage(recordedVideoElement.value, 0, 0, previewCanvas.value.width, previewCanvas.value.height);

    // // 미리 보기용 캔버스에 스트림 프레임을 그립니다.
    // const videoElement = document.createElement('video');
    // videoElement.srcObject = stream;
    // videoElement.play();
    // videoElement.onplay = () => {
    //   const drawFrame = () => {
    //     previewCtx.value.drawImage(videoElement, 0, 0, previewCanvas.value.width, previewCanvas.value.height);
    //     if (recording.value) {
    //       requestAnimationFrame(drawFrame);
    //     }
    //   };
    //   drawFrame();
    // };
    recordedVideoElement.value.srcObject = stream.value; // 미리 보기 비디오 요소에 스트림 설정
    recordedVideoElement.value.play(); // 비디오 재생
    


    mediaRecorder = new MediaRecorder(stream.value);
    recording.value = true;

    mediaRecorder.ondataavailable = handleDataAvailable;
    mediaRecorder.onstop = handleStopRecording;

    mediaRecorder.start(); // Start recording
  } catch (error) {
    console.error('Error accessing user media:', error);
  }
};

// 녹화 중지 함수
const stopRecording = () => {
  if (mediaRecorder && mediaRecorder.state !== 'inactive') {
    mediaRecorder.stop();
  }
};

// 데이터가 사용 가능할 때 호출되는 이벤트 핸들러
const handleDataAvailable = (event) => {
  if (event.data.size > 0) {
    recordedChunks.value.push(event.data);
  }
};

// 녹화 중지 시 호출되는 이벤트 핸들러
const handleStopRecording = () => {
  blob.value = new Blob(recordedChunks.value, { type: 'video/webm' });
  url.value = window.URL.createObjectURL(blob.value);
  recordedVideoElement.value.src = url.value;
  recording.value = false;
  console.log(url.value)
  // previewCtx = previewCanvas.value.getContext('2d');
  // previewCtx.value.clearRect(0, 0, previewCanvas.value.width, previewCanvas.value.height);
};

// Clean up resources
const cleanupResources = () => {
  if (stream.value) {
    stream.getTracks().forEach(track => track.stop());
  }
  if (url.value) {
    window.URL.revokeObjectURL(url.value);
  }
};

import { onBeforeUnmount } from 'vue';
onBeforeUnmount(cleanupResources);





















// const menuState = reactive({
//   mic: false,
//   video: false,
//   share: false,
//   record: false,
//   emoticon: false,
//   setting: false,
//   chat: false,

// })

const screenWidth = ref(window.innerWidth)

function handleResize() {
  screenWidth.value = window.innerWidth
}

onMounted(async() => {
  window.addEventListener('resize', handleResize)
  start()
  
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
})

const isLagerScreen = computed(() => screenWidth.value >= 1280)

const colSize = computed(() => isLagerScreen.value ? 2 : 1)
const colOffset = computed(() => isLagerScreen.value ? 0 : 1)

</script>
<template>
   <div>
    <div background-color="red">
      <a  :href="url" download="recorded_video.webm" >확대하고 다운해</a>
    </div>
    <!-- <canvas ref="previewCanvas" controls style="max-width: 100%;" width="100" height="60"></canvas> -->
  </div>    
  <v-container id="enter" class="h-100" style="min-width: 2000px">
    
    <v-row class="h-100">
      <!-- 화면 -->
      <v-col id="screen">
        <!-- 위 서브 화면 -->
        <v-row class="mt-1" style="height: 20%;">
          <v-col v-for="(member,key,index) in tokiRoomMembers" :id="`biggBG${index}`" :key="member.name" class="ml-2 mr-2 mb-2 d-flex align-end">
            <RoomUserView  :userInfo="member">
              <template #video>
                <video :id="`room-${member.name}`" class="toki-video" :ref="(el)=>(tokiRoomVideo[member.name]=el)" autoplay ></video>
              </template>
            </RoomUserView>
          </v-col>
        </v-row>
        <!-- 메인 화면 -->
        <v-row id="main-screen" class="h-50">
          <v-col cols="12" >
            
            <!-- <canvas v-if=recording ref="previewCanvas" controls style="max-width: 100%;" width="100" height="60"></canvas>
     -->
            <video  ref="recordedVideoElement"  style="max-width: 100%; " width="500" height="300"></video>
            <div background-color="red">
      <a  :href="url" download="recorded_video.webm" >Download Recorded Video</a>
    </div>
          </v-col>
        </v-row>
        <!-- 아래 서브 화면 -->
        <v-row class="mb-1" style="height: 20%;">
          <v-col id="biggBG1" class="ml-2 mr-2 mt-2 d-flex align-end">
            <v-sheet></v-sheet>
          </v-col>
          <v-col id="biggBG2" class="ml-2 mr-2 mt-2 d-flex align-end">
            <v-sheet></v-sheet>
          </v-col>
          <v-col id="biggBG3" class="ml-2 mr-2 mt-2 d-flex align-end">
            <v-sheet></v-sheet>
          </v-col>
          <v-col id="biggBG4" class="ml-2 mr-2 mt-2 d-flex align-end">
            <v-sheet></v-sheet>
          </v-col>
          <v-col id="biggBG5" class="ml-2 mr-2 mt-2 d-flex align-end">
            <v-sheet></v-sheet>
          </v-col>
        </v-row>


        <!-- 영상 옵션 바 -->
        <v-row class="mt-1 ml-1 mr-1" >
          
          <!-- 마이크 설정-->
          <!-- <v-col>
            <v-expansion-panels>
              <v-expansion-panel bg-color="black" style="border-radius: 30px; border: 1px solid white;">
                <v-expansion-panel-title expand-icon="mdi-menu-down">
                  <svg-icon type="mdi" :path="pathMicrophone"></svg-icon>
                  <p class="ml-5">마이크</p>
                </v-expansion-panel-title>
                <v-expansion-panel-text>
                  <v-list bg-color="black">
                    <v-list-item value="마이크-on-off">마이크 on/off</v-list-item>
                    <v-list-item value="음성변조">음성 변조</v-list-item>
                  </v-list>
                </v-expansion-panel-text>
              </v-expansion-panel>
            </v-expansion-panels>
          </v-col> -->

          <v-col
            justify="center"
            :cols="colSize"
            :offset="colOffset"
          >
            <div class="mx-4 hidden-sm-and-down"></div>

            <v-menu location="top" v-model="menuMicOpen">
              <template v-slot:activator="{ props }">
                <v-btn
                  v-if="isLagerScreen"
                  prepend-icon="mdi-microphone"
                  :append-icon="menuMicOpen ? 'mdi-menu-down' : 'mdi-menu-up'"
                  color="black"
                  class="ma-2 "
                  v-bind="props"
                  size="x-large"
                  style="border-radius: 30px; border: 1px solid white;"
                >
                  마이크
                </v-btn>
                <v-btn
                  v-else
                  class="ma-2"
                  color="black"
                  icon="mdi-microphone"
                  v-bind="props"
                  width="100%"
                  size="large"
                  style="border: 1px solid white;"
                >

                </v-btn>
              </template>
              <v-list bg-color="black" style="text-align: center;">
                <v-list-item 
                  link 
                  value="마이크-on-off"
                  @click="startAudioProcessing"
                >
                  마이크 on/off
                </v-list-item>

                <v-list-item 
                  link 
                  value="음성변조"
                >
                  음성 변조
                </v-list-item>
              </v-list>
            </v-menu>
          </v-col>


          <!-- 캠 설정 -->
          <!-- <v-col>
            <v-expansion-panels>
              <v-expansion-panel bg-color="black" style="border-radius: 30px; border: 1px solid white;">
                <v-expansion-panel-title expand-icon="mdi-menu-down">
                  <svg-icon type="mdi" :path="pathVideo"></svg-icon>
                  <p class="ml-5">캠</p>
                </v-expansion-panel-title>
                <v-expansion-panel-text>
                  <v-list bg-color="black">
                    <v-list-item value="캠-on-off">캠 on/off</v-list-item>
                    <v-list-item value="가상배경">가상 배경</v-list-item>
                    <v-list-item value="버츄얼">버츄얼 적용</v-list-item>
                  </v-list>
                </v-expansion-panel-text>
              </v-expansion-panel>
            </v-expansion-panels>
          </v-col> -->

          <v-col
            justify="center"
            :cols="colSize"
            :offset="colOffset"
          >
            <div class="mx-4 hidden-sm-and-down"></div>

            <v-menu location="top" v-model="menuVideoOpen">
              <template v-slot:activator="{ props }">
                <v-btn
                  v-if="isLagerScreen"
                  prepend-icon="mdi-video"
                  :append-icon="menuVideoOpen ? 'mdi-menu-down' : 'mdi-menu-up'"
                  color="black"
                  class="ma-2 "
                  v-bind="props"
                  size="x-large"
                  style="border-radius: 30px; border: 1px solid white;"
                >
                  캠
                </v-btn>
                <v-btn
                  v-else
                  class="ma-2"
                  color="black"
                  v-bind="props"
                  icon="mdi-video"
                  size="large"
                  style="border: 1px solid white;"
                >

                </v-btn>
              </template>
              <v-list bg-color="black" style="text-align: center;">
                <v-list-item link value="캠-on-off">
                  캠 on/off
                </v-list-item>

                <v-list-item link value="가상배경">
                  가상 배경
                </v-list-item>

                <v-list-item link value="버츄얼">
                  버츄얼 적용
                </v-list-item>
              </v-list>
            </v-menu>
          </v-col>

          <!-- 화면 공유 -->
          <!-- <v-col>
            <v-expansion-panels>
              <v-expansion-panel bg-color="black" style="border-radius: 30px; border: 1px solid white;">
                <v-expansion-panel-title expand-icon="mdi-menu-down">
                  <svg-icon type="mdi" :path="pathMonitorShare"></svg-icon>
                  <p class="ml-5">공유</p>
                </v-expansion-panel-title>
                <v-expansion-panel-text>
                  <v-list bg-color="black">
                    <v-list-item value="화면공유">화면 공유하기</v-list-item>
                  </v-list>
                </v-expansion-panel-text>
              </v-expansion-panel>
            </v-expansion-panels>
          </v-col> -->

          <v-col
            justify="center"
            :cols="colSize"
            :offset="colOffset"
          >
            <div class="mx-4 hidden-sm-and-down"></div>

            <v-menu location="top" v-model="menuShareOpen">
              <template v-slot:activator="{ props }">
                <v-btn
                  v-if="isLagerScreen"
                  prepend-icon="mdi-monitor-share"
                  :append-icon="menuShareOpen ? 'mdi-menu-down' : 'mdi-menu-up'"
                  color="black"
                  class="ma-2 "
                  v-bind="props"
                  size="x-large"
                  style="border-radius: 30px; border: 1px solid white;"
                >
                  공유
                </v-btn>
                <v-btn
                  v-else
                  class="ma-2"
                  color="black"
                  v-bind="props"
                  icon="mdi-monitor-share"
                  size="large"
                  style="border: 1px solid white;"
                >

                </v-btn>
              </template>
              <v-list bg-color="black" style="text-align: center;">                  
                <v-list-item value="화면공유">
                  화면 공유하기
                </v-list-item>
              </v-list>
            </v-menu>
          </v-col>


          <!-- 녹화 -->
          <!-- <v-col>
            <v-expansion-panels>
              <v-expansion-panel bg-color="black" style="border-radius: 30px; border: 1px solid white;">
                <v-expansion-panel-title expand-icon="mdi-menu-down">
                  <svg-icon type="mdi" :path="pathRadioboxMarked"></svg-icon>
                  <p class="ml-5">녹화</p>
                </v-expansion-panel-title>
                <v-expansion-panel-text>
                  <v-list bg-color="black">
                    <v-list-item value="화면녹화">화면 녹화</v-list-item>
                  </v-list>
                </v-expansion-panel-text>
              </v-expansion-panel>
            </v-expansion-panels>
          </v-col> -->

          <v-col
            justify="center"
            :cols="colSize"
            :offset="colOffset"
          >
            <div class="mx-4 hidden-sm-and-down"></div>

            <v-menu location="top" v-model="menuRecordOpen">
              <template v-slot:activator="{ props }">
                <v-btn
                  v-if="isLagerScreen"
                  prepend-icon="mdi-radiobox-marked"
                  :append-icon="menuRecordOpen ? 'mdi-menu-down' : 'mdi-menu-up'"
                  color="black"
                  class="ma-2 "
                  v-bind="props"
                  size="x-large"
                  style="border-radius: 30px; border: 1px solid white;"
                  @click="toggleRecording">{{ recording ? '해제' : '확대' }}
                  
                </v-btn>
                <v-btn
                  v-else
                  class="ma-2"
                  color="black"
                  v-bind="props"
                  icon="mdi-radiobox-marked"
                  size="large"
                  style="border: 1px solid white;"
                >

                </v-btn>
              </template>
              <v-list bg-color="black" style="text-align: center;">                  
                <v-list-item value="화면녹화">
                  화면 녹화
                </v-list-item>
              </v-list>
            </v-menu>
          </v-col>


          <!-- 이모티콘 -->
          <!-- <v-col cols="1" sm="2">
            <v-expansion-panels>
              <v-expansion-panel bg-color="black" style="border-radius: 30px; border: 1px solid white;">
                <v-expansion-panel-title expand-icon="mdi-menu-down">
                  <svg-icon type="mdi" :path="pathEmoticonOutline"></svg-icon>
                  <p class="ml-5">이모티콘</p>
                </v-expansion-panel-title>
                <v-expansion-panel-text>
                  <v-chip-group>
                    <v-chip value="화면녹화">😀</v-chip>
                    <v-chip value="화면녹화">😀</v-chip>
                    <v-chip value="화면녹화">😀</v-chip>
                    <v-chip value="화면녹화">😀</v-chip>
                    <v-chip value="화면녹화">😀</v-chip>
                  </v-chip-group>
                </v-expansion-panel-text>
              </v-expansion-panel>
            </v-expansion-panels>
          </v-col> -->

          <v-col
            justify="center"
            cols="1"
          >
            <div class="mx-4 hidden-sm-and-down"></div>

            <v-menu location="top" v-model="menuEmoticonOpen">
              <template v-slot:activator="{ props }">
                <v-btn
                  class="ma-2"
                  color="black"
                  v-bind="props"
                  icon="mdi-emoticon-outline"
                  size="large"
                  style="border: 1px solid white;"
                >

                </v-btn>
              </template>              
              <v-list bg-color="black" style="text-align: center;">                  
                <v-list-item-group multiple>
                  <v-row>
                    <v-col cols="12">
                      <v-chip-group row>
                        <v-chip value="화면녹화">😀</v-chip>
                        <v-chip value="화면녹화">😀</v-chip>
                        <v-chip value="화면녹화">😀</v-chip>
                        <v-chip value="화면녹화">😀</v-chip>
                      </v-chip-group>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="12">
                      <v-chip-group row>
                        <v-chip value="화면녹화">😀</v-chip>
                        <v-chip value="화면녹화">😀</v-chip>
                        <v-chip value="화면녹화">😀</v-chip>
                        <v-chip value="화면녹화">😀</v-chip>
                      </v-chip-group>
                    </v-col>
                  </v-row>
                  <!-- <v-chip value="화면녹화">😀</v-chip>
                  <v-chip value="화면녹화">😀</v-chip>
                  <v-chip value="화면녹화">😀</v-chip>
                  <v-chip value="화면녹화">😀</v-chip>
                  <v-chip value="화면녹화">😀</v-chip> -->
                </v-list-item-group>
              </v-list>
            </v-menu>
          </v-col>

          <!-- 환경 설정 -->
          <!-- <v-col>
            <v-expansion-panels>
              <v-expansion-panel bg-color="black" style="border-radius: 10px; border: 1px solid white;">
                <v-expansion-panel-title hide-actions>
                  <svg-icon type="mdi" :path="pathCog"></svg-icon>
                  <p class="ml-5">설정</p>
                </v-expansion-panel-title>
              </v-expansion-panel>
            </v-expansion-panels>
          </v-col> -->
          <v-col
            justify="center"
            cols="1"
          >
            <div class="mx-4 hidden-sm-and-down"></div>

            <v-menu location="top" v-model="menuSettingOpen">
              <template v-slot:activator="{ props }">
                <v-btn
                  class="ma-2"
                  color="black"
                  v-bind="props"
                  icon="mdi-cog"
                  size="large"
                  style="border: 1px solid white;"
                >

                </v-btn>
              </template>
              <v-list bg-color="black" style="text-align: center; width: 250px; white-space: nowrap;">   
                <v-list-item 
                  prepend-icon="mdi-gamepad-variant"
                  style="margin-left: 10px;"
                  value="게임"
                  @click="openGameDialog"
                >
                  게임
                </v-list-item>
                
                <v-list-item 
                  prepend-icon="mdi-account-group"
                  style="margin-left: 10px;"
                  value="소회의실"
                >
                  소회의실
                </v-list-item>
                <v-list-item 
                  prepend-icon="mdi-vote"
                  style="margin-left: 10px;"
                  value="투표"
                >
                  투표
                </v-list-item>
                <v-list-item 
                  prepend-icon="mdi-send"
                  style="margin-left: 10px;"
                  value="초대"
                >
                  초대
                </v-list-item>               
                <v-list-item 
                  prepend-icon="mdi-cog"
                  style="margin-left: 10px;"
                  value="환경설정"
                >
                  환경 설정
                </v-list-item>
              </v-list>
              
            </v-menu>
          </v-col>
          
          
          <!-- 채팅 -->
          <v-col
            justify="center"
            :cols="colSize"            
          >
            <!-- <div class="mx-4 hidden-sm-and-down"></div> -->
          
            <v-btn
              v-if="isLagerScreen"
              prepend-icon="mdi-chat-processing-outline"
              color="black"
              class="ma-2 "
              v-bind="props"
              size="x-large"
              style="border-radius: 10px; border: 1px solid white;"
              @click="chatBox = !chatBox"
            >
              채팅
            </v-btn>
              
            <v-btn
              v-else
              class="ma-2"
              color="black"
              v-bind="props"
              icon="mdi-chat-processing-outline"
              size="large"
              style="border-radius: 10px; border: 1px solid white;"
              
            >

            </v-btn>
              

            
          </v-col>


          <!-- <v-col>
            <v-expansion-panels @click="chatBox = !chatBox">
              <v-expansion-panel bg-color="black" style="border-radius: 10px; border: 1px solid white;">
                <v-expansion-panel-title collapse-icon="mdi-rabbit-variant-outline" expand-icon="mdi-menu-right">
                  <svg-icon type="mdi" :path="pathChatProcessingOutline"></svg-icon>
                  <p>채팅</p>
                </v-expansion-panel-title>
              </v-expansion-panel>
            </v-expansion-panels>
          </v-col> -->
        </v-row>

        <!-- 영상 타이틀 -->
        <!-- <v-row>
          <v-col cols="2">
            <v-sheet>닉네임</v-sheet>
          </v-col>
          <v-col cols="2">
            <v-sheet>화상 채팅 방 제목</v-sheet>
          </v-col>
        </v-row> -->
      </v-col>

      
      <!-- 채팅 화면 -->
      <v-col v-if="chatBox" id="chatt" cols="3">
        <v-row class="h-100">
          <v-col><RoomChatComponent :userName="userInfo"/></v-col>
        </v-row>
      </v-col>
    </v-row>



    <!-- Modal 화면 -->
    <RoomGameModal 
      :roomGameDialog="roomGameDialog"
      @update:roomGameDialog="roomGameDialog = $event"
    />

  </v-container>
  <link href="https://cdn.jsdelivr.net/npm/@mdi/font@5.x/css/materialdesignicons.min.css" rel="stylesheet">
</template>



<style scoped>
#main-screen {
    background-color: #063b17; /* 칠판의 배경색을 흰색으로 설정합니다. */
    border: 30px solid rgb(145, 68, 23); /* 칠판의 테두리를 그립니다. */
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5); /* 흰색 칠판에 회색 그림자를 추가합니다. */
    position: relative; /* 내부 요소의 위치를 상대적으로 설정합니다. */
    overflow: hidden; /* 내부 컨텐츠가 칠판 영역을 벗어나지 않도록 설정합니다. */
}


#screen {
  background-color: rgb(0, 0, 0);
  white-space: nowrap;
}
#chatt {
  background-color: aqua;
}

.toki-video{
  width: 100%;
	height: auto;
}video {
      width: 100%; /* 자식 요소인 video 태그의 너비를 100%로 설정하여 부모 요소에 맞춥니다. */
      height: 100%; /* 자식 요소인 video 태그의 높이를 100%로 설정하여 부모 요소에 맞춥니다. */
      object-fit: cover; /* video 요소가 부모 요소에 꽉 차도록 합니다. */
    }

/* 작은 화면 */
/* #biggBG1 {
  background-color: antiquewhite;
}
#biggBG2 {
  background-color: aquamarine;
}
#biggBG3 {
  background-color: rgb(186, 222, 176);
}
#biggBG4 {
  background-color: rgb(232, 255, 127);
}
#biggBG5 {
  background-color: rgb(255, 163, 127);
} */

</style>