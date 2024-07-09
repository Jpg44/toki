<script setup>
import { ref, defineProps, defineEmits } from 'vue'
import RoomSubMeetingDetailModal from '@/components/modal/RoomSubMeetingDetailModal.vue'

const props = defineProps({
    roomMeetingDialog: {
        type: Boolean,
        required: true,
    }
})

const emit = defineEmits(['update:roomMeetingDialog'])
const selectedMeeting = ref('')

function closeModal() {
  emit('update:roomMeetingDialog', false)
}

// 자식 RommSubMeetingDetailModal.vue 에서 오는 emit 수신.
function selectMeeting(number) {
    selectedMeeting.value = number
    console.log(selectedMeeting.value, ' 번 방')
}

</script>

<template>
    <div>
        <v-dialog
            v-model="props.roomMeetingDialog"
            @click:outside="closeModal"
            width="auto"
            height="1000px"                        
        >
            <v-sheet 
                width="1000px" 
                height="1000px" 
                color="black"                
                style="border-radius: 20px; border: 1px solid rgb(255, 255, 255);"
            >

                <!-- 소회의실 상태 표시줄 (맨 위) -->
                <v-toolbar
                    color="black"                    
                >
                    <v-toolbar-title                        
                        style="margin-left: 50px;"
                    >
                        소회의실
                    </v-toolbar-title>
                </v-toolbar>

                <!-- 소회의실 목록 -->
                <v-row
                    class="d-flex align-center justify-center"
                    style="margin-left: 10%; margin-right: 10%; margin-top: 20%; text-align: center;"             
                >
                  <!-- 소회의실 (1번 라인) -->
                  <v-row style="margin-bottom: 3%;">
                    <v-col v-for="i in [1, 2, 3, 4]" :key="i">
                      <RoomSubMeetingDetailModal @select-meeting="selectMeeting" :meeting-number="i"/>
                      <br>
                      <br>
                      <p class="text-h6" style="white-space: nowrap;">
                          소회의실 {{ i }}
                      </p>
                    </v-col>
                  </v-row>
                  
                  <!-- 소회의실 (2번 라인) -->
                  <v-row style="margin-bottom: 3%;">
                    <v-col v-for="i in [5, 6, 7, 8]" :key="i">
                      <RoomSubMeetingDetailModal @select-meeting="selectMeeting" :meeting-number="i"/>
                      <br>
                      <br>
                      <p class="text-h6" style="white-space: nowrap;">
                          소회의실 {{ i }}
                      </p>
                    </v-col>
                  </v-row>
                  
                </v-row>
            </v-sheet>
        </v-dialog>
    </div>
</template>

<style scoped>

</style>