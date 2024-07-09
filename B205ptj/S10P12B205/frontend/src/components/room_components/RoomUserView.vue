<script setup>
import { ref, onMounted } from 'vue';
const props = defineProps({
  userInfo: Object,
  
})
const userInfo = ref(props.userInfo)
console.log(userInfo.value)

</script>

<template>
  <div class="paripant-container">
    <slot name="video"></slot>
    <span class="toki-name-span" >{{ userInfo.name }}</span>
  </div>
</template>

<style scoped>
.paripant-container{
  width: 100%;
  height: auto;
  max-width: 300px;
  display: block;
  position:relative;
}
.toki-name-span{
  position: absolute;
  display: inline-block;
  right:1rem;
  bottom: 1rem;
  background-color: white;
  border-radius: 10%;
  padding: 0.2rem;
  font-size: 1rem;
  opacity: 50%;
}

</style>