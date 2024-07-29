<template>
    <div class="login">
      <h2>登入會員</h2> <!-- 顯示登入標題 -->
      <form @submit.prevent="handleLogin"> <!-- 表單提交事件，防止默認行為並調用 handleLogin 方法 -->
        <div class="form-group">
          <label for="username">帳號：</label> <!-- 帳號標籤 -->
          <input type="text" id="username" v-model="username" required /> <!-- 帳號輸入框，使用 v-model 雙向綁定 -->
        </div>
        <div class="form-group">
          <label for="password">密碼：</label> <!-- 密碼標籤 -->
          <input type="password" id="password" v-model="password" required /> <!-- 密碼輸入框，使用 v-model 雙向綁定 -->
        </div>
        <div class="form-group">
          <label for="captcha">驗證碼：</label> <!-- 驗證碼標籤 -->
          <input type="text" id="captcha" v-model="captcha" required /> <!-- 驗證碼輸入框，使用 v-model 雙向綁定 -->
          <div class="captcha-display">{{ captchaCode }}</div> <!-- 顯示生成的驗證碼 -->
          <button type="button" @click="generateCaptcha">生成新驗證碼</button> <!-- 生成新驗證碼的按鈕 -->
        </div>
        <button type="submit">登入</button> <!-- 提交表單的登入按鈕 -->
      </form>
    </div>
  </template>
  
  <script>
  export default {
    name: 'LoginComponent', // 設定組件的名稱為 LoginComponent
    data() {
      return {
        username: '', // 用於綁定帳號輸入的變數
        password: '', // 用於綁定密碼輸入的變數
        captcha: '', // 用於綁定驗證碼輸入的變數
        captchaCode: '', // 生成的驗證碼
      };
    },
    created() {
      this.generateCaptcha(); // 在組件創建時生成初始驗證碼
    },
    methods: {
      generateCaptcha() {
        // 生成隨機驗證碼（例如，6位數字）
        this.captchaCode = Math.random().toString(36).substring(2, 8).toUpperCase(); // 隨機生成驗證碼
      },
      handleLogin() {
        // 處理登入邏輯
        if (this.captcha !== this.captchaCode) { // 驗證碼比對
          alert('驗證碼不正確！'); // 如果驗證碼不正確，顯示提示
          return; // 結束函數
        }
        // 在這裡添加其他登入邏輯，例如 API 請求
        alert(`登入成功：${this.username}`); // 登入成功提示
      },
    },
  }
  </script>
  
  <style>
  .login {
    padding: 80px; /* 增加內邊距 */
    color: white; /* 設定文字顏色為白色 */
    background-color: rgba(0, 0, 0, 0.5); /* 設定半透明黑色背景 */
  }
  
  .form-group {
    margin-bottom: 30px; /* 增加每組表單元素的下邊距 */
  }
  
  input {
    padding: 10px; /* 增加輸入框的內邊距 */
    width: 100%; /* 設定輸入框寬度為 100% */
    border: 1px solid #ccc; /* 設定邊框 */
    border-radius: 5px; /* 設定圓角邊框 */
  }
  
  .captcha-display {
    margin: 10px 0; /* 設定上下邊距 */
    font-size: 18px; /* 設定字體大小 */
    font-weight: bold; /* 設定字體為粗體 */
    color: yellow; /* 設定顏色，根據需要可以調整 */
  }
  
  button {
    padding: 10px 15px; /* 設定按鈕的內邊距 */
    background-color: #28a745; /* 設定按鈕背景顏色 */
    color: white; /* 設定按鈕文字顏色為白色 */
    border: none; /* 移除按鈕邊框 */
    border-radius: 5px; /* 設定圓角邊框 */
    cursor: pointer; /* 設定鼠標懸停時的樣式 */
  }
  
  button[type="button"] {
    margin-top: 10px; /* 添加生成新驗證碼按鈕的上邊距 */
  }
  </style>
  