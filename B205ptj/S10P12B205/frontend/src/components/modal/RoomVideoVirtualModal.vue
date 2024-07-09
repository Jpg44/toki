<script setup>
import { ref, defineProps, defineEmits } from 'vue'
import RoomVideoVirtualDetail1Modal from '@/components/modal/RoomVideoVirtualDetail1Modal.vue';
import RoomVideoVirtualDetail2Modal from '@/components/modal/RoomVideoVirtualDetail2Modal.vue';
import RoomVideoVirtualDetail3Modal from '@/components/modal/RoomVideoVirtualDetail3Modal.vue';
import RoomVideoVirtualDetail4Modal from '@/components/modal/RoomVideoVirtualDetail4Modal.vue';

const props = defineProps({
    roomVideoVirtualDialog: {
        type: Boolean,
        required: true,
    }
})

const emit = defineEmits(['update:roomVideoVirtualDialog'])
const selectedVirtual = ref('')

function closeModal() {
  emit('update:roomVideoVirtualDialog', false)
}

// 자식 RommVoiceChangeDetail(음성변조)Modal.vue 에서 오는 emit 수신.
function selectVirtual(virtual) {
    selectedVirtual.value = virtual
    console.log(virtual)
}

</script>

<template>
    <div>
        <v-dialog
            v-model="props.roomVideoVirtualDialog"
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

                <!-- 버츄얼 적용 상태 표시줄 (맨 위) -->
                <v-toolbar
                    color="black"                    
                >
                    <v-toolbar-title                        
                        style="margin-left: 50px;"
                    >
                        버츄얼 적용
                    </v-toolbar-title>
                </v-toolbar>

                <!-- 버츄얼 목록 -->
                <v-row
                    class="d-flex align-center justify-center"
                    style="margin-left: 10%; margin-right: 10%; margin-top: 30%; text-align: center;"             
                >
                    <!-- 버츄얼 1 -->
                    <v-col>
                        <RoomVideoVirtualDetail1Modal @select-virtual="selectVirtual"/>
                        <br>
                        <br>
                        <p class="text-h6" style="white-space: nowrap;">
                            버츄얼 1
                        </p>
                    </v-col>

                    <!-- 버츄얼 2 -->
                    <v-col>
                        <RoomVideoVirtualDetail2Modal @select-virtual="selectVirtual"/>
                        <br>
                        <br>
                        <p class="text-h6" style="white-space: nowrap;">
                            버츄얼 2
                        </p>
                    </v-col>

                    <!-- 버츄얼 3 -->
                    <v-col>
                        <RoomVideoVirtualDetail3Modal @select-virtual="selectVirtual"/>
                        <br>
                        <br>
                        <p class="text-h6" style="white-space: nowrap;">
                            버츄얼 3
                        </p>
                    </v-col>

                    <!-- 버츄얼 4 -->
                    <v-col>
                        <RoomVideoVirtualDetail4Modal @select-virtual="selectVirtual"/>
                        <br>
                        <br>
                        <p class="text-h6" style="white-space: nowrap;">
                            버츄얼 4
                        </p>
                    </v-col>
                </v-row>
            </v-sheet>
        </v-dialog>
    </div>
</template>

<style scoped>

</style>