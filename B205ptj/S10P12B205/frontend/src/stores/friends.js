import { ref } from 'vue'
import { defineStore } from 'pinia'
import { friendListSearch, friendListAdd } from '@/js/Friend.js'

export const useFriendsStore = defineStore('friends', () => {
  // GET 친구 정보 조회
    // 친구 정보 할당
  const friendListSearchData = ref()
  const friendListSearchAxios = function(isFriend) {
    friendListSearch(
      isFriend,
      ({data}) => {console.log(data, '[Success] friends.js : friendListSearchAxios'); friendListSearchData.value = data;},
      (fail) => {console.log(fail, '[Error] friends.js : friendListSearchAxios');}
      )
  }

  // POST 친구 요청
  const friendListAddAxios = function(toUserTag) {
    friendListAdd(
      toUserTag,
      ({data}) => {console.log(data, '[Success] friends.js : friendListAddAxios');},
      (fail) => {console.log(fail, '[Error] friends.js : friendListAddAxios');}
    )    
  }

  return { friendListSearchAxios, friendListSearchData, friendListAddAxios }
}, { persist: true })
