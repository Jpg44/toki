<script setup>
import { sendMessage } from './WebRTC';
import adapter from 'webrtc-adapter';
import { ref, onMounted } from 'vue';

const containerClass = ref(isPresentMainParticipant() ? 'participant' : 'participant main');

function switchContainerClass() {
  containerClass.value = containerClass.value === 'participant' ? 'participant main' : 'participant';
}

function isPresentMainParticipant() {
  return document.getElementsByClassName('participant main').length !== 0;
}

onMounted(() => {
  // Your other logic here
});
</script>

<template>
  <span :class="containerClass" :id="name" @click="switchContainerClass">
    <video :id="'video-' + name" autoplay controls="false"></video>
    <textarea></textarea>
    <span>{{ name }}</span>
  </span>
</template>

<style scoped>
.participant {
  /* Your styles here */
}

.participant.main {
  /* Your styles here */
}
</style>