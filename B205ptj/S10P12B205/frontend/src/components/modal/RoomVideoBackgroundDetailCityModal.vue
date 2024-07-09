<script setup>
import { ref, defineEmits } from 'vue'

// 부모 RoomVideoBackgroundModal.vue 으로 emit 발신
const emit = defineEmits(['selectBackground'])

const dialog = ref(false)

// 배경을 선택할 때, 함수
const selectBackground = function(background){
    emit('selectBackground', background)
    dialog.value = true
}
</script>
<template>
    <v-btn color="white" size="150px" @click="selectBackground('city-background')">
      <v-icon class="mdi mdi-city" size="100px"> </v-icon>
    </v-btn>

    <v-dialog
      v-model="dialog"
      width="auto"
      height="800px"
      transition="dialog-top-transition"
      no-click-animation
    >
      <v-sheet 
        width="800px" 
        height="800px" 
        color="black"                
        style="border-radius: 20px; border: 1px solid rgb(255, 255, 255);"
      >
        <!-- 배경 상태 표시줄 (맨 위) -->
        <v-toolbar
          color="black"                    
        >
          <v-toolbar-title                        
            style="margin-left: 50px;"
          >
            가상 배경 (도시)
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
            src="src\assets\city.jpg"
            cover
          >
            <v-card-title>도시 배경</v-card-title>
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