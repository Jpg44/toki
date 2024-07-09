<script setup>
import { ref } from 'vue'
import ProfileMessageSendModal from '@/components/modal/ProfileMessageSendModal.vue'

// 전체 유저 목록 GET axios
const userList = ref([
  { text: '싸', icon: 'mdi-account' },
  { text: '싸피', icon: 'mdi-account' },
  { text: 'ssafy', icon: 'mdi-account' },
  { text: 'SSAFY', icon: 'mdi-account' },
  { text: 'sa', icon: 'mdi-account' },
])

// 유저 검색할 때, 키워드
const inputValue = ref()

// 유저 검색 후, 선택한 유저
const dialogData = ref()

// 유저 검색 모달창, On / Off 변수
const dialog = ref(false)

// 유저 검색 모달창 '닫기' 클릭
const dialogClose = function(){
  dialog.value = false
}

// 검색된 유저 목록
const items = ref([])

// 유저 검색 함수
const searchUser = function(inputValue) {
  for (const user of userList.value) {
    if (user.text.indexOf(inputValue)) {
      continue
    } else {
      items.value.push(user)
    }
  }
}

// 로딩 변수
const loaded = ref(false)
const loading = ref(false)

// 유저 검색할 때, 로딩 함수
const onSearch = function(user) {
  loading.value = true
  setTimeout(() => {
    loading.value = false
    loaded.value = true
  }, 2000)
  searchUser(user)
}
</script>

<template>
  <v-row justify="end">
    <v-dialog v-model="dialog" scrollable width="auto">
      <template v-slot:activator="{ props }">
        <v-btn color="purple" v-bind="props">쪽지 보내기</v-btn>
      </template>
      <v-card style="width: 500px;">
        <v-card-title class="ml-3 mt-3">유저 검색</v-card-title>

        <!-- 검색 창 -->
        <v-card-text>
          <v-text-field
            :loading="loading"
            density="compact"
            variant="solo"
            label="유저 찾기"
            append-inner-icon="mdi-magnify"
            single-line
            hide-details
            v-model="inputValue"
            @click:append-inner="onSearch(inputValue)"
            @keyup.enter="onSearch(inputValue)"
          ></v-text-field>
        </v-card-text>    

        <v-divider></v-divider>

        <!-- 닉네임 유저 조회 창 -->
        <v-list density="compact">
          <v-list-item
            v-for="(item, i) in items"
            :key="i"
            :value="item"
            color="primary"
            @click="dialogData=item.text"
          >
            <template v-slot:prepend>
              <v-icon :icon="item.icon"></v-icon>
            </template>
            <v-list-item-title v-text="item.text"></v-list-item-title>
          </v-list-item>
          <v-divider></v-divider>
          
          <v-card-actions>
            <v-btn color="purple-darken-1" variant="text" @click="dialogClose()">
              닫기
            </v-btn>
            <template v-if="dialogData === null"></template>
            <template v-else>
              <ProfileMessageSendModal :dialogData="dialogData"/>
            </template>
          </v-card-actions>
        </v-list>
      </v-card>
    </v-dialog>
  </v-row>
  <link href="https://cdn.jsdelivr.net/npm/@mdi/font@5.x/css/materialdesignicons.min.css" rel="stylesheet">
</template>

