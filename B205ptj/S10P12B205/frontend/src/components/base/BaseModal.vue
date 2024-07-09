<script setup>
import LoginModal from '../modal/LoginModal.vue';
import GrayButton from '../buttons/GrayButton.vue';
import { click } from '@/js/Click';
import { onMounted, ref } from 'vue';
const props = defineProps({
    state:Boolean
});
const loginprop=ref();
loginprop.value=props.state
console.log("jbjkg",loginprop.value);
const emit =defineEmits(["clickchange"]);
function clickch(){
    console.log("눌렷냐");
emit("clickchange")
}

</script>

<template>
    <div id="modal">
        제목
        <LoginModal v-on:click-change="clickch"/>
        <GrayButton v-on:click="clickch()"/>
    </div>    
</template>

<style scoped>
#modal{
    position: absolute;
  top: 50%;
  left: 50%;
  /* transform: translate(-50%, -50%); */
  background-color: silver;
  border-radius: 10px;
  padding: 20px;
  box-sizing: border-box;
  z-index: 10;

  font-weight: bold;
  font-size: 15px;
  text-shadow: 2px 2px 6px gray;
  border-radius: 5px;
  font-style: initial;

  box-shadow: 4px 4px 6px gray;
}
</style>