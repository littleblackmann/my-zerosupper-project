import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import store from './store'; // 新增：導入 Vuex store
import '@fortawesome/fontawesome-free/css/all.css';
import axios from 'axios'; 


// 創建 Vue 應用實例
const app = createApp(App);

app.config.globalProperties.$http = axios;

// 使用路由和 Vuex store
app.use(router);
app.use(store); // 新增：使用 Vuex store

// 僅在有設定 API key 時載入 Google Maps，避免送出無效請求。
const googleMapsApiKey = import.meta.env.VITE_GOOGLE_MAPS_API_KEY;

if (googleMapsApiKey) {
  const script = document.createElement('script');
  script.src = `https://maps.googleapis.com/maps/api/js?key=${encodeURIComponent(googleMapsApiKey)}`;
  script.async = true;
  script.defer = true;
  document.head.appendChild(script);
}

// 掛載應用
app.mount('#app');
