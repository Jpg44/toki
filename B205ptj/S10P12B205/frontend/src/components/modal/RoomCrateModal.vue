<script setup>
import {roomCreate,roomEnter } from "@/js/Room.js"
import { ref } from "vue";
const props = defineProps({user:String,category:Array});
const roomCreateObject = ref({
    user:String,
    title:String,
    category:String,
    tag:Array,
    badge:Boolean,
    baduser:Boolean,
    videoOn:Boolean,
    password:String,
    userLimit:String,
    userGender:String,
});
const categoryList = ref({});
roomCreateObject.user.value=props.user;
categoryList.value=props.category.List;
</script>

<template>
    <div>
        <input v-bind="roomCreateObject.title" value="제목" type="text"/>
        <v-combobox
  label="카테고리"
  :items="categoryList"
  v-bind="roomCreateObject.category"
></v-combobox>



        제목:<input v-bind="roomCreateObject.title" />
        제목:<input v-bind="roomCreateObject.title" />



    </div>
</template>

<style scoped></style>