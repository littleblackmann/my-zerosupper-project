import { createRouter, createWebHistory } from 'vue-router'; // 從 vue-router 導入創建路由器和歷史記錄的函數
import Menu from '../components/Menu.vue'; // 引入菜單組件
import Location from '../components/Location.vue'; // 引入位置組件
import Login from '../components/Login.vue'; // 引入登入組件
import Register from '../components/Register.vue'; // 引入註冊組件
import Order from '../components/Order.vue'; // 引入點餐組件
//import HomePage from '../components/HomePage.vue'; // 引入首頁組件
import About from '../components/About.vue'; // 引入首頁組件

// 定義路由的數組
const routes = [
  //{ path: '/', component: HomePage }, // 根路徑對應首頁組件
  { path: '/about', component: About }, // '/about' 路徑對應關於我們組件
  { path: '/menu', component: Menu }, // '/menu' 路徑對應菜單組件
  { path: '/location', component: Location }, // '/location' 路徑對應位置組件
  { path: '/login', component: Login }, // '/login' 路徑對應登入組件
  { path: '/register', component: Register }, // '/register' 路徑對應註冊組件
  { path: '/order', component: Order }, // '/order' 路徑對應點餐組件
  
];

// 創建路由器實例
const router = createRouter({
  history: createWebHistory(), // 使用 HTML5 的歷史模式
  routes, // 將定義的路由數組傳入路由器
});

export default router; // 將路由器實例導出
