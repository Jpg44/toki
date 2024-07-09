<script setup>
import { ref, defineEmits } from 'vue'

// 부모 RoomVoiceChangeModal.vue 으로 emit 발신
const emit = defineEmits(['selectVoice'])

const dialog = ref(false)

// 음성 변조를 선택할 때, 함수
const selectVoice = function(voice){
    emit('selectVoice', voice)
    dialog.value = true
}

</script>
<template>
    <v-btn color="white" icon="" size="150px" @click="selectVoice('older-voice')">
      <v-icon class="mdi mdi-human-cane" size="100px"> </v-icon>
    </v-btn>

    <v-dialog
      v-model="dialog"
      width="auto"
      transition="dialog-top-transition"
    >
      <v-card
        color="black"
        style="border-radius: 20px; border: 1px solid rgb(255, 255, 255);"
      >
        <v-card-text>
          <v-icon class="mdi mdi-human-cane" size="100px"> </v-icon>
          노인 목소리로 음성 변조 되었습니다.
        </v-card-text>
        <v-card-actions>
          <v-btn
            color="blue"
            block 
            @click="dialog = false"
          >닫기</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
</template>
  
<style scoped>

</style>