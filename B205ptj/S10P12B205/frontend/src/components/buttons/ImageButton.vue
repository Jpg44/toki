<script setup>
import BaseButton from '../base/BaseButton.vue';
import { ref } from 'vue';
const props = defineProps({type:String,color:String
});
const name = ref();
name.value=null;
</script>

<template>
      <img src="@/assets/logo.svg" width="25" height="25" />
      <BaseButton v-bind:type="name" ></BaseButton>
   
</template>

<style scoped>

</style>