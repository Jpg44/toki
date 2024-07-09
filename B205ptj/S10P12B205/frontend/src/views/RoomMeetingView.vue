<script setup>
import RoomMeetingComponent from '@/components/room_components/RoomMeetingComponent.vue';
import { defineProps, onMounted } from 'vue';

const roomData = JSON.parse(sessionStorage.getItem('roomData'))


onMounted(() => {
  console.log(roomData)
})

</script>

<template>
    <div class="room-view">
      <RoomMeetingComponent/>
    </div>
</template>

<style scoped>
.room-view {
  height: 100vh;
  width: 100vw;
}
</style>
