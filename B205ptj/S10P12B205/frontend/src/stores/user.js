import { ref } from 'vue'
import { defineStore } from 'pinia'
import { useAuthStore } from '@/stores/auth.js'
import { memberDetail, memberSimple, otherMemberDetail } from '@/js/Member.js'

export const useUserStore = defineStore('user', () => {
  // pinia 유저 Tag 가져오기
  let userTags = null
  if (useAuthStore().user === null || useAuthStore().user === undefined) {
    console.log('로그인 되지 않았습니다. / user.js')
  } else {
    userTags = useAuthStore().userTag
  }

  // GET 유저 정보 상세 조회
    // 유저 상세 정보 할당
  const memberDetailData = ref()
  const memberDetailAxios = function() {
    if (userTags === null || userTags === undefined) {
        console.log('로그인을 하지 않아서, 유저 상세 정보 조회 memberDetailAxios 함수가 작동하지 않습니다.')
      }
    else {
      memberDetail(
        userTags,
        ({data}) => {console.log(data, '[Success] user.js : memberDetailAxios'); memberDetailData.value = data;},
        (fail) => {console.log(fail, '[Error] user.js : memberDetailAxios');}
        )
    }
  }

  // GET 유저 정보 간단 조회
    // 유저 간단 정보 할당
  const memberSimpleData = ref()
  const memberSimpleAxios = function() {
    if (userTags === null || userTags === undefined) {
      console.log('로그인을 하지 않아서, 유저 간단 정보 조회 memberSimpleAxios 함수가 작동하지 않습니다.')
    }
    else {
      memberSimple(
        userTags,
        ({data}) => {console.log(data, '[Success] user.js : memberSimpleAxios'); memberSimpleData.value = data},
        (fail) => {console.log(fail, '[Error] user.js : memberSimpleAxios');}
      )
    }
  }

  // GET 다른 유저 정보 조회
    // 다른 유저 정보 할당
  const otherMemberDetailData = ref()
  const otherMemberDetailAxios = function(toUserTags) {
    otherMemberDetail(
      toUserTags,
      ({data}) => {console.log(data, '[Success] user.js : otherMemberDetailAxios'); otherMemberDetailData.value = data},
      (fail) => {console.log(fail, '[Error] user.js : otherMemberDetailAxios');}
    )
  }

  return { memberDetailAxios, memberDetailData, memberSimpleAxios, memberSimpleData, otherMemberDetailAxios, otherMemberDetailData }
}, { persist: true })
