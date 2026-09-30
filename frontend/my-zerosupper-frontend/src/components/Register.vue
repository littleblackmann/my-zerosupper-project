<template>
  <div class="auth-page auth-page-register">
    <section class="auth-visual">
      <div>
        <p>JOIN THE TABLE</p>
        <h1>好東西要留一份，也要留一個位置。</h1>
        <span>建立帳號後就能預約取餐、保留訂單紀錄。</span>
      </div>
    </section>

    <section class="auth-panel">
      <div class="auth-card">
        <p class="auth-kicker">CREATE ACCOUNT</p>
        <h2>註冊會員</h2>
        <p class="auth-subtitle">只需要電子信箱和一組至少 10 個字元的密碼。</p>
        <p v-if="errorMessage" class="auth-error" role="alert">{{ errorMessage }}</p>
        <form class="auth-form" @submit.prevent="handleRegister">
          <label for="register-email">電子信箱
            <input id="register-email" v-model.trim="email" type="email" autocomplete="email" placeholder="name@example.com" required />
          </label>
          <label for="register-password">密碼
            <input id="register-password" v-model="password" type="password" autocomplete="new-password" minlength="10" placeholder="至少 10 個字元" required />
          </label>
          <button type="submit" :disabled="isSubmitting">{{ isSubmitting ? '建立中…' : '建立會員帳號' }}</button>
        </form>
        <p class="auth-switch">已經是會員？<router-link to="/login">回到登入</router-link></p>
      </div>
    </section>
  </div>
</template>

<script>
import './Login.css';
import api, { apiErrorMessage } from '../services/api';

export default {
  name: 'RegisterComponent',
  data() {
    return { email: '', password: '', errorMessage: '', isSubmitting: false };
  },
  methods: {
    async handleRegister() {
      this.errorMessage = '';
      this.isSubmitting = true;
      try {
        const response = await api.post('/auth/register', { email: this.email, password: this.password });
        if (response.status === 201) this.$router.push({ path: '/login', query: { registered: '1' } });
      } catch (error) {
        this.errorMessage = apiErrorMessage(error, '註冊失敗，請稍後再試。');
      } finally {
        this.isSubmitting = false;
      }
    },
  },
};
</script>
