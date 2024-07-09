<script setup>
import { useRoute, useRouter } from 'vue-router';
import { onMounted } from 'vue'
import { useCounterStore } from '@/stores/counter';
import { jwtDecode } from 'jwt-decode'

const route = useRoute();
const router = useRouter()
const store = useCounterStore()

onMounted(() => {
    
    // const resultCode = route.query.resultCode
    // const token = route.query.token

    // // const accessToken = route.params.atk;
    // // const refreshToken = route.params.rtk;
    // console.log('resultCode: ', resultCode);
    // console.log('token: ', token);
    // if (resultCode === '200' && token) {
    //     store.setToken(token)
    //     router.push('/'); // 홈으로 이동
    // } else {
    //     // 그렇지 않으면
    //     store.setToken(token)
    //     console.log('elseToken: ', token)
    //     // router.push('/'); // 홈으로 이동
    // }
    const accessToken = localStorage.getItem('accessToken')
    console.log(accessToken)
    if (accessToken) {
        setTimeout(function(){ window.location.replace('/')}, 1)
    }
});
</script>


<template>
    <div>
        <!-- <h1>{{ accessToken }}</h1> -->
        
        <h2>로그인 Success</h2>
    </div>
</template>

<style scoped>

</style>

<!-- <template>
    <div>

    </div>
</template>

<script setup>

</script>

<style lang="scss" scoped>

</style> -->