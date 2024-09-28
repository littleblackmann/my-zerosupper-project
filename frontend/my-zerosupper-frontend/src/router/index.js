import { createRouter, createWebHistory } from 'vue-router';
import Menu from '../components/Menu.vue';
import Location from '../components/Location.vue';
import Login from '../components/Login.vue';
import Register from '../components/Register.vue';
import Order from '../components/Order.vue';
import About from '../components/About.vue';
import Cart from '../components/Cart.vue';
import AdminUser from '../components/AdminUser.vue';  

function isLoggedIn() {
  return localStorage.getItem('userToken') !== null;
}

function isAdminLoggedIn() {
  return localStorage.getItem('adminToken') !== null; 
}

const routes = [
  { path: '/about', component: About },
  { path: '/menu', component: Menu },
  { path: '/location', component: Location },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  {
    path: '/order',
    component: Order,
    meta: { requiresAuth: true }
  },
  { path: '/cart', component: Cart },
  {
    path: '/admin',
    component: AdminUser,
    meta: { requiresAdminAuth: true }
  }
];

const router = createRouter({
  history: createWebHistory(),
  routes
});

router.beforeEach((to, from, next) => {
  // 需要一般用戶權限的檢查
  if (to.meta.requiresAuth && !isLoggedIn()) {
    next('/login');
  } 
  // 需要管理員權限的檢查
  else if (to.meta.requiresAdminAuth && !isAdminLoggedIn()) {
    next('/login');  
  } 
  else {
    next();  // 否則繼續導航
  }
});


export default router;
