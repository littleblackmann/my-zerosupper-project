<template>
  <div class="order">
    <h1>線上點餐</h1>
    <div v-if="products.length === 0" class="loading">載入中...</div>
    <div v-else class="product-grid">
      <div v-for="product in products" :key="product.productId" class="product-card">
        <img :src="product.imageUrl" :alt="product.productName" @click="zoomImage(product.imageUrl)" />
        <div class="product-info">
          <h2>{{ product.productName }}</h2>
          <p class="description">{{ product.description }}</p>
          <p class="price">價格：{{ product.price }} 元</p>
          <p class="stock">庫存：{{ product.stock }}</p>
          <button @click="addToCart(product)" :disabled="product.stock === 0">
            {{ product.stock === 0 ? '暫時缺貨' : '加入購物車' }}
          </button>
        </div>
        <div v-if="product.addedToCart" class="added-message">已放入購物車！</div>
      </div>
    </div>

    <!-- 圖片放大遮罩層 -->
    <div class="image-zoom-overlay" @click="closeZoom">
      <img :src="zoomedImageSrc" alt="Zoomed image" v-if="zoomedImageSrc">
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { mapActions } from 'vuex';

export default {
  name: 'OrderComponent',
  data() {
    return {
      products: [],
      zoomedImageSrc: null
    };
  },
  mounted() {
    this.fetchProducts();
  },
  methods: {
    ...mapActions(['addToCart']),

    async fetchProducts() {
      try {
        const response = await axios.get('http://localhost:9527/products', {
          params: {
            category: 'FOOD',
            limit: 100,
            offset: 0
          }
        });
        this.products = response.data.results.map(product => ({
          ...product,
          addedToCart: false
        }));
      } catch (error) {
        console.error('獲取產品資料時發生錯誤:', error);
      }
    },

    async addToCart(product) {
      if (product.stock === 0) return;

      const productToAdd = {
        productId: product.productId,
        productName: product.productName,
        price: product.price,
        quantity: 1,
        imageUrl: product.imageUrl
      };

      await this.$store.dispatch('addToCart', productToAdd);
      product.addedToCart = true;

      setTimeout(() => {
        product.addedToCart = false;
      }, 2000);
    },

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

<style src="./Order.css"></style>