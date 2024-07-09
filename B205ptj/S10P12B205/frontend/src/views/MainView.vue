<script setup>
import UserDisplay from '@/components/web_rtc/UserDisplay.vue';
import Ladder from '@/components/game/Ladder.vue';
import ScreenRecorder from '@/components/screenRecord/ScreenRecord.vue'
import dice from '@/components/game/dice.vue';
</script>

<template>
    <div>
        main입니다.
        <div>
            <dice/>
        
    
    </div>
    <div id="app">
    <!-- <ScreenRecorder /> -->
  </div>
        <div>
        <UserDisplay/>
    </div>
    
    </div>
    
</template>

<style scoped>

</style>