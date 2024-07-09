<script setup>
import { ref, defineEmits, defineProps } from 'vue'

// 부모 RoomSubMeetingModal.vue 에서 받은 porps
defineProps({
    meetingNumber: Number
})

// 부모 RoomSubMeetingModal.vue 으로 emit 발신
const emit = defineEmits(['selectMeeting'])

const dialog = ref(false)

// 소회의실을 선택할 때, 함수
const selectMeeting = function(number){
    emit('selectMeeting', number)
    dialog.value = true
}
</script>
<template>
    <v-btn color="white" size="150px" @click="selectMeeting(meetingNumber)">
      <v-icon class="mdi mdi-rabbit" size="100px" color="blue-lighten-2"> </v-icon>
    </v-btn>

    <v-dialog
      v-model="dialog"
      width="auto"
      height="800px"
      persistent
      transition="dialog-top-transition"
      no-click-animation
    >
      <v-sheet 
        width="800px" 
        height="800px" 
        color="black"                
        style="border-radius: 20px; border: 1px solid rgb(255, 255, 255);"
      >
        <!-- 게임 상태 표시줄 (맨 위) -->
        <v-toolbar
          color="black"                    
        >
          <v-toolbar-title                        
            style="margin-left: 50px;"
          >
            소회의실 {{ meetingNumber }}
          </v-toolbar-title>
        </v-toolbar>
        
        <v-card
          class="mx-auto"
          max-width="auto"
          color="black"
        >
          <v-img
            class="align-end text-white"
            height="auto"
            src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
            cover
          >
            <v-card-title>{{ meetingNumber }}번 방</v-card-title>
          </v-img>
  
          <v-card-actions>
          </v-card-actions>
          <v-btn 
            color="primary"
            block @click="dialog = false"
            class="text-h6 font-weight-bold"
          >
            닫기
          </v-btn>
        </v-card>
      </v-sheet>
    </v-dialog>
</template>
  
<style scoped>

</style>