<script setup>
import { ref, defineEmits } from 'vue'
import dice from '@/components/game/dice.vue';
// 부모 RoomGameModal.vue 으로 emit 발신
const emit = defineEmits(['selectGame'])

const dialog = ref(false)

// 게임을 선택할 때, 함수
const selectGame = function(gameName){
    emit('selectGame', gameName)
    dialog.value = true
}
</script>
<template>
    <v-btn color="white" icon="" size="150px" @click="selectGame('dice')">
      <v-icon class="mdi mdi-dice-5" size="100px"> </v-icon>
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
            게임 (주사위)
          </v-toolbar-title>
        </v-toolbar>
        
        <v-card
          class="mx-auto"
          max-width="auto"
          color="white"
        >
          <!-- <v-img
            class="align-end text-white"
            height="auto"
            src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
            cover
          >
            <v-card-title>주사위 게임</v-card-title>
          </v-img> -->
          <div ><dice/></div>
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