<template>
  <div class="menu">
    <h1>菜單</h1>
    <div v-if="loading.menus">載入中...</div>
    <div v-else-if="error.menus" class="error-message">載入菜單時發生錯誤。請稍後再試。</div>
    <div v-else class="menu-images">
      <div v-for="menu in menus" :key="menu.productId">
        <img :src="menu.imageUrl" :alt="menu.productName" class="menu-image" @click="zoomImage(menu.imageUrl)" />
      </div>
    </div>

    <h1>漢堡</h1>
    <div v-if="loading.burgers">載入中...</div>
    <div v-else-if="error.burgers" class="error-message">載入漢堡資料時發生錯誤。請稍後再試。</div>
    <div v-else class="food-grid">
      <div v-for="burger in burgers" :key="burger.productId" class="food-item">
        <img :src="burger.imageUrl" :alt="burger.productName" class="food-image" @click="zoomImage(burger.imageUrl)" />
        <div class="food-info">
          <h3>{{ burger.productName }}</h3>
          <p>{{ burger.description }}</p>
        </div>
      </div>
    </div>

    <!-- 新增的圖片放大遮罩層 -->
    <div class="image-zoom-overlay" @click="closeZoom">
      <img :src="zoomedImageSrc" alt="Zoomed image" v-if="zoomedImageSrc">
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'MenuComponent',
  data() {
    return {
      menus: [],
      burgers: [],
      loading: {
        menus: true,
        burgers: true
      },
      error: {
        menus: false,
        burgers: false
      },
      zoomedImageSrc: null  // 新增的數據屬性
    };
  },
  mounted() {
    this.fetchMenus();
    this.fetchBurgers();
  },
  methods: {
    async fetchMenus() {
      try {
        const response = await axios.get('http://localhost:9527/products', {
          params: {
            category: 'MENU',
            limit: 100,
            offset: 0
          }
        });
        this.menus = response.data.results;
      } catch (error) {
        console.error('獲取菜單資料時發生錯誤:', error);
        this.error.menus = true;
      } finally {
        this.loading.menus = false;
      }
    },
    async fetchBurgers() {
      try {
        const response = await axios.get('http://localhost:9527/products', {
          params: {
            category: 'BURGER',
            limit: 100,
            offset: 0
          }
        });
        this.burgers = response.data.results;
      } catch (error) {
        console.error('獲取漢堡資料時發生錯誤:', error);
        this.error.burgers = true;
      } finally {
        this.loading.burgers = false;
      }
    },
    // 新增的方法
    zoomImage(imageSrc) {
      this.zoomedImageSrc = imageSrc;
      document.querySelector('.image-zoom-overlay').style.display = 'flex';
    },
    closeZoom() {
      this.zoomedImageSrc = null;
      document.querySelector('.image-zoom-overlay').style.display = 'none';
    }
  }
};
</script>

<style>
@import './Menu.css';
</style>