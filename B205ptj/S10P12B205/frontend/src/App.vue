<script setup>
import { useRoute } from 'vue-router';
import HeaderView from './views/HeaderView.vue';
import FooterView from './views/FooterView.vue';
import { computed } from 'vue';


const route = useRoute()
const isRoomView = computed(() => route.name === 'roomjoin')
const isProfileView = computed(() => route.name === 'profile')
const isMeetingRoomView = computed(() => route.name === 'roomMeeting')
</script>

<template>
  <v-app style="padding-top: 60px; ">
    <HeaderView v-if="!isRoomView && !isMeetingRoomView" />
    <RouterView />
    <FooterView v-if="!isRoomView && !isProfileView && !isMeetingRoomView"/>
    <link href="https://cdn.jsdelivr.net/npm/@mdi/font@5.x/css/materialdesignicons.min.css" rel="stylesheet">
  </v-app>
</template>

<style scoped>

</style>
