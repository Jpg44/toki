<script setup>
import BaseButton from '../base/BaseButton.vue';
import { ref } from 'vue';
const props = defineProps({type:String,color:String
});
const name = ref();
name.value=props.type;
if(name.value==null){
  name.value="취소"
}
</script>

<template>
      <BaseButton v-bind:type=name v-bind:id="'gray'"></BaseButton>
    
</template>

<style scoped>
#gray {
    /* background-color: rgb(70, 70, 70); */
}
</style>