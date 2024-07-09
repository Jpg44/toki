<script setup>
import { ref, defineProps, defineEmits } from 'vue'
import RoomGameDetailLadderModal from '@/components/modal/RoomGameDetailLadderModal.vue'
import RoomGameDetailDiceModal from '@/components/modal/RoomGameDetailDiceModal.vue'
import RoomGameDetailDrawLotsModal from '@/components/modal/RoomGameDetailDrawLotsModal.vue'
import RoomGameDetailTaboModal from '@/components/modal/RoomGameDetailTaboModal.vue'

const props = defineProps({
    roomGameDialog: {
        type: Boolean,
        required: true,
    }
})

const emit = defineEmits(['update:roomGameDialog'])
const selectedGame = ref('')

function closeModal() {
  emit('update:roomGameDialog', false)
}

// 자식 RommGameDetail(게임)Modal.vue 에서 오는 emit 수신.
function selectGame(gameName) {
    selectedGame.value = gameName
    console.log(gameName)
}

</script>

<template>
    <div>
        <v-dialog
            v-model="props.roomGameDialog"
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

                <!-- 게임 상태 표시줄 (맨 위) -->
                <v-toolbar
                    color="black"                    
                >
                    <v-toolbar-title                        
                        style="margin-left: 50px;"
                    >
                        게임
                    </v-toolbar-title>
                </v-toolbar>

                <!-- 게임 목록 -->
                <v-row
                    class="d-flex align-center justify-center"
                    style="margin-left: 10%; margin-right: 10%; margin-top: 25%; text-align: center;"             
                >
                    <!-- 사다리 게임 -->
                    <v-col>
                        <RoomGameDetailLadderModal @select-game="selectGame"/>
                        <br>
                        <br>
                        <p class="text-h6" style="white-space: nowrap;">
                            사다리 게임
                        </p>
                    </v-col>

                    <!-- 주사위 게임 -->
                    <v-col>
                        <RoomGameDetailDiceModal @select-game="selectGame"/>
                        <br>
                        <br>
                        <p class="text-h6" style="white-space: nowrap;">
                            주사위 게임
                        </p>
                    </v-col>

                    <!-- 제비 뽑기 -->
                    <v-col>
                        <RoomGameDetailDrawLotsModal @select-game="selectGame"/>
                        <br>
                        <br>
                        <p class="text-h6" style="white-space: nowrap;">
                            룰렛
                        </p>
                    </v-col>

                    <!-- 단어 맞추기 -->
                    <v-col>
                        <RoomGameDetailTaboModal @select-game="selectGame"/>
                        <br>
                        <br>
                        <p class="text-h6" style="white-space: nowrap;">
                            단어 맞추기
                        </p>
                    </v-col>
                </v-row>
            </v-sheet>
        </v-dialog>
    </div>
</template>

<style scoped>

</style>