<script setup>
import RoomComponent from '@/components/room_components/RoomComponent.vue'
import TokiRoom from '@/components/room_components/TokiRoom.vue';
import { ref } from 'vue';
import { onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
const route = useRoute()
const router = useRouter()
const roomData = JSON.parse(sessionStorage.getItem('roomData'))


const props = defineProps(
  {
    roomPk:String,
  }
)
const userName = ref('')
console.log(props.roomPk)
const roomInfoData = ref({roomPk:props.roomPk})
const userInfo = ref({name:null})
const enterRoom = () => {

  if (userName.value && userName.value !== '') {
    userInfo.value.name=userName.value
    isRoomEnter.value=false
  }

}

const exitRoom = () => {
  isRoomEnter.value = false
  router.push( { name: 'home'})
}

const isRoomEnter=ref(true)

onMounted(() => {
  console.log(roomData)
})

</script>

<template>
  <div>
    <v-container
    class="d-flex align-center justify-center flex-wrap text-center"
      style="min-width: 3000px;"
    >

      <v-dialog
       width="auto"
       :style="{backgroundColor: 'white'}"
       v-model="isRoomEnter">
        <v-card class="align-center justify-center flex-wrap text-center" style="border-radius: 20px; border: 1px solid white;" color="black" width="600" height="600">
          <v-row align="center" justify="center" class="fill-height">
            <v-col cols="12">
              <v-img
                height="290"
                :src="`https://picsum.photos/900/600?image=${route.params.roomPk * 5 + 10}`"
              >
              </v-img>
              <v-card-title id="font-check">
                <span>입장 하기</span>
              </v-card-title>
            </v-col>
            <v-col cols="12">
              <v-form @submit.prevent="enterRoom">
                <v-text-field v-model="userName" class="mb-2" clearable label="userName"></v-text-field>
            <!-- <v-text-field v-model="password" :type="'password'" clearable label="Password"></v-text-field> -->
              </v-form>
            </v-col>
            <v-col cols="12">
              <v-card-actions>
                <v-btn color="primary" block @click="exitRoom">취소</v-btn>
              </v-card-actions>
            </v-col>
          </v-row>
        </v-card>
    </v-dialog>
    </v-container>
  </div>
  
    <TokiRoom v-if="userInfo&&userInfo.name!==null" :roomInfo="roomInfoData" :userInfo="userInfo"/>
  
  <link rel="stylesheet" href="//cdn.jsdelivr.net/gh/neodgm/neodgm-pro-webfont@latest/neodgm_pro/style.css">
</template>

<style scoped>
/* .room-view {
  height: 1000px;
  width: 1000px;
} */
.font-neodgm-pro { 
    font-family: 'NeoDunggeunmo Pro'; 
}

#font-check {
    font-family: NeoDunggeunmo Pro;
}
</style>
