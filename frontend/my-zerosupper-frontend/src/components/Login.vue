<template>
  <div class="login-container">
    <div class="login-box">
      <h2 class="login-title">登入會員</h2>
      <form v-if="!isLoggedIn" @submit.prevent="handleLogin" class="login-form">
        <div class="form-group">
          <label for="email">電子信箱：</label>
          <input type="email" id="email" v-model="email" required />
        </div>
        <div class="form-group">
          <label for="password">密碼：</label>
          <input type="password" id="password" v-model="password" required />
        </div>
        <div class="form-group">
          <label for="captcha">驗證碼：</label>
          <div class="captcha-container">
            <input type="text" id="captcha" v-model="captcha" required />
            <div class="captcha-display">{{ captchaCode }}</div>
          </div>
          <button type="button" @click="generateCaptcha" class="captcha-button">生成新驗證碼</button>
        </div>
        <button type="submit" class="login-button">登入</button>
      </form>

      <div v-else class="welcome-message">
        <h3>歡迎，{{ userEmail }}！</h3>
        <p>現在你可以到線上點餐來選擇你要的餐點。</p>
        <button @click="handleLogout" class="logout-button">登出</button>
      </div>
    </div>
  </div>
</template>

<script>
import './Login.css';
import axios from 'axios';

export default {
  name: 'LoginComponent',
  data() {
    return {
      email: '',
      password: '',
      captcha: '',
      captchaCode: '',
      isLoggedIn: false,
      userEmail: ''
    };
  },
  created() {
    this.generateCaptcha();
    this.isLoggedIn = !!localStorage.getItem('userToken');
    this.userEmail = localStorage.getItem('userEmail') || '';
  },
  methods: {
    generateCaptcha() {
      this.captchaCode = Math.random().toString(36).substring(2, 8).toUpperCase();
    },
    async handleLogin() {
      if (this.captcha !== this.captchaCode) {
        alert('驗證碼不正確！');
        return;
      }
      
      try {
        const response = await axios.post('http://localhost:9527/users/login', {
          email: this.email,
          password: this.password
        });
        
        if (response.status === 200) {
          alert('登入成功');
          console.log('登入成功，用戶信息：', response.data);
          
          localStorage.setItem('userToken', response.data.token);
          localStorage.setItem('userId', response.data.userId);
          localStorage.setItem('userEmail', this.email);
          this.isLoggedIn = true;
          this.userEmail = this.email;

          if (this.email === 'king@gmail.com') {
            localStorage.setItem('adminToken', 'true');
            this.$router.push('/admin');
          } else {
            this.$router.push('/menu');
          }

        } else {
          alert('登入失敗，請檢查您的電子郵件和密碼。');
        }
      } catch (error) {
        console.error('登入錯誤:', error);
        alert('登入時發生錯誤，請稍後再試。');
      }
    },
    handleLogout() {
      localStorage.removeItem('userToken');
      localStorage.removeItem('userId');
      localStorage.removeItem('userEmail');
      this.isLoggedIn = false;
      this.userEmail = '';
      alert('登出成功');
      this.$store.dispatch('clearCart');
      this.$router.push('/login');
    },
  },
}
</script>

<style scoped>
/* 這裡不需要額外的樣式，所有樣式都在 Login.css 中定義 */
</style>