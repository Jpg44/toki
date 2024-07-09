<script setup>
import { ref, defineProps, defineEmits } from 'vue'
import RoomVideoBackgroundDetailCityModal from '@/components/modal/RoomVideoBackgroundDetailCityModal.vue';
import RoomVideoBackgroundDetailOfficeModal from '@/components/modal/RoomVideoBackgroundDetailOfficeModal.vue';
import RoomVideoBackgroundDetailHomeModal from '@/components/modal/RoomVideoBackgroundDetailHomeModal.vue';
import RoomVideoBackgroundDetailNatureModal from '@/components/modal/RoomVideoBackgroundDetailNatureModal.vue';

const props = defineProps({
    roomVideoBackgroundDialog: {
        type: Boolean,
        required: true,
    }
})

const emit = defineEmits(['update:roomVideoBackgroundDialog'])
const selectedBackground = ref('')

function closeModal() {
  emit('update:roomVideoBackgroundDialog', false)
}

// 자식 RoomVideoBackgroundDetail(가상배경)Modal.vue 에서 오는 emit 수신.
function selectBackground(background) {
    selectedBackground.value = background
    console.log(background)
}

</script>

<template>
    <div>
        <v-dialog
            v-model="props.roomVideoBackgroundDialog"
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

                <!-- 가상 배경 상태 표시줄 (맨 위) -->
                <v-toolbar
                    color="black"                    
                >
                    <v-toolbar-title                        
                        style="margin-left: 50px;"
                    >
                        가상 배경
                    </v-toolbar-title>
                </v-toolbar>

                <!-- 가상 배경 목록 -->
                <v-row
                    class="d-flex align-center justify-center"
                    style="margin-left: 10%; margin-right: 10%; margin-top: 30%; text-align: center;"             
                >
                    <!-- 도시 가상 배경 -->
                    <v-col>
                        <RoomVideoBackgroundDetailCityModal @select-background="selectBackground"/>
                        <br>
                        <br>
                        <p class="text-h6" style="white-space: nowrap;">
                            도시 배경
                        </p>
                    </v-col>

                    <!-- 사무실 배경 -->
                    <v-col>
                        <RoomVideoBackgroundDetailOfficeModal @select-background="selectBackground"/>
                        <br>
                        <br>
                        <p class="text-h6" style="white-space: nowrap;">
                            사무실 배경
                        </p>
                    </v-col>

                    <!-- 가정집 배경 -->
                    <v-col>
                        <RoomVideoBackgroundDetailHomeModal @select-background="selectBackground"/>
                        <br>
                        <br>
                        <p class="text-h6" style="white-space: nowrap;">
                            가정집 배경
                        </p>
                    </v-col>

                    <!-- 자연 배경 -->
                    <v-col>
                        <RoomVideoBackgroundDetailNatureModal @select-background="selectBackground"/>
                        <br>
                        <br>
                        <p class="text-h6" style="white-space: nowrap;">
                            자연 배경
                        </p>
                    </v-col>
                </v-row>
            </v-sheet>
        </v-dialog>
    </div>
</template>

<style scoped>

</style>