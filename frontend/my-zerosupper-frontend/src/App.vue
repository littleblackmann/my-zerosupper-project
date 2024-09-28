<template>
  <div id="app">
    <header>
      <router-link to="/" style="text-decoration: none; color: white;">
        <h1>ZERO Supper</h1>
      </router-link>
      <nav>
        <router-link to="/About">關於我們</router-link>
        <router-link to="/menu">餐點介紹</router-link>
        <router-link to="/location">餐廳位置</router-link>
        <router-link to="/login">登入會員</router-link>
        <router-link to="/register">註冊會員</router-link>
        <router-link to="/order">線上點餐</router-link>
        <router-link to="/cart">購物車</router-link> 
      </nav>
  <div class="social-media-icons">
    <a href="https://www.facebook.com/profile.php?id=100063825376433" target="_blank" class="icon">
      <i class="fab fa-facebook-f"></i>
    </a>
    <a href="https://www.instagram.com/" target="_blank" class="icon">
      <i class="fab fa-instagram"></i>
    </a>
    <a href="https://lin.ee/tWnZO63" target="_blank" class="icon">
      <i class="fab fa-line"></i>
    </a>
  </div>
</header>
<main>
  <transition name="fade" mode="out-in">
    <router-view></router-view>
  </transition>
</main>
<footer>
  <div class="footer-text">
    隱私權聲明 政府食安規範標示
  </div>
  <div class="footer-text">
    Copyright © ZERO Supper (08579527), 2024
  </div>
</footer>
  </div>
</template>
<script>
import './App.css';

export default {
  name: 'App',
}
</script>