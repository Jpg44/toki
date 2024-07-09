import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

const vuetify = createVuetify({
    components,
    directives,
})

const pinia = createPinia()
const app = createApp(App)

// const handleExternalUrl = () => {
//     const currentUrl = window.location.href
//     console.log(currentUrl)
//     if (currentUrl.includes('i10b205.p.ssafy.io/auth/login')) {
//         const urlParams = new URLSearchParams(window.location.search)
//         const resultCode = urlParams.get('resultCode')
//         const token = urlParams.get('token')

//         console.log('urlParams: ', urlParams)
//         console.log('token: ', token)

//         if (token) {
//             if(resultCode === '200') {
//                 const counterStore = useCounterStore()
//                 counterStore.setToken(token)
//                 router.push('/success')
//             }   else {
//                 router.push('/')
//             }
//         }
//     }
// }

pinia.use(piniaPluginPersistedstate)
app.use(router)
app.use(pinia)
app.use(vuetify)
app.mount('#app')
// app.mount('#app', () => {
//     // handleExternalUrl()
// })