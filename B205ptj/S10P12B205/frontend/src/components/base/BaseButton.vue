<script setup>
import { onMounted, ref } from 'vue';

//부모에게 가져옴
const props = defineProps({
    type: String, color: String
});


</script>  

<template>
        <v-btn >{{ props.type }}</v-btn>
        
</template>

<style scoped>

</style>