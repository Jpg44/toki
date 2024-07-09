<script setup>
import { computed, onMounted, onUnmounted, reactive, ref } from 'vue'
import { roomUpdate } from '@/js/Room';
import RoomMeetingExitModal from '../modal/RoomMeetingExitModal.vue'
import RoomChatComponent from '@/components/room_components/RoomChatComponent.vue'

// 채팅 확장
const chatBox = ref(false)

const micOnOff  = ref(false)
const videoOnOff = ref(false)
const recordOnOff = ref(false)
const menuEmoticonOpen = ref(false)
const roomGameDialog = ref(false)
const roomMeetingDialog = ref(false)

const exitDialog = ref(false)

const openExitMeetingDialog = () => {
  exitDialog.value = !exitDialog.value
}

const openGameDialog = () => {
  roomGameDialog.value = !roomGameDialog.value
}

const oepnMeetingDialog = () => {
  roomMeetingDialog.value = !roomMeetingDialog.value
}
const updateRoom = () => {
  roomUpdate(
    roomId,
    (success) => {
      console.log(success)
    },
    (error) => {
      console.log(error)
    }
  )
}


const screenWidth = ref(window.innerWidth)

function handleResize() {
  screenWidth.value = window.innerWidth
}

onMounted(() => {
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
})

const isLagerScreen = computed(() => screenWidth.value >= 1280)

const colSize = computed(() => isLagerScreen.value ? 2 : 1)
const colOffset = computed(() => isLagerScreen.value ? 0 : 1)

</script>
<template>
  <v-container id="enter" class="h-100" style="min-width: 600px;">
    <v-row class="h-100">
      <!-- 화면 -->
      <v-col id="screen" style="border-radius: 10px;">
        <!-- 위 서브 화면 -->
        <v-row class="mt-1" style="height: 40%;">
          
        </v-row>
        <!-- 메인 화면 -->
        <v-row style="height: 10%;">

        </v-row>
        <!-- 아래 서브 화면 -->
        <v-row class="mb-1" style="height: 40%;">
          
        </v-row>


        <!-- 영상 옵션 바 -->
        <v-row class="mt-1 ml-1 mr-1" >
          
          <v-col
            justify="center"
            :cols="colSize"
            :offset="colOffset"
          >
            <div class="mx-4 hidden-sm-and-down"></div>
                <v-btn
                  v-if="isLagerScreen"
                  :prepend-icon="micOnOff? 'mdi-microphone' : 'mdi-microphone-off'"
                  :color="micOnOff? 'green-lighten-1' : 'black'"
                  class="ma-2"
                  @click="micOnOff = !micOnOff"
                  size="x-large"
                  style="border-radius: 30px; border: 1px solid white;"
                >
                  마이크
                </v-btn>
                <v-btn
                  v-else
                  class="ma-2"
                  :color="micOnOff? 'green-lighten-1' : 'black'"
                  :icon="micOnOff? 'mdi-microphone' : 'mdi-microphone-off'"
                  @click="micOnOff = !micOnOff"
                  width="100%"
                  size="large"
                  style="border: 1px solid white;"
                >
                </v-btn>
          </v-col>

          <v-col
            justify="center"
            :cols="colSize"
            :offset="colOffset"
          >
            <div class="mx-4 hidden-sm-and-down"></div>

                <v-btn
                  v-if="isLagerScreen"
                  :prepend-icon="videoOnOff? 'mdi-video' : 'mdi-video-off'"
                  :color="videoOnOff? 'green-lighten-1' : 'black'"
                  class="ma-2 "
                  @click="videoOnOff = !videoOnOff"
                  size="x-large"
                  style="border-radius: 30px; border: 1px solid white;"
                >
                  캠
                </v-btn>
                <v-btn
                  v-else
                  class="ma-2"
                  :color="videoOnOff? 'green-lighten-1' : 'black'"
                  :icon="videoOnOff? 'mdi-video' : 'mdi-video-off'"
                  @click="videoOnOff = !videoOnOff"
                  size="large"
                  style="border: 1px solid white;"
                >

                </v-btn>
          </v-col>

           <v-col
            justify="center"
            :cols="colSize"
            :offset="colOffset"
          >
            <div class="mx-4 hidden-sm-and-down"></div>
                <v-btn
                  v-if="isLagerScreen"
                  :prepend-icon="recordOnOff? 'mdi-radiobox-marked' : 'mdi-radiobox-blank'"
                  :color="recordOnOff? 'red-lighten-1' : 'black'"
                  class="ma-2 "
                  @click="recordOnOff = !recordOnOff"
                  size="x-large"
                  style="border-radius: 30px; border: 1px solid white;"
                >
                  녹화
                </v-btn>
                <v-btn
                  v-else
                  class="ma-2"
                  :color="recordOnOff? 'red-lighten-1' : 'black'"
                  :icon="recordOnOff? 'mdi-radiobox-marked' : 'mdi-radiobox-blank'"
                  @click="recordOnOff = !recordOnOff"
                  size="large"
                  style="border: 1px solid white;"
                >
                </v-btn>
          </v-col>


          <!-- 이모티콘 -->
          <!-- <v-col cols="1" sm="2">
            <v-expansion-panels>
              <v-expansion-panel bg-color="black" style="border-radius: 30px; border: 1px solid white;">
                <v-expansion-panel-title expand-icon="mdi-menu-down">
                  <svg-icon type="mdi" :path="pathEmoticonOutline"></svg-icon>
                  <p class="ml-5">이모티콘</p>
                </v-expansion-panel-title>
                <v-expansion-panel-text>
                  <v-chip-group>
                    <v-chip value="화면녹화">😀</v-chip>
                    <v-chip value="화면녹화">😀</v-chip>
                    <v-chip value="화면녹화">😀</v-chip>
                    <v-chip value="화면녹화">😀</v-chip>
                    <v-chip value="화면녹화">😀</v-chip>
                  </v-chip-group>
                </v-expansion-panel-text>
              </v-expansion-panel>
            </v-expansion-panels>
          </v-col> -->

          <v-col
            justify="center"
            cols="1"
            offset="1"
          >
            <div class="mx-4 hidden-sm-and-down"></div>

            <v-menu location="top" v-model="menuEmoticonOpen">
              <template v-slot:activator="{ props }">
                <v-btn
                  class="ma-2"
                  color="black"
                  v-bind="props"
                  icon="mdi-emoticon-outline"
                  size="large"
                  style="border: 1px solid white;"
                >

                </v-btn>
              </template>              
              <v-list bg-color="black" style="text-align: center;">                  
                <v-list-item-group multiple>
                  <v-row>
                    <v-col cols="12">
                      <v-chip-group row>
                        <v-chip value="화면녹화">😀</v-chip>
                        <v-chip value="화면녹화">😀</v-chip>
                        <v-chip value="화면녹화">😀</v-chip>
                        <v-chip value="화면녹화">😀</v-chip>
                      </v-chip-group>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="12">
                      <v-chip-group row>
                        <v-chip value="화면녹화">😀</v-chip>
                        <v-chip value="화면녹화">😀</v-chip>
                        <v-chip value="화면녹화">😀</v-chip>
                        <v-chip value="화면녹화">😀</v-chip>
                      </v-chip-group>
                    </v-col>
                  </v-row>
                </v-list-item-group>
              </v-list>
            </v-menu>
          </v-col>

          <v-col
            justify="center"
            cols="2"
          >
            <div class="mx-4 hidden-sm-and-down"></div>
                <v-btn
                  class="ma-2"
                  color="black"
                  prepend-icon="mdi-exit-to-app"
                  @click="openExitMeetingDialog"
                  size="x-large"
                  style="border-radius: 30px; border: 1px solid white;"
                >
                나가기
                </v-btn>
          </v-col>
          
          
          <!-- 채팅 -->
          <v-col
            justify="center"
            :cols="colSize"
            :offset="colOffset"
          >
            <!-- <div class="mx-4 hidden-sm-and-down"></div> -->
          
            <v-btn
              v-if="isLagerScreen"
              prepend-icon="mdi-chat-processing-outline"
              color="black"
              class="ma-2 "
              v-bind="props"
              size="x-large"
              style="border-radius: 10px; border: 1px solid white;"
              @click="chatBox = !chatBox"
            >
              채팅
            </v-btn>
              
            <v-btn
              v-else
              class="ma-2"
              color="black"
              v-bind="props"
              icon="mdi-chat-processing-outline"
              size="large"
              style="border-radius: 10px; border: 1px solid white;"
              
            >

            </v-btn>
              

            
          </v-col>


          <!-- <v-col>
            <v-expansion-panels @click="chatBox = !chatBox">
              <v-expansion-panel bg-color="black" style="border-radius: 10px; border: 1px solid white;">
                <v-expansion-panel-title collapse-icon="mdi-rabbit-variant-outline" expand-icon="mdi-menu-right">
                  <svg-icon type="mdi" :path="pathChatProcessingOutline"></svg-icon>
                  <p>채팅</p>
                </v-expansion-panel-title>
              </v-expansion-panel>
            </v-expansion-panels>
          </v-col> -->
        </v-row>

        <!-- 영상 타이틀 -->
        <v-row>
          <v-col cols="2">
            <v-sheet>닉네임</v-sheet>
          </v-col>
          <v-col cols="2">
            <v-sheet>화상 채팅 방 제목</v-sheet>
          </v-col>
        </v-row>
      </v-col>

      
      <!-- 채팅 화면 -->
      <v-col v-if="chatBox" id="chatt" cols="3">
        <v-row class="h-100">
          <v-col><RoomChatComponent/></v-col>
        </v-row>
      </v-col>
    </v-row>



    <RoomMeetingExitModal 
      :exitDialog="exitDialog"
      @update:exitDialog="exitDialog = $event"
    />

  </v-container>
  <link href="https://cdn.jsdelivr.net/npm/@mdi/font@5.x/css/materialdesignicons.min.css" rel="stylesheet">
</template>



<style scoped>
#main-screen {
  background-color: rgb(167, 111, 169);
}

#screen {
  background-color: rgb(0, 0, 0);
  white-space: nowrap;
}
#chatt {
  background-color: aqua;
}

/* 작은 화면 */
#biggBG1 {
  background-color: antiquewhite;
}
#biggBG2 {
  background-color: aquamarine;
}
#biggBG3 {
  background-color: rgb(186, 222, 176);
}
#biggBG4 {
  background-color: rgb(232, 255, 127);
}
#biggBG5 {
  background-color: rgb(255, 163, 127);
}

</style>