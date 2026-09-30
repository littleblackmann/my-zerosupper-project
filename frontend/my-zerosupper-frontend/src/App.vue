<template>
  <div id="app">
    <a class="skip-link" href="#main-content">跳至主要內容</a>

    <header class="site-header">
      <div class="header-inner">
        <router-link class="brand" to="/" aria-label="ZERO Supper 首頁" @click="closeNavigation">
          <span class="brand-mark" aria-hidden="true">ZS</span>
          <span class="brand-copy">
            <strong>ZERO Supper</strong>
            <small>BURGER &amp; MEMORIES</small>
          </span>
        </router-link>

        <button
          class="nav-toggle"
          type="button"
          :aria-expanded="navigationOpen"
          aria-controls="primary-navigation"
          aria-label="開啟或關閉導覽選單"
          @click="navigationOpen = !navigationOpen"
        >
          <span></span>
          <span></span>
          <span></span>
        </button>

        <nav id="primary-navigation" class="primary-navigation" :class="{ 'is-open': navigationOpen }">
          <div class="nav-links">
            <router-link to="/about" @click="closeNavigation">我們的故事</router-link>
            <router-link to="/menu" @click="closeNavigation">紀念菜單</router-link>
            <router-link to="/location" @click="closeNavigation">店的位置</router-link>
          </div>

          <div class="nav-actions">
            <router-link v-if="!isLoggedIn" class="nav-member-link" to="/login" @click="closeNavigation">登入</router-link>
            <router-link v-if="!isLoggedIn" class="nav-member-link" to="/register" @click="closeNavigation">註冊</router-link>
            <router-link v-if="isLoggedIn" class="nav-member-link" to="/orders" @click="closeNavigation">我的訂單</router-link>
            <router-link v-if="isAdmin" class="nav-member-link" to="/admin" @click="closeNavigation">後台管理</router-link>
            <router-link class="cart-link" to="/cart" @click="closeNavigation">
              購物車
              <span v-if="cartItemCount" class="cart-count">{{ cartItemCount }}</span>
            </router-link>
            <router-link class="order-link" to="/order" @click="closeNavigation">線上點餐</router-link>
          </div>
        </nav>
      </div>
    </header>

    <main id="main-content" class="site-main">
      <router-view v-slot="{ Component }">
        <transition name="fade" mode="out-in">
          <component :is="Component" />
        </transition>
      </router-view>
    </main>

    <footer class="site-footer">
      <div class="footer-inner">
        <div class="footer-brand">
          <span class="brand-mark brand-mark-small" aria-hidden="true">ZS</span>
          <div>
            <strong>ZERO Supper</strong>
            <p>一間漢堡店，和我們捨不得忘記的那段日子。</p>
          </div>
        </div>

        <div class="footer-links" aria-label="頁尾連結">
          <router-link to="/about">品牌故事</router-link>
          <router-link to="/menu">紀念菜單</router-link>
          <router-link to="/location">店的位置</router-link>
        </div>

        <div class="social-links" aria-label="社群連結">
          <a href="https://www.facebook.com/profile.php?id=100063825376433" target="_blank" rel="noreferrer" aria-label="Facebook"><i class="fab fa-facebook-f"></i></a>
          <a href="https://www.instagram.com/" target="_blank" rel="noreferrer" aria-label="Instagram"><i class="fab fa-instagram"></i></a>
          <a href="https://lin.ee/tWnZO63" target="_blank" rel="noreferrer" aria-label="LINE"><i class="fab fa-line"></i></a>
        </div>
      </div>

      <div class="footer-legal">
        <span>統一編號 08579527</span>
        <span>Copyright © ZERO Supper {{ currentYear }}</span>
      </div>
    </footer>
  </div>
</template>

<script>
import './App.css';

export default {
  name: 'App',
  data() {
    return {
      navigationOpen: false,
      isLoggedIn: localStorage.getItem('userToken') !== null,
      isAdmin: localStorage.getItem('userRole') === 'ADMIN',
      currentYear: new Date().getFullYear(),
    };
  },
  computed: {
    cartItemCount() {
      return this.$store.getters.cartItemCount;
    },
  },
  watch: {
    $route() {
      this.closeNavigation();
    },
  },
  mounted() {
    window.addEventListener('zerosupper-auth-changed', this.refreshAuthState);
  },
  beforeUnmount() {
    window.removeEventListener('zerosupper-auth-changed', this.refreshAuthState);
  },
  methods: {
    refreshAuthState() {
      this.isLoggedIn = localStorage.getItem('userToken') !== null;
      this.isAdmin = localStorage.getItem('userRole') === 'ADMIN';
    },
    closeNavigation() {
      this.navigationOpen = false;
    },
  },
};
</script>
