<template>
  <div class="menu-page">
    <section class="menu-intro">
      <div class="section-shell menu-intro-inner">
        <div>
          <p class="menu-kicker">THE ORIGINAL MENU</p>
          <h1>當年的菜單，<br />現在看還是會餓。</h1>
        </div>
        <p>不是復刻一間還在營業的店，而是把記憶裡的招牌味道好好保存下來。</p>
      </div>
    </section>

    <section class="menu-content section-shell">
      <div class="menu-section-heading">
        <div><span>01</span><div><p>MENU ARCHIVE</p><h2>紀念菜單</h2></div></div>
        <p>點擊圖片可以放大查看。</p>
      </div>
      <div v-if="loading.menus" class="menu-state">正在翻出以前的菜單…</div>
      <div v-else-if="error.menus" class="menu-state menu-error">菜單暫時載入失敗，請稍後再試。</div>
      <div v-else class="menu-archive-grid">
        <button v-for="menu in menus" :key="menu.productId" class="menu-archive-card" type="button" @click="zoomImage(menu.imageUrl)">
          <img :src="menu.imageUrl" :alt="menu.productName" />
          <span>點擊放大</span>
        </button>
      </div>
    </section>

    <section class="burger-section">
      <div class="section-shell">
        <div class="menu-section-heading">
          <div><span>02</span><div><p>SIGNATURE BURGER</p><h2>漢堡</h2></div></div>
          <router-link to="/order">前往線上點餐 →</router-link>
        </div>
        <div v-if="loading.burgers" class="menu-state">漢堡正在上桌…</div>
        <div v-else-if="error.burgers" class="menu-state menu-error">餐點資料暫時載入失敗，請稍後再試。</div>
        <div v-else class="burger-grid">
          <article v-for="burger in burgers" :key="burger.productId" class="burger-card">
            <button class="burger-image-button" type="button" @click="zoomImage(burger.imageUrl)">
              <img :src="burger.imageUrl" :alt="burger.productName" />
              <span class="burger-badge">ZERO CLASSIC</span>
            </button>
            <div class="burger-info">
              <div class="burger-title-row">
                <h3>{{ burger.productName }}</h3>
                <strong v-if="burger.price">NT$ {{ Number(burger.price).toFixed(0) }}</strong>
              </div>
              <p>{{ burger.description }}</p>
            </div>
          </article>
        </div>
      </div>
    </section>

    <div v-if="zoomedImageSrc" class="image-zoom-overlay" role="dialog" aria-modal="true" aria-label="放大的菜單圖片" @click.self="closeZoom">
      <button class="zoom-close" type="button" aria-label="關閉放大圖片" @click="closeZoom">×</button>
      <img :src="zoomedImageSrc" alt="放大的菜單或餐點圖片" />
    </div>
  </div>
</template>

<script>
import api from '../services/api';
import './Menu.css';

export default {
  name: 'MenuComponent',
  data() {
    return {
      menus: [],
      burgers: [],
      loading: { menus: true, burgers: true },
      error: { menus: false, burgers: false },
      zoomedImageSrc: null,
    };
  },
  mounted() {
    this.fetchMenus();
    this.fetchBurgers();
  },
  methods: {
    async fetchMenus() {
      try {
        const response = await api.get('/products', { params: { category: 'MENU', limit: 100, offset: 0 } });
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
        const response = await api.get('/products', { params: { category: 'BURGER', limit: 100, offset: 0 } });
        this.burgers = response.data.results;
      } catch (error) {
        console.error('獲取漢堡資料時發生錯誤:', error);
        this.error.burgers = true;
      } finally {
        this.loading.burgers = false;
      }
    },
    zoomImage(imageSrc) { this.zoomedImageSrc = imageSrc; },
    closeZoom() { this.zoomedImageSrc = null; },
  },
};
</script>
