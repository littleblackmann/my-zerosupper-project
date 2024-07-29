import { createApp } from 'vue'; // 從 Vue 中導入 createApp 函數
import App from './App.vue'; // 導入根組件 App
import router from './router'; // 導入路由設定
import '@fortawesome/fontawesome-free/css/all.css';

// 創建 Vue 應用實例
const app = createApp(App).use(router);

// 動態引入 Google Maps API
const script = document.createElement('script');
script.src = `https://maps.googleapis.com/maps/api/js?key=${process.env.VUE_APP_GOOGLE_MAPS_API_KEY}`;
script.async = true;
script.defer = true;
document.head.appendChild(script);

// 掛載應用
app.mount('#app');
