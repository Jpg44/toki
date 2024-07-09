<template>
    <v-btn
        style="margin-left: 20px;"
        color="primary"
        variant="flat"
        @click.stop="oepnSideVar"
    >
    Toggle
    </v-btn>

</template>
<script setup>

import { defineEmits } from 'vue';

const emit = defineEmits(['drawer'])

const oepnSideVar = () => {
    emit('drawer')
}

</script>

<style>

</style>