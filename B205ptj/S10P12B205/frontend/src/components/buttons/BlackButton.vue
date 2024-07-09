<script setup>
import BaseButton from '../base/BaseButton.vue';
import { ref } from 'vue';
const props = defineProps({type:String,color:String
});
const name = ref();
name.value=props.type;
if(name.value==null){
  name.value="확인"
}
</script>

<template>
      <BaseButton v-bind:type=name v-bind:id="'black'"></BaseButton>  
      <video src=""></video>
</template>

<style scoped>
#black {
    color: aliceblue;
    background-color: rgb(18, 17, 17);

}
</style>