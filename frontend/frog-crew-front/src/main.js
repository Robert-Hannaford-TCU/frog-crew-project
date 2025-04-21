import { createApp } from 'vue'
import App from './App.vue'
import router from './router.js' // Import your router config

const app = createApp(App)
app.use(router) // Enable Vue Router
app.mount('#app')
