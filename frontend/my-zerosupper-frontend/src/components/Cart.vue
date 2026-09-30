<template>
  <div class="cart-page">
    <section class="cart-heading section-shell">
      <p>YOUR ORDER</p>
      <h1>購物車</h1>
      <span>確認餐點，再告訴我們你想什麼時候來取餐。</span>
    </section>

    <section v-if="cart.length === 0" class="empty-cart section-shell">
      <div class="empty-cart-icon" aria-hidden="true">0</div>
      <h2>購物車還是空的</h2>
      <p>先去看看今天想吃哪份漢堡套餐吧。</p>
      <router-link to="/order">前往線上點餐</router-link>
    </section>

    <section v-else class="cart-layout section-shell">
      <div class="cart-list">
        <p class="cart-step">01 · 餐點明細</p>
        <article v-for="item in cart" :key="item.productId" class="cart-item">
          <img :src="item.imageUrl" :alt="item.productName" />
          <div class="cart-item-copy">
            <div><h2>{{ item.productName }}</h2><strong>NT$ {{ item.price }}</strong></div>
            <p>ZERO Supper 經典餐點</p>
            <div class="cart-item-actions">
              <div class="quantity-control" aria-label="調整數量">
                <button type="button" :disabled="item.quantity <= 1" aria-label="減少一份" @click="updateQuantity(item.productId, item.quantity - 1)">−</button>
                <span>{{ item.quantity }}</span>
                <button type="button" aria-label="增加一份" @click="updateQuantity(item.productId, item.quantity + 1)">＋</button>
              </div>
              <button class="remove-button" type="button" @click="removeFromCart(item.productId)">移除</button>
            </div>
          </div>
        </article>
      </div>

      <aside class="checkout-card">
        <p class="cart-step">02 · 預約取餐</p>
        <div class="checkout-total"><span>訂單總計</span><strong>NT$ {{ cartTotal }}</strong></div>
        <div class="checkout-divider"></div>
        <label for="arrivalDate">取餐日期<input id="arrivalDate" v-model="arrivalDate" type="date" required /></label>
        <label for="arrivalTime">取餐時間<input id="arrivalTime" v-model="arrivalTime" type="time" required /></label>
        <label for="phoneNumber">聯絡電話<input id="phoneNumber" v-model="phoneNumber" type="tel" autocomplete="tel" placeholder="例如：0912 345 678" required /></label>
        <p v-if="checkoutError" class="checkout-error" role="alert">{{ checkoutError }}</p>
        <button class="checkout-button" type="button" :disabled="checkingOut" @click="checkout">
          {{ checkingOut ? '正在送出…' : '確認並送出訂單' }}
        </button>
        <small>送出前會確認會員登入，餐點價格仍以後端核算結果為準。</small>
      </aside>
    </section>

    <section v-if="confirmationVisible" class="order-confirmation section-shell" role="status">
      <span aria-hidden="true">✓</span>
      <div>
        <p>ORDER RECEIVED</p>
        <h2>ZERO Supper 已收到你的訂單。</h2>
        <p>預約取餐：{{ arrivalDate }} {{ arrivalTime }} · {{ phoneNumber }}</p>
        <ul><li v-for="item in purchasedItems" :key="item.productId">{{ item.productName }} × {{ item.quantity }}</li></ul>
      </div>
    </section>
  </div>
</template>

<script>
import { mapState, mapGetters, mapActions } from 'vuex';
import api, { apiErrorMessage } from '../services/api';
import './Cart.css';

export default {
  name: 'CartComponent',
  data() {
    return { arrivalDate: '', arrivalTime: '', phoneNumber: '', confirmationVisible: false, purchasedItems: [], checkoutError: '', checkingOut: false };
  },
  computed: { ...mapState(['cart']), ...mapGetters(['cartTotal']) },
  methods: {
    ...mapActions(['removeFromCart', 'updateCartItemQuantity', 'clearCart']),
    updateQuantity(productId, quantity) {
      if (quantity > 0) this.updateCartItemQuantity({ productId, quantity });
    },
    async checkout() {
      this.checkoutError = '';
      if (!localStorage.getItem('userToken')) {
        this.$router.push({ path: '/login', query: { redirect: '/cart' } });
        return;
      }
      if (!this.arrivalDate || !this.arrivalTime || !this.phoneNumber) {
        this.checkoutError = '請先填寫取餐日期、時間與聯絡電話。';
        return;
      }
      const orderItems = this.cart.map(item => ({ productId: item.productId, quantity: item.quantity }));
      if (orderItems.length === 0) return;
      this.checkingOut = true;
      try {
        const response = await api.post('/orders', { items: orderItems, arrivalDate: this.arrivalDate, arrivalTime: this.arrivalTime, phoneNumber: this.phoneNumber });
        if (response.status === 201) {
          this.purchasedItems = response.data.items;
          this.clearCart();
          this.confirmationVisible = true;
        }
      } catch (error) {
        this.checkoutError = apiErrorMessage(error, '結帳失敗，請稍後再試。');
      } finally {
        this.checkingOut = false;
      }
    },
  },
};
</script>
