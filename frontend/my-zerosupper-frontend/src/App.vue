<template>
  <div id="app">
    <header>
      <router-link to="/" style="text-decoration: none; color: white;">
        <h1>ZERO Supper</h1> <!-- 頁面標題 -->
      </router-link>
      <nav>
        <router-link to="/About">關於我們</router-link> <!-- 線上點餐的導航鏈接 -->
        <router-link to="/menu">餐點介紹</router-link> <!-- 餐點介紹的導航鏈接 -->
        <router-link to="/location">餐廳位置</router-link> <!-- 餐廳位置的導航鏈接 -->
        <router-link to="/login">登入會員</router-link> <!-- 登入會員的導航鏈接 -->
        <router-link to="/register">註冊會員</router-link> <!-- 註冊會員的導航鏈接 -->
        <router-link to="/order">線上點餐</router-link> <!-- 線上點餐的導航鏈接 -->
      </nav>

      <div class="social-media-icons">
        <a href="https://www.facebook.com/profile.php?id=100063825376433/你的Facebook頁面" target="_blank" class="icon">
          <i class="fab fa-facebook-f"></i>
        </a>
        <a href="https://www.instagram.com/你的Instagram頁面" target="_blank" class="icon">
          <i class="fab fa-instagram"></i>
        </a>
        <a href="https://lin.ee/tWnZO63/你的Line頁面" target="_blank" class="icon">
          <i class="fab fa-line"></i>
        </a>
      </div>

    </header>
    <main>
      <transition name="fade" mode="out-in"> <!-- 定義頁面切換的過渡效果 -->
        <router-view></router-view> <!-- 顯示當前路由對應的組件 -->
      </transition>
    </main>
  </div>
</template>

<script>
  import './App.css'; //引入css

  export default {
    name: 'App', // 組件名稱
  }
</script>