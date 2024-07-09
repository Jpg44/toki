<script setup>
import { ref, defineProps, defineEmits } from 'vue'

const props = defineProps({
    roomInviteDialog: {
        type: Boolean,
        required: true,
    }
})

const emit = defineEmits(['update:roomInviteDialog'])

function closeModal() {
  emit('update:roomInviteDialog', false)
}

// 전체 유저 목록 GET axios
const userList = ref([
  { text: '싸', icon: 'mdi-rabbit' },
  { text: '싸피', icon: 'mdi-rabbit' },
  { text: 'ssafy', icon: 'mdi-rabbit' },
  { text: 'SSAFY', icon: 'mdi-rabbit' },
  { text: 'sa', icon: 'mdi-rabbit' },
])

// 유저 검색할 때, 키워드
const inputValue = ref()

// 유저 검색 후, 선택한 유저
const dialogData = ref()

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

// 알람 내용
const alarm = ref()

// 초대 할 때, 함수
const inviteFunc = function(){
  if (dialogData.value === undefined){
    alarm.value = "초대할 사람을 클릭해주세여."
  } else {
    alarm.value = `${dialogData.value} 님이 초대 되었습니다.` 
  }
}
</script>

<template>
    <div>
        <v-dialog
            v-model="props.roomInviteDialog"
            @click:outside="closeModal"
            width="auto"
            height="1000px"                        
        >
            <v-sheet 
                width="1000px" 
                height="1000px" 
                color="indigo-lighten-5"                
                style="border-radius: 20px; border: 1px solid rgb(255, 255, 255);"
            >

                <!-- 초대 상태 표시줄 (맨 위) -->
                <v-toolbar
                    color="black"             
                >
                    <v-toolbar-title                        
                        style="margin-left: 50px;"
                    >
                        초대 하기
                    </v-toolbar-title>
                </v-toolbar>
                
                <!-- 초대 창 -->
                <v-container fluid>
                  <v-row
                    class="d-flex align-center= justify-center"
                    style="margin-left: 10%; margin-right: 10%; margin-top: 10%;"             
                  > 
                    <v-card min-width="800" style="border: solid 1px rgb(219, 219, 219);">
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
                      </v-list>
                    </v-card>
                  </v-row>

                  <v-row 
                    class="d-flex align-center justify-end" 
                    style="margin-left: 10%; margin-right: 10%; margin-top: 2%;"
                  >
                    <small v-show="dialogData===undefined" style="color: red;">{{ alarm }}</small>
                    <small v-show="dialogData!==undefined" style="color: rgb(0, 136, 199);">{{ alarm }}</small>
                    
                    <v-btn
                      class="ma-2 pa-2 rounded-lg" 
                      min-width="100" 
                      color="black"
                      @click="inviteFunc()"
                    >초대</v-btn>
                    <v-btn 
                      class="ma-2 pa-2 rounded-lg" 
                      min-width="100"
                      @click="closeModal()"
                    >취소</v-btn>
                  </v-row>

                </v-container>
            </v-sheet>
        </v-dialog>
    </div>
  <link href="https://cdn.jsdelivr.net/npm/@mdi/font@5.x/css/materialdesignicons.min.css" rel="stylesheet">
</template>

<style scoped>

</style>