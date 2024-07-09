<script setup>
import { ref,onMounted,watch } from 'vue'
import {Client} from '@stomp/stompjs';
const props = defineProps({userName:String});
const items = ref([
    { 'name': '토키1', 'content': 'good day' },
    { 'name': '토키2', 'content': 'bed day' },
])
const message = ref('')
const stompClient = ref(new Client());

const chat = {
    chatType: "COMMONCHAT",
    sendTo: "ALL",
    fromUser: "",
    content: " "
  }
  console.log("dksanlnkamslmf;am;mf;aml;fm;alm",props.userName);
  const chatM=ref("")
  const chatU=ref(props.userName.name);
//채팅 열자마자 연결
const clearMessage =()=>{
    chatM.value = ''
}
onMounted(() => {
  console.log("로딩") 
  

  stompClient.value.brokerURL='wss://i10b205.p.ssafy.io/ws/chat',
  stompClient.value.onConnect=()=>{
  console.dir("연결시도");
  stompClient.value.subscribe('/subChat/room/ccc',
   message => items.value.push({ 'name': JSON.parse( message.body).fromUser, 'content': JSON.parse( message.body).content})
  //console.log(JSON.parse( message.body).content)
  );
  stompClient.value.debug((debug) => {
      console.log(debug)
    })

}
    stompClient.value.activate();
  })
//connect();
const onMessage = (message) => {
  console.log(message)
}
const test = ()=>{
  console.log("Ddd")
  chat.content=chatM.value;
  chat.fromUser=chatU.value;
  stompClient.value.publish({ destination: '/pubChat/room/ccc', body: JSON.stringify(chat) }, onMessage);
  stompClient.value.debug((debug) => {
      console.log(debug)
    })
    clearMessage();
}
const virtualScroll = ref(null)
// 채팅 스크롤을 가장 아래로 이동
const handleScroll = () => {
  virtualScroll.value.$el.scrollTop = virtualScroll.value.$el.scrollHeight;
}
// 채팅 데이터 변화될 때, 모니터링
watch(items.value, () => {
   handleScroll()
})
</script>

<template>
  <v-card class="mx-auto h-100" max-width="500">
    <v-virtual-scroll :items="items" height="500" item-height="48" ref="virtualScroll">
      <template v-slot:default="{ item }">
        <v-list-item >
          <v-list-item-title style="white-space: pre-wrap; word-wrap: break-word;">{{ item.content }}</v-list-item-title>
          <v-list-item-subtitle>닉네임 : {{ item.name }}</v-list-item-subtitle>
          <template v-slot:prepend>
            <v-icon class="bg-primary">mdi-rabbit</v-icon>
          </template>
        </v-list-item>
        
      </template>
    </v-virtual-scroll>

    <v-divider></v-divider>

    <v-form @submit.prevent>
      <v-container>
        <v-row>
          <v-col cols="12">
            <v-text-field
              v-model="chatM"
              append-icon="mdi-send"
              prepend-icon="mdi-rabbit"
              variant="filled"
              clear-icon="mdi-close-circle"
              clearable
              label="채팅 입력"
              type="text"
              @click:append="test()"
              @click:clear="clearMessage"
              @keyup.enter="test()"
            ></v-text-field>
          </v-col>
        </v-row>
      </v-container>
    </v-form>
    <!-- {{ items }} -->
    <!-- <input type="text" v-model="chatU" > -->
  </v-card>
</template>
  


<style scoped>
</style>