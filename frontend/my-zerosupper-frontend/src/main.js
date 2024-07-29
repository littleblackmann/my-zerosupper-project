import { createApp } from 'vue' // 從 Vue 中導入 createApp 函數
import App from './App.vue' // 導入根組件 App
import router from './router' // 導入路由設定
import '@fortawesome/fontawesome-free/css/all.css';

// 創建 Vue 應用實例，並使用 router 插件，然後掛載到 id 為 'app' 的 DOM 元素上
createApp(App).use(router).mount('#app')
