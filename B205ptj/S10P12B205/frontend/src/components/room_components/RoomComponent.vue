<script setup>
import { computed, onMounted, onUnmounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import RoomGameModal from '../modal/RoomGameModal.vue'
import RoomSubMeetingModal from '@/components/modal/RoomSubMeetingModal.vue'
import RoomVoiceChangeModal from '@/components/modal/RoomVoiceChangeModal.vue'
import RoomVoteModal from '@/components/modal/RoomVoteModal.vue'
import RoomInviteModal from '@/components/modal/RoomInviteModal.vue'
import RoomConfigModal from '@/components/modal/RoomConfigModal.vue'
import RoomVideoBackgroundModal from '@/components/modal/RoomVideoBackgroundModal.vue'
import RoomVideoVirtualModal from '@/components/modal/RoomVideoVirtualModal.vue'

import RoomChatComponent from '@/components/room_components/RoomChatComponent.vue'

// 패널 확장
const menuMicOpen = ref(false) // 마이크
const menuVideoOpen = ref(false) // 비디오
const menuShareOpen = ref(false) // 공유
const menuRecordOpen = ref(false) // 녹화
const menuEmoticonOpen = ref(false) // 이모지
const menuSettingOpen = ref(false) // 옵션
const menuChatOpen = ref(false) // 채팅

// 마이크 on / off
const micOnOff = ref(false)

// 음성 변조 모달창 on / off
const roomVoiceChangeDialog = ref(false)
const openVoiceChangeDialog = () => {
  roomVoiceChangeDialog.value = !roomVoiceChangeDialog.value
}

// 비디오 on / off
const videoOnOff = ref(false)

// 가상 배경 모달창 on / off
const roomVideoBackgroundDialog = ref(false)
const openVideoBackgroundDialog = () => {
  roomVideoBackgroundDialog.value = !roomVideoBackgroundDialog.value
}

// 버츄얼 적용 모달창 on / off
const roomVideoVirtualDialog = ref(false)
const openVideoVirtualDialog = () => {
  roomVideoVirtualDialog.value = !roomVideoVirtualDialog.value
}

// 화면 공유 on / off
const shareOnOff = ref(false)

// 녹화  on / off
const recordOnOff = ref(false)

// 이모티콘 클릭 할 때, 함수
const selectEmoticon = (emt) => {
  console.log(`${emt} 출력`)
}

// 게임 모달창 on / off
const roomGameDialog = ref(false)
const openGameDialog = () => {
  roomGameDialog.value = !roomGameDialog.value
}

// 소회의실 모달창 on / off
const roomMeetingDialog = ref(false)
const openMeetingDialog = () => {
  roomMeetingDialog.value = !roomMeetingDialog.value
}

// 투표 모달창 on / off
const roomVotegDialog = ref(false)
const openVoteDialog = () => {
  roomVotegDialog.value = !roomVotegDialog.value
}

// 초대 모달창 on / off
const roomInviteDialog = ref(false)
const openInvitDialog = () => {
  roomInviteDialog.value = !roomInviteDialog.value
}

// 환경설정 모달창 on / off
const roomConfigDialog = ref(false)
const openConfigDialog = () => {
  roomConfigDialog.value = !roomConfigDialog.value
}

// 나가기 버튼 클릭 시, 함수
const router = useRouter()
const exitRoom = () => {
  router.go(-1)
}

const screenWidth = ref(window.innerWidth)

function handleResize() {
  screenWidth.value = window.innerWidth
}

onMounted(() => {
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
})

const isLagerScreen = computed(() => screenWidth.value >= 1280)

const colSize = computed(() => isLagerScreen.value ? 2 : 1)
const colOffset = computed(() => isLagerScreen.value ? 0 : 1)

</script>
<template>
  <v-container id="enter" class="h-100" style="min-width: 600px">
    <v-row class="h-100">
      <!-- 화면 -->
      <v-col id="screen">
        <!-- 위 서브 화면 -->
        <v-row class="mt-1" style="height: 20%;">
          <v-col id="biggBG1" class="ml-2 mr-2 mb-2 d-flex align-end">
            <v-sheet>아이디 or 닉네임</v-sheet>
          </v-col>
          <v-col id="biggBG2" class="ml-2 mr-2 mb-2 d-flex align-end">
            <v-sheet>아이디 or 닉네임</v-sheet>
          </v-col>
          <v-col id="biggBG3" class="ml-2 mr-2 mb-2 d-flex align-end">
            <v-sheet>아이디 or 닉네임</v-sheet>
          </v-col>
          <v-col id="biggBG4" class="ml-2 mr-2 mb-2 d-flex align-end">
            <v-sheet>아이디 or 닉네임</v-sheet>
          </v-col>
          <v-col id="biggBG5" class="ml-2 mr-2 mb-2 d-flex align-end">
            <v-sheet>아이디 or 닉네임</v-sheet>
          </v-col>
        </v-row>
        <!-- 메인 화면 -->
        <v-row id="main-screen" class="h-50">
          <v-col cols="12">
            메인 화면
          </v-col>
        </v-row>
        <!-- 아래 서브 화면 -->
        <v-row class="mb-1" style="height: 20%;">
          <v-col id="biggBG1" class="ml-2 mr-2 mt-2 d-flex align-end">
            <v-sheet>아이디 or 닉네임</v-sheet>
          </v-col>
          <v-col id="biggBG2" class="ml-2 mr-2 mt-2 d-flex align-end">
            <v-sheet>아이디 or 닉네임</v-sheet>
          </v-col>
          <v-col id="biggBG3" class="ml-2 mr-2 mt-2 d-flex align-end">
            <v-sheet>아이디 or 닉네임</v-sheet>
          </v-col>
          <v-col id="biggBG4" class="ml-2 mr-2 mt-2 d-flex align-end">
            <v-sheet>아이디 or 닉네임</v-sheet>
          </v-col>
          <v-col id="biggBG5" class="ml-2 mr-2 mt-2 d-flex align-end">
            <v-sheet>아이디 or 닉네임</v-sheet>
          </v-col>
        </v-row>


        <!-- 영상 옵션 바 -->
        <v-row class="mt-1 ml-1 mr-1" >

          <!-- 마이크 설정-->
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
                  :prepend-icon="micOnOff? 'mdi-microphone' : 'mdi-microphone-off'"
                  :append-icon="menuMicOpen ? 'mdi-menu-down' : 'mdi-menu-up'"
                  :color="micOnOff? 'green-lighten-1' : 'black'"
                  class="ma-2"
                  v-bind="props"
                  size="x-large"
                  style="border-radius: 30px; border: 1px solid white;"
                >
                  마이크
                </v-btn>
                <v-btn
                  v-else
                  class="ma-2"
                  :color="micOnOff? 'green-lighten-1' : 'black'"
                  :icon="micOnOff? 'mdi-microphone' : 'mdi-microphone-off'"
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
                  @click="micOnOff = !micOnOff"
                >
                  마이크 on/off
                </v-list-item>

                <v-list-item 
                  link 
                  value="음성변조"
                  @click="openVoiceChangeDialog"
                >
                  음성 변조
                </v-list-item>
              </v-list>
            </v-menu>
          </v-col>


          <!-- 캠 설정 -->
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
                  :prepend-icon="videoOnOff? 'mdi-video' : 'mdi-video-off'"
                  :append-icon="menuVideoOpen ? 'mdi-menu-down' : 'mdi-menu-up'"
                  :color="videoOnOff? 'green-lighten-1' : 'black'"
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
                  :color="videoOnOff? 'green-lighten-1' : 'black'"
                  :icon="videoOnOff? 'mdi-video' : 'mdi-video-off'"
                  v-bind="props"
                  size="large"
                  style="border: 1px solid white;"
                >
                </v-btn>
              </template>
              <v-list bg-color="black" style="text-align: center;">
                <v-list-item link value="캠-on-off" @click="videoOnOff = !videoOnOff">
                  캠 on/off
                </v-list-item>

                <v-list-item link value="가상배경" @click="openVideoBackgroundDialog">
                  가상 배경
                </v-list-item>

                <v-list-item link value="버츄얼" @click="openVideoVirtualDialog">
                  버츄얼 적용
                </v-list-item>
              </v-list>
            </v-menu>
          </v-col>


          <!-- 화면 공유 -->
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
                  :prepend-icon="shareOnOff? 'mdi-monitor-share' : 'mdi-monitor-off'"
                  :append-icon="menuShareOpen ? 'mdi-menu-down' : 'mdi-menu-up'"
                  :color="shareOnOff? 'red-lighten-1' : 'black'"
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
                  :color="shareOnOff? 'red-lighten-1' : 'black'"
                  :icon="shareOnOff? 'mdi-monitor-share' : 'mdi-monitor-off'"
                  v-bind="props"
                  size="large"
                  style="border: 1px solid white;"
                >
                </v-btn>
              </template>
              <v-list bg-color="black" style="text-align: center;">                  
                <v-list-item value="화면공유" @click="shareOnOff = !shareOnOff">
                  화면 공유하기
                </v-list-item>
              </v-list>
            </v-menu>
          </v-col>

          
          <!-- 화면 녹화 -->
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
                  :color="recordOnOff? 'red-lighten-1' : 'black'"
                  class="ma-2 "
                  v-bind="props"
                  size="x-large"
                  style="border-radius: 30px; border: 1px solid white;"
                >
                  녹화
                </v-btn>
                <v-btn
                  v-else
                  class="ma-2"
                  icon="mdi-radiobox-marked"
                  :color="recordOnOff? 'red-lighten-1' : 'black'"
                  v-bind="props"
                  size="large"
                  style="border: 1px solid white;"
                >
                </v-btn>
              </template>
              <v-list bg-color="black" style="text-align: center;">                  
                <v-list-item value="화면녹화" @click="recordOnOff = !recordOnOff">
                  화면 녹화
                </v-list-item>
              </v-list>
            </v-menu>
          </v-col>


          <!-- 이모티콘 -->
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
                <v-item-group multiple>
                  <v-row>
                    <v-col cols="12">
                      <v-chip-group row>
                        <v-chip @click="selectEmoticon('이모지 1')">😀</v-chip>
                        <v-chip @click="selectEmoticon('이모지 2')">😀</v-chip>
                        <v-chip @click="selectEmoticon('이모지 3')">😀</v-chip>
                        <v-chip @click="selectEmoticon('이모지 4')">😀</v-chip>
                      </v-chip-group>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="12">
                      <v-chip-group row>
                        <v-chip @click="selectEmoticon('이모지 5')">😀</v-chip>
                        <v-chip @click="selectEmoticon('이모지 6')">😀</v-chip>
                        <v-chip @click="selectEmoticon('이모지 7')">😀</v-chip>
                        <v-chip @click="selectEmoticon('이모지 8')">😀</v-chip>
                      </v-chip-group>
                    </v-col>
                  </v-row>
                </v-item-group>
              </v-list>
            </v-menu>
          </v-col>


          <!-- 환경 설정 -->
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
                  @click="openMeetingDialog"
                >
                  소회의실
                </v-list-item>
                <v-list-item 
                  prepend-icon="mdi-vote"
                  style="margin-left: 10px;"
                  value="투표"
                  @click="openVoteDialog"
                >
                  투표
                </v-list-item>
                <v-list-item 
                  prepend-icon="mdi-send"
                  style="margin-left: 10px;"
                  value="초대"
                  @click="openInvitDialog"
                >
                  초대
                </v-list-item>               
                <v-list-item 
                  prepend-icon="mdi-cog"
                  style="margin-left: 10px;"
                  value="환경설정"
                  @click="openConfigDialog"
                >
                  환경 설정
                </v-list-item>
                <v-list-item 
                  prepend-icon="mdi-exit-to-app"
                  style="margin-left: 10px;"
                  value="나가기"
                  @click="exitRoom"
                >
                  방 나가기
                </v-list-item>
              </v-list>
              
            </v-menu>
          </v-col>
          
          
          <!-- 채팅 -->
          <v-col
            justify="center"
            :cols="colSize"            
          >
            <v-btn
              v-if="isLagerScreen"
              prepend-icon="mdi-chat-processing-outline"
              color="black"
              class="ma-2 "
              size="x-large"
              style="border-radius: 10px; border: 1px solid white;"
              @click="menuChatOpen = !menuChatOpen"
            >
              채팅
            </v-btn>
              
            <v-btn
              v-else
              class="ma-2"
              color="black"
              icon="mdi-chat-processing-outline"
              size="large"
              style="border-radius: 10px; border: 1px solid white;"
              @click="menuChatOpen = !menuChatOpen"
            >
            </v-btn>
              

            
          </v-col>

        </v-row>

        <!-- 영상 타이틀 -->
        <v-row>
          <v-col cols="2">
            <v-sheet>닉네임</v-sheet>
          </v-col>
          <v-col cols="2">
            <v-sheet>화상 채팅 방 제목</v-sheet>
          </v-col>
        </v-row>
      </v-col>

      
      <!-- 채팅 화면 -->
      <v-col v-show="menuChatOpen" id="chatt" cols="3">
        <v-row class="h-100">
          <v-col><RoomChatComponent/></v-col>
        </v-row>
      </v-col>
    </v-row>



    <!-- Modal (게임) 화면 -->
    <RoomGameModal 
      :roomGameDialog="roomGameDialog"
      @update:roomGameDialog="roomGameDialog = $event"
    />
    <!-- Modal (소회의실) 화면 -->
    <RoomSubMeetingModal
      :roomMeetingDialog="roomMeetingDialog"
      @update:roomMeetingDialog="roomMeetingDialog = $event"
    />
    <!-- Modal (투표) 화면 -->
    <RoomVoteModal
      :roomVotegDialog="roomVotegDialog"
      @update:roomVotegDialog="roomVotegDialog = $event"
    />
    <!-- Modal (초대) 화면 -->
    <RoomInviteModal
      :roomInviteDialog="roomInviteDialog"
      @update:roomInviteDialog="roomInviteDialog = $event"
    />
    <!-- Modal (환경 설정) 화면 -->
    <RoomConfigModal
      :roomConfigDialog="roomConfigDialog"
      @update:roomConfigDialog="roomConfigDialog = $event"
    />
    <!-- Modal (음성 변조) 화면 -->
    <RoomVoiceChangeModal
      :roomVoiceChangeDialog="roomVoiceChangeDialog"
      @update:roomVoiceChangeDialog="roomVoiceChangeDialog = $event"
    />
    <!-- Modal (가상 배경) 화면 -->
    <RoomVideoBackgroundModal
      :roomVideoBackgroundDialog="roomVideoBackgroundDialog"
      @update:roomVideoBackgroundDialog="roomVideoBackgroundDialog = $event"
    />
    <!-- Modal (버츄얼) 화면 -->
    <RoomVideoVirtualModal
      :roomVideoVirtualDialog="roomVideoVirtualDialog"
      @update:roomVideoVirtualDialog="roomVideoVirtualDialog = $event"
    />
  </v-container>
  <link href="https://cdn.jsdelivr.net/npm/@mdi/font@5.x/css/materialdesignicons.min.css" rel="stylesheet">
</template>



<style scoped>
#main-screen {
  background-color: rgb(167, 111, 169);
}

#screen {
  background-color: rgb(0, 0, 0);
  white-space: nowrap;
}
#chatt {
  background-color: aqua;
}

/* 작은 화면 */
#biggBG1 {
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
}

</style>