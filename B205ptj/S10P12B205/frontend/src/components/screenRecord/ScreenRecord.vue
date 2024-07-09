<template>
  <div>
    <!-- Video elements: preview and recorded -->
    <div>
      <button @click="toggleRecording">{{ recording ? 'Stop Recording' : 'Start Recording' }}</button>
    </div>
    <canvas ref="previewCanvas" controls style="max-width: 100%;" width="100" height="60"></canvas>
    <video ref="recordedVideoElement" controls style="max-width: 100%; " width="500" height="300"></video>
    
    <div>
      <a v-if="blob" :href="url" download="recorded_video.webm">Download Recorded Video</a>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const previewCanvas = ref(null); // 미리 보기용 캔버스 요소의 참조
const recordedVideoElement = ref(null); // 녹화된 비디오 요소의 참조
const recording = ref(false); // 녹화 중인지 여부를 나타내는 상태 변수
const url = ref(''); // 녹화된 비디오의 URL
const blob = ref(''); // Blob 객체를 저장하는 상태 변수
const recordedChunks = ref([]); // 녹화된 데이터 청크를 저장하는 배열
let stream; // 미디어 스트림 객체
let mediaRecorder; // 미디어 레코더 객체
let previewCtx=ref('');
// 녹화 시작/중지 토글 함수
const toggleRecording = async () => {
  if (recording.value) {
    stopRecording();
  } else {
    startRecording();
  }
};

// 녹화 시작 함수
const startRecording = async () => {
  try {
    stream = await navigator.mediaDevices.getDisplayMedia({ video: true, audio: true });

    // 캔버스 크기 설정
    previewCanvas.value.width = stream.getVideoTracks()[0].getSettings().width;
    previewCanvas.value.height = stream.getVideoTracks()[0].getSettings().height;

    // 캔버스에 비디오 프레임 그리기
    previewCtx.value = previewCanvas.value.getContext('2d');
    previewCtx.value.drawImage(recordedVideoElement.value, 0, 0, previewCanvas.value.width, previewCanvas.value.height);

    // 미리 보기용 캔버스에 스트림 프레임을 그립니다.
    const videoElement = document.createElement('video');
    videoElement.srcObject = stream;
    videoElement.play();
    videoElement.onplay = () => {
      const drawFrame = () => {
        previewCtx.value.drawImage(videoElement, 0, 0, previewCanvas.value.width, previewCanvas.value.height);
        if (recording.value) {
          requestAnimationFrame(drawFrame);
        }
      };
      drawFrame();
    };

    mediaRecorder = new MediaRecorder(stream);
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
  previewCtx = previewCanvas.value.getContext('2d');
  previewCtx.value.clearRect(0, 0, previewCanvas.value.width, previewCanvas.value.height);
};

// Clean up resources
const cleanupResources = () => {
  if (stream) {
    stream.getTracks().forEach(track => track.stop());
  }
  if (url.value) {
    window.URL.revokeObjectURL(url.value);
  }
};

import { onBeforeUnmount } from 'vue';
onBeforeUnmount(cleanupResources);
</script>