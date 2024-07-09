<script setup>
import { ref } from 'vue'
import ProfileFiendsCreateModal from '@/components/modal/ProfileFiendsCreateModal.vue'
import { useFriendsStore } from '@/stores/friends';

// friends.js store 접근
const storeFriend = useFriendsStore()

// // 부모 ProfileComponent.vue 에서 보낸 props
// const friendListSearchProps = defineProps({
//   // 친구 목록 정보 데이터
//   friendListSearchData: Object,
// })

// // friendListSearchProps 데이터 목록, 낱개로 풀기
// const items = ref([
//   { type: 'subheader', title: '친구 리스트' },
// ])
// let itemsId = 0
// for (const data of friendListSearchProps.friendListSearchData) {
//   itemsId ++
//   const friend = {}
//   friend.id = itemsId
//   friend.prependAvatar = 'https://cdn.vuetifyjs.com/images/lists/1.jpg'
//   friend.title = data.userNickname
//   friend.subtitle = '자기소개'
//   items.value.push(friend) 
// }

// // DELTE 친구 삭제
// const friendDeleteAxios = function(pk){
//   friendDelete(
//     {friendPk: pk},
//     ({data}) => {console.log(data, '친구 삭제');},
//     (fail) => {console.log(fail, '[Error] ProfileMyFriendsComponent.vue : Friend3DeleteAxios')}
//   )
// }
const items = ref([
  {
    id: 1,
    prependAvatar: 'https://cdn.vuetifyjs.com/images/lists/1.jpg',
    title: '아이디 or 닉네임',
    subtitle: '친구 자기소개 내용 란 입니다.',
  },
  {
    id: 2,
    prependAvatar: 'https://cdn.vuetifyjs.com/images/lists/2.jpg',
    title: '아이디 or 닉네임',
    subtitle: '놀고 싶다. 평생 놀고 싶다. 돈 많은 백수가 되고 싶다!',
  },
  {
    id: 3,
    prependAvatar: 'https://cdn.vuetifyjs.com/images/lists/3.jpg',
    title: '아이디 or 닉네임',
    subtitle:
      '그렇게 아득바득 살고 싶어? 그냥 살아! 놀자!',
  },
  {
    id: 4,
    prependAvatar: 'https://cdn.vuetifyjs.com/images/lists/4.jpg',
    title: '아이디 or 닉네임',
    subtitle:
      '친구 해줘. 심심해 나랑 놀아줘',
  },
  {
    id: 5,
    prependAvatar: 'https://cdn.vuetifyjs.com/images/lists/5.jpg',
    title: '아이디 or 닉네임',
    subtitle:
      'ipsoumehcjoej fp[ekjfopefnkldji ejffdsdodojf]',
  },
])

</script>

<template>
  <v-container class="mt-3" fluid>
    <v-row>
      <v-col offset="1" cols="10">
        <v-card class="mx-auto">
          <div class="mt-7 mb-4 mr-10">
            {{ storeFriend.friendListSearchData.data }}
            <!-- 친구 추가 버튼 -->
            <ProfileFiendsCreateModal/>
          </div>

          <div class="v-list v-theme--light v-lt--density-default v-list--three-line" tabindex="0" role="listbox">
            <div class="v-list-subheader" type="subheader">
              <div class="v-list-subheader__text ml-2">친구 리스트</div>
            </div>
            <template v-for="item in items" :key="item.id">
              <div v-ripple class="v-list-item v-theme--light v-list-item--density-default v-list-item--three-line v-list-item--variant-text">
                <span class="v-list-item__underlay"></span>
                <div class="v-list-item__prepend">
                  <div class="v-avatar v-theme--light v-avatar--density-default v-avatar--size-default v-avatar--variant-flat">
                    <div class="v-responsive v-img" aria-label>
                      <div class="v-responsive__sizer" style="padding-bottom: 100%;"></div>
                      <!-- 이미지 url -->
                      <img class="v-img__img v-img__img--cover" :src="item.prependAvatar" alt style>
                    </div>
                    <span class="v-avatar__underlay"></span>
                  </div>
                  <div class="v-list-item__spacer"></div>
                </div>
                <div class="v-list-item__content" data-no-activator>
                  <!-- 아이디 닉네임 -->
                  <div class="v-list-item-title">{{ item.title }}</div>
                  <div class="v-list-item-subtitle">
                    <div class="d-flex justify-space-between">
                      <div>
                        <!-- 자기 소개 -->
                        <span class="text-primary">자기 소개</span>
                        <!-- 자기 소개 내용 -->
                        {{ item.subtitle }}
                      </div>
                      <v-btn class="ma-1 ml-7" color="blue-lighten-5">친구 삭제</v-btn>
                    </div>
                  </div>
                </div>
              </div>
            </template>
          </div>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>



<style scoped>

</style>