<template>
    <div>
        <button 
            class="btn-hover color-9"
            @click="openDialog"
        >
        LOGIN
        </button>
        <!-- <v-btn
            style="width: 90px"
            color="primary"
            @click="openDialog"
        >
            login
        </v-btn> -->
        <LoginModal/>

    </div>

  </template>
<script setup>

import { ref, provide } from 'vue';
import LoginModal from '@/components/modal/LoginModal.vue'

const dialog = ref(false)

const openDialog = () => {
    dialog.value = true
    
}
provide('dialogState', dialog)


</script>

<style>
.btn-hover {
    width: 90px;
    font-size: 15px;
    font-weight: 300;
    color: #fff;
    cursor: pointer;
    
    height: 36px;
    text-align:center;
    border: none;
    background-size: 300% 100%;
    letter-spacing: 1px;

    border-radius: 5px;
    moz-transition: all .4s ease-in-out;
    -o-transition: all .4s ease-in-out;
    -webkit-transition: all .4s ease-in-out;
    transition: all .4s ease-in-out;
}


.btn-hover:hover {
    background-position: 100% 0;
    moz-transition: all .4s ease-in-out;
    -o-transition: all .4s ease-in-out;
    -webkit-transition: all .4s ease-in-out;
    transition: all .4s ease-in-out;
}

.btn-hover.color-9 {
    background-image: linear-gradient(to right, #39b2e6, #6193eb, #21c8ff, #6198eb);
    box-shadow: 0 4px 15px 0 rgba(65, 132, 234, 0.75);
}
</style>