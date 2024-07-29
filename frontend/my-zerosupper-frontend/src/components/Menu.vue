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
    import './Menu.css';

    export default {
    name: 'MenuComponent', // 組件名稱
    }
</script>