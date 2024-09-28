<template>
  <div class="login-container">
    <div class="login-box">
      <h2 class="login-title">註冊會員</h2>
      <form class="login-form" @submit.prevent="handleRegister">
        <div class="form-group">
          <label for="email">電子郵件：</label>
          <input type="email" id="email" v-model="email" required />
        </div>
        <div class="form-group">
          <label for="password">密碼：</label>
          <input type="password" id="password" v-model="password" required />
        </div>
        <button class="login-button" type="submit">註冊</button>
      </form>
    </div>
  </div>
</template>

<script>
import './Register.css';
import axios from 'axios';

export default {
  name: 'RegisterComponent',
  data() {
    return {
      email: '',
      password: '',
    };
  },
  methods: {
    async handleRegister() {
      try {
        const response = await axios.post('http://localhost:9527/users/register', {
          email: this.email,
          password: this.password
        });

        if (response.status === 201) {
          alert('註冊成功！');
          console.log('註冊的用戶信息:', response.data);
          this.clearForm();
          this.$router.push('/login');
        } else {
          alert('註冊失敗，請稍後再試。');
        }
      } catch (error) {
        console.error('註冊錯誤:', error);
        alert('註冊時發生錯誤，請稍後再試。');
      }
    },
    clearForm() {
      this.email = '';
      this.password = '';
    },
  },
}
</script>

<style scoped>
/* 這裡不需要額外的樣式，所有樣式都在 Register.css 中定義 */
</style>