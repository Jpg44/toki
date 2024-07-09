<script setup>
import { ref, defineProps, defineEmits } from 'vue'
import RoomVoiceChangeDetailOlderModal from '@/components/modal/RoomVoiceChangeDetailOlderModal.vue'
import RoomVoiceChangeDetailBabyModal from '@/components/modal/RoomVoiceChangeDetailBabyModal.vue';
import RoomVoiceChangeDetailWomanModal from '@/components/modal/RoomVoiceChangeDetailWomanModal.vue';
import RoomVoiceChangeDetailManModal from '@/components/modal/RoomVoiceChangeDetailManModal.vue';

const props = defineProps({
    roomVoiceChangeDialog: {
        type: Boolean,
        required: true,
    }
})

const emit = defineEmits(['update:roomVoiceChangeDialog'])
const selectedVoice = ref('')

function closeModal() {
  emit('update:roomVoiceChangeDialog', false)
}

// 자식 RommVoiceChangeDetail(음성변조)Modal.vue 에서 오는 emit 수신.
function selectVoice(voice) {
    selectedVoice.value = voice
    console.log(voice)
}

</script>

<template>
    <div>
        <v-dialog
            v-model="props.roomVoiceChangeDialog"
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

                <!-- 음성 변조 상태 표시줄 (맨 위) -->
                <v-toolbar
                    color="black"                    
                >
                    <v-toolbar-title                        
                        style="margin-left: 50px;"
                    >
                        음성 변조
                    </v-toolbar-title>
                </v-toolbar>

                <!-- 음성 변조 목록 -->
                <v-row
                    class="d-flex align-center justify-center"
                    style="margin-left: 10%; margin-right: 10%; margin-top: 25%; text-align: center;"             
                >
                    <!-- 노인 -->
                    <v-col>
                        <RoomVoiceChangeDetailOlderModal @select-voice="selectVoice"/>
                        <br>
                        <br>
                        <p class="text-h6" style="white-space: nowrap;">
                            노인 음성
                        </p>
                    </v-col>

                    <!-- 어린 아이 -->
                    <v-col>
                        <RoomVoiceChangeDetailBabyModal @select-voice="selectVoice"/>
                        <br>
                        <br>
                        <p class="text-h6" style="white-space: nowrap;">
                            아이 음성
                        </p>
                    </v-col>

                    <!-- 성인 여성 -->
                    <v-col>
                        <RoomVoiceChangeDetailWomanModal @select-voice="selectVoice"/>
                        <br>
                        <br>
                        <p class="text-h6" style="white-space: nowrap;">
                            여성 음성
                        </p>
                    </v-col>

                    <!-- 성인 남성 -->
                    <v-col>
                        <RoomVoiceChangeDetailManModal @select-voice="selectVoice"/>
                        <br>
                        <br>
                        <p class="text-h6" style="white-space: nowrap;">
                            남성 음성
                        </p>
                    </v-col>
                </v-row>
            </v-sheet>
        </v-dialog>
    </div>
</template>

<style scoped>

</style>