<script setup>
import { ref, watchEffect, onMounted, computed } from 'vue';
import { useRouter } from 'vue-router';
import LoginButton from '@/components/buttons/LoginButton.vue';
import SideVarButton from '@/components/buttons/SideVarButton.vue';
import SideVarModal from '@/components/modal/SideVarModal.vue'
import BroadcastButton from '@/components/buttons/BroadcastButton.vue';
import { logout } from '@/js/Login';
import router from '@/router/index.js';
import { useAuthStore } from '@/stores/auth';

const authStore = useAuthStore()



const isLoggedIn = computed(() => authStore.isAuthenticated)
const search = ref('')

const drawer = ref(false)

const toggleDrawer = () => {
  drawer.value = !drawer.value
}

const logoutAccount = () => {
  // 로그아웃 후 홈으로 리디렉션
  authStore.clearToken()
  logout(
    (success) => {
      console.log(success)
    },
    (error) => {
      console.log(error)
    }
  )
  router.go(0)
}

const searching = () => {
  if (search.value) {
    router.push({ name: 'search', query: { tag: search.value }})
  }
}



</script>

<template>
  <v-app-bar 
    elevation="0" 
    style="border-bottom: 1px solid rgba(0,0,0,0.1);"
  >
    <template
      v-slot:prepend
      
    >
      <router-link :to="{ name: 'home'}"><img style="padding-left: 10%;" src="@/assets/profile_assets/토키 로고.png" alt="로고 이미지"></router-link>
    </template>
    <v-app-bar-title id="menu_font">토키</v-app-bar-title>

    <v-text-field
      style="margin-right: 60px;"
      v-model="search"
      density="compact"
      label="Search"
      base-color="light-blue-lighten-1"
      single-line
      flat
      clearable
      hide-details
      variant="plain"
      @keyup.enter="searching"
    >
      <template
        v-slot:append
      >
        <v-icon
          :class="{'cursor-not-allowed': !search}"
          @click="searching"
        >
        mdi-magnify</v-icon>
      </template>
    </v-text-field>

    <BroadcastButton/>

    <template v-if="isLoggedIn">
      <v-btn style="margin-left: 20px; width: 90px;" @click="logoutAccount">logout</v-btn>
    </template>
    <template v-else>
      <LoginButton style="margin-left: 20px;"/>
    </template>


    <template
      v-slot:append
    >
      <SideVarButton @click="toggleDrawer"/>
    </template>
  </v-app-bar>
  <SideVarModal :drawer="drawer" @update-drawer="toggleDrawer"/>
</template>

<style scoped>

img {
  width: 50px;
  height: 50px;
  object-fit: cover;
}
#menu_font {
  color: rgb(159, 220, 225);
  padding-left: 3%;
  font-size: 20px;
  font-weight: 900;
}


</style>