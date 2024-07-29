<template>
    <div class="order">
      <h2>線上點餐</h2> <!-- 顯示線上點餐標題 -->
      <div v-if="!isLoggedIn"> <!-- 如果用戶未登入 -->
        <p>請先登入會員才能進行線上點餐。</p> <!-- 提示用戶登入 -->
        <router-link to="/login">前往登入頁面</router-link> <!-- 提供登入頁面的連結 -->
      </div>
      <div v-else> <!-- 如果用戶已登入 -->
        <p>這裡將顯示你的菜單和點餐功能。</p> <!-- 顯示菜單和點餐功能的提示 -->
        <!-- 在這裡添加你的點餐邏輯和介面 -->
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'OrderComponent', // 設定組件的名稱為 OrderComponent
    computed: {
      isLoggedIn() {
        // 檢查用戶是否已登入（根據實際情況調整）
        return !!localStorage.getItem('user'); // 假設使用 localStorage 存儲登入狀態
      },
    },
  }
  </script>
  
  <style>
  .order {
    padding: 20px; /* 增加內邊距 */
    color: white; /* 設定文字顏色為白色 */
    background-color: rgba(0, 0, 0, 0.5); /* 設定半透明黑色背景 */
  }
  </style>
  