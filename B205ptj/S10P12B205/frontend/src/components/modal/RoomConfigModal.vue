<script setup>
import { ref, defineProps, defineEmits } from 'vue'
import RoomGameDetailLadderModal from '@/components/modal/RoomGameDetailLadderModal.vue'
import RoomGameDetailDiceModal from '@/components/modal/RoomGameDetailDiceModal.vue'
import RoomGameDetailDrawLotsModal from '@/components/modal/RoomGameDetailDrawLotsModal.vue'
import RoomGameDetailTaboModal from '@/components/modal/RoomGameDetailTaboModal.vue'

const props = defineProps({
    roomConfigDialog: {
        type: Boolean,
        required: true,
    }
})

const emit = defineEmits(['update:roomConfigDialog'])
const selectedGame = ref('')

function closeModal() {
  emit('update:roomConfigDialog', false)
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
            v-model="props.roomConfigDialog"
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
                <!-- 환경 설정 표시줄 (맨 위) -->
                <v-toolbar
                    color="black"                    
                >
                    <v-toolbar-title                        
                        style="margin-left: 50px;"
                    >
                        환경 설정
                    </v-toolbar-title>
                </v-toolbar>
            </v-sheet>
        </v-dialog>
    </div>
</template>

<style scoped>

</style>