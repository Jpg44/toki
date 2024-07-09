<script setup>
import { ref, watch, defineProps, defineEmits, computed } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import { useUserStore } from '@/stores/user';

const authStore = useAuthStore()
const userStore = useUserStore()

const isLoggedIn = computed(() => authStore.isAuthenticated)

const props = defineProps({
  drawer: {
    type: Boolean,
    required: true,
  }
})

const emits = defineEmits(['update-drawer'])

const drawer = ref(props.drawer)

const router = useRouter()

const selectedCategory = ref('')

const category = ['게임', '공부', '스포츠', '잡담', '음악', '자유(기타)', ]

const navigateCategory = (value) => {
  if (value) {
    router.push({ name: 'search', query: { category: selectedCategory.value, page: 1 }})
    drawer.value = false
  }
}

const goToHome = () => {
  router.push({ name: 'home' })
  drawer.value = false
}

const goToRoom = () => {
  router.push({ name: 'rooms', query: { page: 1 }})
  drawer.value = false
}

watch(selectedCategory, navigateCategory)

watch(() => props.drawer, (newValue) => {
  drawer.value = newValue
})

watch(drawer, (newValue) => {
  if (newValue != props.drawer) {
    emits('update-drawer', newValue)
  }
})

// auth.js 에서 유저 태그
const storeAuth = useAuthStore()

</script>

<template>
  <div>

    <v-navigation-drawer
      v-model="drawer"
      location="right"
      temporary
    >

    <div style="display: flex; align-items: center; margin-top: 10px; margin-bottom: 10px;">
      <v-btn
      variant="text"
      icon="mdi-chevron-right"
      @click.stop="drawer = !drawer"
      
      >
      </v-btn>
      <RouterLink v-if="storeAuth.userTag === null || storeAuth.userTag === undefined" :to="{ name: 'home'}">
        <img src="@/assets/profile_assets/프로필.png" style="width: 40px; height: 40px; border-radius: 50%; margin-left: 1px;">
        <div>
            <h4 style="margin-left: 10px;">5반 유저</h4>
        </div>
      </RouterLink>
      <RouterLink v-else :to="{ name: 'profile', params: { tag: storeAuth.userTag}}">
        <img src="@/assets/profile_assets/프로필.png" style="width: 40px; height: 40px; border-radius: 50%; margin-left: 1px;">
        <div>
            <h4 style="margin-left: 10px;">5반 유저</h4>
        </div>
      </RouterLink>

    </div>

    <v-divider></v-divider>

    <v-list density="compact" nav>
      <v-list-item 
        value="home"
        @click="goToHome"
      >
        <div style="display: flex; align-items: center;">
          <img src="@/assets/profile_assets/토키 로고.png" alt="토키 로고" style="width: 45px; margin-right: 12px; margin-left: -8px;">
          <div>
            <h5>home</h5>
          </div>

        </div>
      </v-list-item>
      <v-list-item 
        value="all"
        @click="goToRoom"
       >
        <div style="display: flex; align-items: center;">
          <img src="@/assets/profile_assets/비디오.png" alt="전체" style="width: 50px; margin-right: 8px; margin-left: -10px;">
          <div>
            <h5>전체</h5>
          </div>

        </div>
        
      </v-list-item>
      <v-list-item value="fav">
        <div style="display: flex; align-items: center;">
          <img src="@/assets/profile_assets/즐겨찾기.png" alt="즐겨찾기" style="width: 30px; height: 30px; margin-right: 18px;">
          <div>
            <h5>추천 동영상</h5>
          </div>

        </div>
      </v-list-item>
      <v-list-item value="category" style="height: 10px;">
        <div style="display: flex; align-items: center;">
          <img src="@/assets/profile_assets/카테고리.png" alt="카테고리" style="width: 35px; margin-right: 15px; margin-left: 0px;">
          <v-select
          class="mt-3"
          clearable
          density="compact"
          :items="category"
          v-model="selectedCategory"
          variant="plain"
          
          ></v-select>

        </div>
        
      </v-list-item>
      <!-- <v-list-item prepend-icon="mdi-forum" title="About" value="about"></v-list-item> -->
    </v-list>
    </v-navigation-drawer>

  </div>

</template>

<style scoped>
.v-navigation-drawer {
  z-index: 2400; 
}
</style>
