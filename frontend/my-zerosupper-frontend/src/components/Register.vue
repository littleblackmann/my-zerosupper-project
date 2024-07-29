<template>
    <div class="register">
      <h2>註冊會員</h2> <!-- 顯示註冊會員的標題 -->
      <form @submit.prevent="handleRegister"> <!-- 當表單提交時，防止默認行為並調用 handleRegister 方法 -->
        <div class="form-group">
          <label for="name">姓名：</label> <!-- 姓名標籤 -->
          <input type="text" id="name" v-model="name" required /> <!-- 姓名輸入框，必填 -->
        </div>
        <div class="form-group">
          <label for="username">帳號：</label> <!-- 帳號標籤 -->
          <input type="text" id="username" v-model="username" required /> <!-- 帳號輸入框，必填 -->
        </div>
        <div class="form-group">
          <label for="password">密碼：</label> <!-- 密碼標籤 -->
          <input type="password" id="password" v-model="password" required /> <!-- 密碼輸入框，必填 -->
        </div>
        <div class="form-group">
          <label for="phone">手機號碼：</label> <!-- 手機號碼標籤 -->
          <input type="tel" id="phone" v-model="phone" required /> <!-- 手機號碼輸入框，必填 -->
        </div>
        <div class="form-group">
          <label for="email">電子郵件：</label> <!-- 電子郵件標籤 -->
          <input type="email" id="email" v-model="email" required /> <!-- 電子郵件輸入框，必填 -->
        </div>
        <button type="submit">註冊</button> <!-- 註冊按鈕 -->
      </form>
    </div>
  </template>
  
  <script>
  export default {
    name: 'RegisterComponent', // 設定組件的名稱為 RegisterComponent
    data() {
      return {
        name: '', // 姓名
        username: '', // 帳號
        password: '', // 密碼
        phone: '', // 手機號碼
        email: '', // 電子郵件
      };
    },
    methods: {
      handleRegister() {
        // 這裡可以顯示註冊成功的提示
        alert('註冊成功！'); // 提示註冊成功
        this.clearForm(); // 清空表單
      },
      clearForm() {
        // 清空表單欄位
        this.name = '';
        this.username = '';
        this.password = '';
        this.phone = '';
        this.email = '';
      },
    },
  }
  </script>
  
  <style>
  .register {
    padding: 80px; /* 增加內邊距 */
    color: white; /* 設定文字顏色為白色 */
    background-color: rgba(0, 0, 0, 0.5); /* 設定半透明黑色背景 */
  }
  .form-group {
    margin-bottom: 30px; /* 每個表單組之間的底部間距 */
  }
  input {
    padding: 10px; /* 設定內邊距 */
    width: 100%; /* 設定寬度為 100% */
    border: 1px solid #ccc; /* 設定邊框樣式 */
    border-radius: 5px; /* 設定圓角 */
  }
  button {
    padding: 10px 15px; /* 設定按鈕內邊距 */
    background-color: #28a745; /* 設定按鈕背景色 */
    color: white; /* 設定按鈕文字顏色為白色 */
    border: none; /* 去除按鈕邊框 */
    border-radius: 5px; /* 設定按鈕圓角 */
    cursor: pointer; /* 設定滑鼠指標為手形 */
  }
  </style>
  