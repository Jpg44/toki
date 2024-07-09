<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user.js'
import { useAuthStore } from '@/stores/auth'
import { memberUpdate } from '@/js/Member.js'
import { badgeCreate } from '@/js/CommonAxios'

// user.js 에서 유저 정보 가져오기
const userStore = useUserStore()

// auth.js 에서 유저 태그 가져오기
const authStore = useAuthStore()

// 유저 정보 수정 request 데이터
const userData = ref({
  userNickname: '',
  profileImageUrl: '',
  selfInfo: ''
})

// 유저 정보 수정 함수
const memberUpdateAxios = async()=> {
  await memberUpdate(
    authStore.userTag,
    userData.value,
    ({data}) => {console.log(data, '[Success] ProfileMyDataComponent.vue : memberUpdateAxios');},
    (fail) => {console.log(fail, '[Error] ProfileMyDataComponent.vue : memberUpdateAxios');}
    ),
    goHome.push({ name: 'home' })
  }

// router push 를 위한 인스턴스 생성
const goHome = useRouter()

</script>

<template>
  <v-container fluid>
    <v-row>
      <v-col cols="12">
        <v-container id="profile_my_data" class="pl-7 pt-8 pb-10 font-weight-black text-h6 d-flex flex-column">
          <div>
              <p class="ml-4 mb-4">닉네임</p>
              <v-text-field label="닉네임 변경" :placeholder="userStore.memberDetailData.data.userNickName" v-model="userData.userNickname"></v-text-field>
          </div>
          <div>
              <p class="ml-4 mb-4">자기 소개</p>
              <v-textarea v-model="userData.selfInfo" counter maxlength="120" single-line :label="userStore.memberDetailData.data.selfInfo"></v-textarea>
          </div>
          <div>
              <p class="ml-4 mb-4">가입 날짜</p>
              <v-text-field disabled :label="userStore.memberDetailData.data.createAt"></v-text-field>
          </div>

          <div>
              <p class="ml-4 mb-4">가입 sns 유형</p>
              <v-text-field v-show="userStore.memberDetailData.data.snsType === 1" disabled label=" 카카오 계정 연동 중"></v-text-field>
              <v-text-field v-show="userStore.memberDetailData.data.snsType === 2" disabled label=" 네이버 계정 연동 중"></v-text-field>
          </div>

          <div class="mt-10 d-flex flex-row-reverse ga-5">
            <v-btn class="rounded-lg" variant="tonal">회원 탈퇴</v-btn>
            <v-btn class="font-weight-black rounded-lg" color="blue-lighten-1" @click="memberUpdateAxios">프로필 변경</v-btn>
          </div>

        </v-container>
      </v-col>
    </v-row>
  </v-container>
</template>



<style scoped>
#profile_my_data {
  background-color: rgb(238, 238, 238);
}
</style>