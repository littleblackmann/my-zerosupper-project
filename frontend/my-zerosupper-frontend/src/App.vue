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
export default {
  name: 'App', // 組件名稱
}
</script>

<style>
.social-media-icons {
  margin-top: 15px; /* 在社交媒體圖標上方添加 15 像素的外邊距 */
  display: flex; /* 使用彈性佈局，使圖標可以靈活排列 */
  justify-content: center; /* 將圖標在容器中水平居中對齊 */
  gap: 30px; /* 在圖標之間添加 30 像素的間隔 */
}

.icon {
  color: white; /* 設置圖標顏色為白色 */
  font-size: 35px; /* 設置圖標的字體大小為 35 像素 */
}


/* 重置瀏覽器默認樣式，確保一致的起點 */
html, body {
  margin: 0; /* 移除默認邊距 */
  padding: 0; /* 移除默認內邊距 */
  width: 100%; /* 設定寬度為視窗寬度的 100% */
  height: 100%; /* 設定高度為視窗高度的 100% */
}

/* 設定應用程式主容器的樣式 */
#app {
  width: 100%; /* 設定寬度為視窗寬度的 100% */
  min-height: 100vh; /* 最小高度為視口高度的 100%，確保在內容少時也能填滿屏幕 */
  display: flex; /* 使用 flexbox 佈局 */
  flex-direction: column; /* 子元素垂直排列 */
  background-image: url('@/assets/background.jpg'); /* 設置背景圖片 */
  background-size: cover; /* 背景圖片覆蓋整個容器 */
  background-position: center; /* 背景圖片居中 */
  background-repeat: no-repeat; /* 背景圖片不重複 */
  background-attachment: fixed; /* 背景固定，不隨頁面滾動 */
  position: relative; /* 為偽元素定位提供參考 */
  z-index: 1; /* 確保內容在半透明遮罩之上 */
}

/* 創建半透明黑色遮罩 */
#app::before {
  content: ""; /* 創建偽元素 */
  position: fixed; /* 固定定位，覆蓋整個視口 */
  top: 0; /* 從頂部開始 */
  left: 0; /* 從左側開始 */
  right: 0; /* 從右側開始 */
  bottom: 0; /* 從底部開始 */
  background: rgba(0, 0, 0, 0.5); /* 半透明黑色背景 */
  z-index: -1; /* 置於主容器背景之上，內容之下 */
}

/* 設定頁面頭部樣式 */
header {
  width: 100%; /* 佔滿容器寬度 */
  text-align: center; /* 文字居中 */
  background-color: rgba(0, 0, 0, 0.5); /* 半透明黑色背景 */
  padding: 20px; /* 內邊距 */
  color: white; /* 文字顏色 */
}

/* 設定導航區域樣式 */
nav {
  margin-top: 10px; /* 與上方元素保持間距 */
}

/* 設定導航鏈接樣式 */
nav a {
  color: white; /* 鏈接文字顏色 */
  margin: 0 10px; /* 左右間距 */
  text-decoration: none; /* 移除下劃線 */
}

/* 設定主要內容區域樣式 */
main {
  flex: 1; /* 佔用剩餘空間 */
  display: flex; /* 使用 flexbox 佈局 */
  flex-direction: column; /* 內容垂直排列 */
  justify-content: flex-start; /* 內容從頂部開始排列 */
  align-items: center; /* 水平居中 */
  color: white; /* 文字顏色 */
  text-align: center; /* 文字居中 */
  padding: 20px; /* 內邊距 */
  overflow-y: auto; /* 允許垂直滾動 */
}

/* 設定頁面切換的淡入淡出效果 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 1.3s ease; /* 透明度變化動畫，持續 1.3 秒 */
}

/* 定義淡入淡出的起始和結束狀態 */
.fade-enter-from,
.fade-leave-to {
  opacity: 0; /* 完全透明 */
}


</style>
