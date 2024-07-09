<script setup>
import { ref } from 'vue'

// 부모 ProfileMessageCreateModal.vue 에서 주는 props
const dialogDataProp = defineProps({
  dialogData : Object
})

// 모달창 on / off
const dialog = ref(false)

// 쪽지 글자 수 예외 알림.
const alarm = ref()
// 쪽지 입력 내용
const content = ref('')
// 쪽지 글자 수 예외 처리
const contentRules = [
  value => {
    if (!value || value?.length < 100) return true;
    return '글자수 초과';
  } 
]

// 쪽지 발송
const sendMessage = function(leth){
  if (leth < 200) {
    alarm.value = "쪽지 글자 수 초과 했습니다."
  } else if (leth === 0) {
    alarm.value = "쪽지를 작성해 주세요."
  } else {
    dialog.value = false
  }
}
</script>

<template>
  <v-dialog v-model="dialog" persistent width="700">
    <template v-slot:activator="{ props }">
      <v-btn color="purple" v-bind="props"> 쪽지 보내기 </v-btn>
    </template>
    <v-card>
      <v-card-text>
        <v-container>
          <v-row>
            <v-col cols="12">
              <v-text-field
              :label= dialogDataProp.dialogData
              placeholder="쪽지 받는 사람"
              readonly
              prepend-icon="mdi-account"
            >
            </v-text-field>
            </v-col>
            <v-col cols="12">
              <v-textarea
                v-model="content"
                label="쪽지 내용"
                :rules="contentRules"
                clearable
                clear-icon="mdi-close-circle"
                prepend-icon="mdi-comment"
              ></v-textarea>
            </v-col>
          </v-row>
        </v-container>
        <small style="color: red;">{{ alarm }}</small>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="blue-darken-1" variant="text" @click="dialog = false">
          Close
        </v-btn>
        <v-btn color="blue-darken-1" variant="text" @click="sendMessage(content.length)">
          Save
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<style scoped>

</style>