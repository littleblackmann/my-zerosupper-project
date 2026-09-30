<template>
  <div class="order-page">
    <section class="order-intro">
      <div class="section-shell order-intro-inner">
        <div>
          <p>ONLINE ORDER</p>
          <h1>想吃，就把它帶走。</h1>
        </div>
        <div class="order-intro-note">
          <span>01 選餐</span><span>02 購物車</span><span>03 預約取餐</span>
          <small>可以先逛、先加購物車，結帳時再登入。</small>
        </div>
      </div>
    </section>

    <section class="order-products section-shell">
      <div class="order-section-heading">
        <div>
          <p>AVAILABLE NOW</p>
          <h2>今天想吃哪一份？</h2>
        </div>
        <router-link to="/cart">查看購物車 →</router-link>
      </div>

      <div v-if="loading" class="order-state">正在準備菜單…</div>
      <div v-else-if="error" class="order-state order-error">餐點載入失敗，請稍後再試。</div>
      <div v-else-if="products.length === 0" class="order-state">目前沒有可供應的餐點。</div>
      <div v-else class="product-grid">
        <article v-for="product in products" :key="product.productId" class="product-card">
          <button class="product-image" type="button" @click="zoomImage(product.imageUrl)">
            <img :src="product.imageUrl" :alt="product.productName" />
            <span>{{ product.stock > 0 ? `剩餘 ${product.stock} 份` : '暫時缺貨' }}</span>
          </button>
          <div class="product-info">
            <p class="product-category">ZERO SUPPER SET</p>
            <div class="product-title-row">
              <h2>{{ product.productName }}</h2>
              <strong>NT$ {{ Number(product.price).toFixed(0) }}</strong>
            </div>
            <p class="product-description">{{ product.description }}</p>
            <button class="add-cart-button" type="button" :disabled="product.stock === 0" @click="addToCart(product)">
              <span>{{ product.stock === 0 ? '暫時缺貨' : '加入購物車' }}</span>
              <span aria-hidden="true">＋</span>
            </button>
            <p v-if="product.addedToCart" class="added-message" role="status">已經替你放進購物車了！</p>
          </div>
        </article>
      </div>
    </section>

    <div v-if="zoomedImageSrc" class="order-image-modal" role="dialog" aria-modal="true" aria-label="放大的餐點圖片" @click.self="closeZoom">
      <button type="button" aria-label="關閉放大圖片" @click="closeZoom">×</button>
      <img :src="zoomedImageSrc" alt="放大的餐點圖片" />
    </div>
  </div>
</template>

<script>
import api from '../services/api';
import './Order.css';

export default {
  name: 'OrderComponent',
  data() {
    return { products: [], zoomedImageSrc: null, loading: true, error: false };
  },
  mounted() {
    this.fetchProducts();
  },
  methods: {
    async fetchProducts() {
      try {
        const response = await api.get('/products', { params: { category: 'FOOD', limit: 100, offset: 0 } });
        this.products = response.data.results.map(product => ({ ...product, addedToCart: false }));
      } catch (error) {
        console.error('獲取產品資料時發生錯誤:', error);
        this.error = true;
      } finally {
        this.loading = false;
      }
    },
    async addToCart(product) {
      if (product.stock === 0) return;
      await this.$store.dispatch('addToCart', {
        productId: product.productId,
        productName: product.productName,
        price: product.price,
        quantity: 1,
        imageUrl: product.imageUrl,
      });
      product.addedToCart = true;
      setTimeout(() => { product.addedToCart = false; }, 2000);
    },
    zoomImage(imageSrc) { this.zoomedImageSrc = imageSrc; },
    closeZoom() { this.zoomedImageSrc = null; },
  },
};
</script>
