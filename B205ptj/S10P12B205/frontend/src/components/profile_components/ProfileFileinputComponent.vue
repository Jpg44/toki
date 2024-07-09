  
<script setup>
import { ref } from 'vue'

const files = ref([])

// 파일 크기 2MB 보다 작을 때, 업로드
const rules = ref([
    value => !value || !value.length || value[0].size < 2000000 || 'Avatar size should be less than 2 MB!',
])

const fileSubmit = () => {
  console.log('file upload')
}
</script>

<template>
  <form @submit.prevent class="mt-4">
  <v-file-input
    v-model="files"
    :rules="rules"
    accept="image/png, image/jpeg, image/bmp"
    color="blue-lighten-1"
    counter
    label="File Image input"
    prepend-icon="mdi-camera"
    variant="outlined"
    :show-size="1000"
    clear-icon="mdi-upload"
    :on-click:clear="fileSubmit"
  >
    <template v-slot:selection="{ fileNames }">
      <template v-for="(fileName) in fileNames" :key="fileName">
        <v-chip color="blue-darken-4" label size="small" class="me-2">
          {{ fileName }}
        </v-chip>
      </template>
    </template>
  </v-file-input>
  <p class="mt-3">2mb 이내의 JPEG, PNG, BMP 형식이어야 합니다.</p>
  </form>  
  
  <link href="https://cdn.jsdelivr.net/npm/@mdi/font@5.x/css/materialdesignicons.min.css" rel="stylesheet">
</template>
  

<style scoped>

</style>