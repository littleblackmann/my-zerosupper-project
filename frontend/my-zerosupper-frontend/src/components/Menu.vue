<template>
    <div class="menu">
      <!-- 菜單標題 -->
      <h1>菜單</h1>
      
      <!-- 菜單圖片區域 -->
      <div class="menu-images">
        <img src="@/assets/menu1.png" alt="菜單1" />
        <img src="@/assets/menu2.png" alt="菜單2" />
        <img src="@/assets/menu3.png" alt="菜單3" />
      </div>
  
      <!-- 漢堡標題 -->
      <h1>漢堡</h1>
      
      <!-- 漢堡項目網格 -->
      <div class="food-grid">
        <!-- 經典漢堡 -->
        <div class="food-item">
          <img src="@/assets/classicburger.jpg" alt="Classic Burger" />
          <div class="food-info">
            <h3>嫩內容物:</h3>
            <p>美生菜<br>牛番茄<br>紫洋蔥<br>手打牛肉<br>起司醬</p>
          </div>
        </div>
  
        <!-- 酪梨漢堡 -->
        <div class="food-item">
          <img src="@/assets/avocadoburger.jpg" alt="Avocado Burger" />
          <div class="food-info">
            <h3>弱內容物:</h3>
            <p>美生菜<br>牛番茄<br>紫洋蔥<br>手打牛肉<br>起司醬</p>
          </div>
        </div>
  
        <!-- 太陽漢堡 -->
        <div class="food-item">
          <img src="@/assets/sunburger.jpg" alt="Sun Burger" />
          <div class="food-info">
            <h3>太培牛內容物:</h3>
            <p>美生菜<br>牛番茄<br>紫洋蔥<br>手打牛肉<br>起司醬</p>
          </div>
        </div>
  
        <!-- 蘑菇漢堡 -->
        <div class="food-item">
          <img src="@/assets/mushroomburger.jpg" alt="Mushroom Burger" />
          <div class="food-info">
            <h3>住三重的姑姑內容物:</h3>
            <p>美生菜<br>牛番茄<br>紫洋蔥<br>手打牛肉<br>起司醬</p>
          </div>
        </div>
  
        <!-- 豬肉漢堡 -->
        <div class="food-item">
          <img src="@/assets/porkburger.jpg" alt="Pork Burger" />
          <div class="food-info">
            <h3>ㄐㄧㄤㄐㄧㄤㄐㄧㄤㄐㄧㄤ內容物:</h3>
            <p>美生菜<br>牛番茄<br>紫洋蔥<br>手打牛肉<br>起司醬</p>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'MenuComponent', // 組件名稱
  }
  </script>
  
  <style scoped>
  /* 整體菜單容器樣式 */
  .menu {
    padding: 20px; /* 內邊距 */
    background-color: rgba(0, 0, 0, 0.5); /* 半透明黑色背景 */
    color: white; /* 文字顏色 */
    border-radius: 20px; /* 圓角 */
  }
  
  /* 標題樣式 */
  h1 {
    margin-bottom: 20px; /* 下方間距 */
  }
  
  /* 菜單圖片容器樣式 */
  .menu-images {
    display: flex; /* 彈性布局 */
    justify-content: space-between; /* 均勻分布 */
    margin-bottom: 30px; /* 下方間距 */
  }
  
  /* 菜單圖片樣式 */
  .menu-images img {
    width: 32%; /* 寬度 */
    height: auto; /* 自適應高度 */
    object-fit: cover; /* 圖片填充方式 */
    border-radius: 10px; /* 圓角 */
  }
  
  /* 食品網格布局 */
  .food-grid {
    display: grid; /* 網格布局 */
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); /* 響應式列 */
    gap: 20px; /* 網格間距 */
  }
  
  /* 食品項目樣式 */
  .food-item {
    background-color: rgba(0, 0, 0, 0.3); /* 半透明黑色背景 */
    border-radius: 8px; /* 圓角 */
    overflow: hidden; /* 隱藏溢出內容 */
  }
  
  /* 食品圖片樣式 */
  .food-item img {
    width: 100%; /* 寬度填滿容器 */
    height: 200px; /* 固定高度 */
    object-fit: cover; /* 圖片填充方式 */
  }
  
  /* 食品信息容器 */
  .food-info {
    padding: 30px; /* 內邊距 */
  }
  
  /* 食品名稱樣式 */
  .food-info h3 {
    margin: 0 0 10px 0; /* 外邊距 */
    font-size: 1em; /* 字體大小 */
  }
  
  /* 食品描述樣式 */
  .food-info p {
    margin: 0; /* 無外邊距 */
    line-height: 1.5; /* 行高 */
  }
  </style>