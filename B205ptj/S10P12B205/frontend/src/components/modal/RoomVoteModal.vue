<script setup>
import { ref, defineProps, defineEmits } from 'vue'

const props = defineProps({
    roomVotegDialog: {
        type: Boolean,
        required: true,
    }
})

const emit = defineEmits(['update:roomVotegDialog'])

function closeModal() {
  emit('update:roomVotegDialog', false)
}

// 투표 내용 글자 수 예외 알림.
const alarm = ref()
// 투표 입력 내용
const content = ref('')
// 투표 글자 수 예외 처리
const contentRules = [
    value => {
        if (!value || value?.length < 200) return true;
        return '글자 수 초과';
    }
]

// 투표 생성
const sendMessage = function(leth){
  if (leth > 200) {
    alarm.value = "투표 내용 글자 수 초과 했습니다."
  } else if (leth === 0) {
    alarm.value = "투표 내용을 작성해 주세요."
  } else {
    closeModal()
  }
}
</script>

<template>
    <div>
        <v-dialog
            v-model="props.roomVotegDialog"
            @click:outside="closeModal"
            width="auto"
            height="1000px"                        
        >
            <v-sheet 
                width="1000px" 
                height="1000px" 
                color="indigo-lighten-5"                
                style="border-radius: 20px; border: 1px solid rgb(255, 255, 255);"
            >

                <!-- 투표 상태 표시줄 (맨 위) -->
                <v-toolbar
                    color="black"             
                >
                    <v-toolbar-title                        
                        style="margin-left: 50px;"
                    >
                        투표
                    </v-toolbar-title>
                </v-toolbar>
                
                <!-- 투표 창 -->
                <v-container fluid>
                  <v-row
                    class="d-flex align-center justify-center"
                    style="margin-left: 10%; margin-right: 10%; margin-top: 20%; text-align: center;"             
                  > 
                    <v-textarea
                      v-model="content"
                      bg-color="white"
                      color="purple" 
                      variant="outlined" 
                      label="투표할 내용 입력" 
                      rows="12" 
                      clearable auto-grow
                      :rules="contentRules"
                    >
                    </v-textarea>
                  </v-row>
                  <v-row 
                    class="d-flex align-center justify-end" 
                    style="margin-left: 10%; margin-right: 10%;"
                  >
                    <small style="color: red;">{{ alarm }}</small>
                    <v-btn
                      class="ma-2 pa-2 rounded-lg" 
                      min-width="100" 
                      color="black"
                      @click="sendMessage(content.length)"
                    >생성</v-btn>
                    <v-btn 
                      class="ma-2 pa-2 rounded-lg" 
                      min-width="100"
                      @click="closeModal()"
                    >취소</v-btn>
                  </v-row>
                </v-container>
            </v-sheet>
        </v-dialog>
    </div>
</template>

<style scoped>

</style>