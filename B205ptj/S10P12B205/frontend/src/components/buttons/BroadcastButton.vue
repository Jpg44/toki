<template>
  <div>
    <v-btn
        style="width: 50px; height: 50px;"
        variant="plain"
        icon="mdi-video"
        color="blue-lighten-2"
        @click="openBroadcast"
    >

        <v-icon
          size="35"
          icon="mdi-video"
          color="light-blue-lighten-2"        
        >

        </v-icon>
    </v-btn>

    <BroadcastModal 
      :broadcastDialog="broadcastDialog"
      @update:broadcastDialog="broadcastDialog = $event"
    />
    <!-- <div id="b3" class="box">
      <svg id="i3" class="icon" viewBox="0 0 100 100">
        <path id="top-line-3"     d="M30,37 L70,37 Z"></path>
        <path id="middle-line-3" 	d="M30,50 L70,50 Z"></path>
        <path id="bottom-line-3" 	d="M30,63 L70,63 Z"></path>
      </svg>  
    </div> -->
  </div>
</template>
  

<script setup>
import  { ref, provide } from 'vue'
import BroadcastModal from '@/components/modal/BroadcastModal.vue';

const broadcastDialog = ref(false)

const openBroadcast = () => {
    broadcastDialog.value = !broadcastDialog.value
}
// provide('broadcastState', broadcastDialog)


</script>

<style scoped>

</style>