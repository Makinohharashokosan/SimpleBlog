import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'

// 引入全局样式
import './assets/styles/main.css'

const app = createApp(App)

import i18n from './i18n'

// 注册 Pinia
app.use(createPinia())
app.use(router)
app.use(i18n)

app.mount('#app')