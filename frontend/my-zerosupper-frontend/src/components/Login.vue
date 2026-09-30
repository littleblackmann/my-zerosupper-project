<template>
  <div class="auth-page">
    <section class="auth-visual">
      <div>
        <p>MEMBERS OF ZERO</p>
        <h1>回來了，就坐下來吃點東西吧。</h1>
        <span>登入後可以送出訂單，也能查看自己的取餐紀錄。</span>
      </div>
    </section>

    <section class="auth-panel">
      <div class="auth-card">
        <template v-if="!isLoggedIn">
          <p class="auth-kicker">WELCOME BACK</p>
          <h2>登入會員</h2>
          <p class="auth-subtitle">使用你的電子信箱與密碼繼續。</p>
          <p v-if="registered" class="auth-success" role="status">註冊完成！現在可以登入了。</p>
          <p v-if="errorMessage" class="auth-error" role="alert">{{ errorMessage }}</p>

          <form class="auth-form" @submit.prevent="handleLogin">
            <label for="login-email">電子信箱
              <input id="login-email" v-model.trim="email" type="email" autocomplete="email" placeholder="name@example.com" required />
            </label>
            <label for="login-password">密碼
              <input id="login-password" v-model="password" type="password" autocomplete="current-password" placeholder="輸入你的密碼" required />
            </label>
            <button type="submit" :disabled="isSubmitting">{{ isSubmitting ? '登入中…' : '登入' }}</button>
          </form>
          <p class="auth-switch">還沒有帳號？<router-link to="/register">建立會員帳號</router-link></p>
        </template>

        <template v-else>
          <div class="auth-welcome-mark" aria-hidden="true">ZS</div>
          <p class="auth-kicker">SIGNED IN</p>
          <h2>歡迎回來</h2>
          <p class="auth-subtitle">{{ userEmail }}</p>
          <div class="auth-member-actions">
            <router-link to="/order">開始點餐</router-link>
            <router-link to="/orders">查看我的訂單</router-link>
            <button type="button" @click="handleLogout">登出</button>
          </div>
        </template>
      </div>
    </section>
  </div>
</template>

<script>
import './Login.css';
import api, { apiErrorMessage, clearSession, saveSession } from '../services/api';

export default {
  name: 'LoginComponent',
  data() {
    return {
      email: '',
      password: '',
      isLoggedIn: false,
      userEmail: '',
      errorMessage: '',
      isSubmitting: false,
      registered: this.$route.query.registered === '1',
    };
  },
  created() {
    this.isLoggedIn = !!localStorage.getItem('userToken');
    this.userEmail = localStorage.getItem('userEmail') || '';
  },
  methods: {
    async handleLogin() {
      this.errorMessage = '';
      this.isSubmitting = true;
      try {
        const response = await api.post('/auth/login', { email: this.email, password: this.password });
        saveSession(response.data);
        this.isLoggedIn = true;
        this.userEmail = response.data.email;
        const redirect = this.$route.query.redirect;
        if (response.data.role === 'ADMIN') this.$router.push('/admin');
        else if (typeof redirect === 'string' && redirect.startsWith('/')) this.$router.push(redirect);
        else this.$router.push('/order');
      } catch (error) {
        this.errorMessage = apiErrorMessage(error, '登入失敗，請檢查電子信箱與密碼。');
      } finally {
        this.isSubmitting = false;
      }
    },
    async handleLogout() {
      try { await api.post('/auth/logout'); }
      catch (error) { console.warn('後端登出失敗，仍會清除本機登入狀態。', error); }
      clearSession();
      this.isLoggedIn = false;
      this.userEmail = '';
      this.$store.dispatch('clearCart');
    },
  },
};
</script>
