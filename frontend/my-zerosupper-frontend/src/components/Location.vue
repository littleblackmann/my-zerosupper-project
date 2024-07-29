<template>
  <div class="location">
    <h1>地址：台北市松山區南京東路五段23巷6弄29號</h1> <!-- 顯示餐廳地址 -->
    <div id="map" class="map"></div> <!-- 地圖顯示區域 -->
  </div>
</template>

<script>
import './Location.css';

/* global google */ // 告訴 ESLint google 是全局變量（防止 ESLint 報錯）

export default {
  name: 'LocationComponent', // 設定組件的名稱為 LocationComponent
  mounted() {
    this.initMap(); // 組件掛載後初始化地圖
  },
  methods: {
    initMap() {
      const restaurantLocation = { lat: 25.05220232417501, lng: 121.55905498393943 }; // 餐廳的緯度和經度
      const map = new google.maps.Map(document.getElementById("map"), { // 初始化地圖
        zoom: 15, // 設定地圖的縮放級別
        center: restaurantLocation, // 設定地圖中心為餐廳位置
      });
      new google.maps.Marker({ // 在地圖上添加標記
        position: restaurantLocation, // 標記的位置
        map: map, // 標記所在的地圖
      });
    },
  },
}
</script>