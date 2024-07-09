<script setup>
import { ref, defineEmits } from 'vue'

// 부모 RoomVideoVirtualModal.vue 으로 emit 발신
const emit = defineEmits(['selectVirtual'])

const dialog = ref(false)

// 버츄얼 선택할 때, 함수
const selectVirtual = function(voice){
    emit('selectVirtual', voice)
    dialog.value = true
}

</script>
<template>
    <v-btn color="white" icon="" size="150px" @click="selectVirtual('virtual 1 ')">
      <v-icon class="mdi mdi-virtual-reality" size="100px"> </v-icon>
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
          <v-icon class="mdi mdi-virtual-reality" size="100px"> </v-icon>
          버츄얼 1 이 적용되었습니다.
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