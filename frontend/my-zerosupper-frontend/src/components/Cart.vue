<template>
    <div class="cart">
      <h2>購物車</h2>
      <div v-if="cart.length === 0" class="empty-cart">購物車是空的</div>
      <div v-else class="cart-content">
        <div v-for="item in cart" :key="item.productId" class="cart-item">
          <img :src="item.imageUrl" :alt="item.productName" class="cart-item-image" />
          <div class="cart-item-details">
            <h3>{{ item.productName }}</h3>
            <p class="price">價格：{{ item.price }} 元</p>
            <div class="quantity-control">
              <button @click="updateQuantity(item.productId, item.quantity - 1)" :disabled="item.quantity <= 1">-</button>
              <span>{{ item.quantity }}</span>
              <button @click="updateQuantity(item.productId, item.quantity + 1)">+</button>
            </div>
            <button @click="removeFromCart(item.productId)" class="remove-btn">移除</button>
          </div>
        </div>
  
        <div class="cart-total">
          <h3>總計：{{ cartTotal }} 元</h3>
  
          <div class="input-group">
            <label for="arrivalDate">選擇到達日期：</label>
            <input type="date" id="arrivalDate" v-model="arrivalDate" required />
          </div>
  
          <div class="input-group">
            <label for="arrivalTime">選擇到達時間：</label>
            <input type="time" id="arrivalTime" v-model="arrivalTime" required />
          </div>
  
          <div class="input-group">
            <label for="phoneNumber">請輸入電話號碼：</label>
            <input type="tel" id="phoneNumber" v-model="phoneNumber" required />
          </div>
  
          <button @click="checkout" class="checkout-btn">結帳</button>
        </div>
      </div>
  
      <div v-if="confirmationVisible" class="confirmation">
        <h3>感謝您的購買：</h3>
        <p>電子郵件: {{ userEmail }}</p>
        <p>購買項目:</p>
        <ul>
          <li v-for="item in purchasedItems" :key="item.productId">{{ item.productName }} x {{ item.quantity }}</li>
        </ul>
        <p>到場日期: {{ arrivalDate }} {{ arrivalTime }}</p>
        <p>電話號碼: {{ phoneNumber }}</p>
        <p>ZERO Supper已收到囉，請用手機拍下這項紀錄。</p>
        <p>再次感謝您，期待您在 {{ arrivalDate }} 的到來。</p>
      </div>
    </div>
  </template>
  
  <script>
  import { mapState, mapGetters, mapActions } from 'vuex';
  import axios from 'axios';
  
  export default {
    name: 'CartComponent',
    data() {
      return {
        arrivalDate: '',
        arrivalTime: '',
        phoneNumber: '',
        confirmationVisible: false,
        userEmail: '',
        purchasedItems: []
      };
    },
    computed: {
      ...mapState(['cart']),
      ...mapGetters(['cartTotal'])
    },
    methods: {
      ...mapActions(['removeFromCart', 'updateCartItemQuantity', 'clearCart']),
  
      updateQuantity(productId, quantity) {
        if (quantity > 0) {
          this.updateCartItemQuantity({ productId, quantity });
        }
      },
  
      async checkout() {
        const userToken = localStorage.getItem('userToken');
        const userId = localStorage.getItem('userId');
        this.userEmail = localStorage.getItem('userEmail');
  
        if (!userToken || !userId) {
          alert('請先登入再進行結帳。');
          this.$router.push('/login');
          return;
        }
  
        if (!this.arrivalDate || !this.arrivalTime || !this.phoneNumber) {
          alert('請填寫所有必要資訊。');
          return;
        }
  
        const orderItems = this.cart.map(item => ({
          productId: item.productId,
          productName: item.productName,
          quantity: item.quantity
        }));
  
        if (orderItems.length === 0) {
          alert('購物車中沒有商品，無法結帳！');
          return;
        }
  
        const totalAmount = this.cartTotal;
  
        try {
          const response = await axios.post(
            `http://localhost:9527/users/${userId}/orders`, 
            { 
              buyItemList: orderItems,
              totalAmount,
              userEmail: this.userEmail,
              arrivalDate: this.arrivalDate,
              arrivalTime: this.arrivalTime,
              phoneNumber: this.phoneNumber
            }, 
            {
              headers: {
                Authorization: `Bearer ${userToken}`
              }
            }
          );
  
          if (response.status === 201) {
            console.log('結帳成功', response.data);
            this.clearCart();
            this.confirmationVisible = true;
            this.purchasedItems = orderItems;
          } else {
            alert('結帳失敗，請重試。');
          }
        } catch (error) {
          console.error('結帳失敗', error.response ? error.response.data : error.message);
          alert('結帳失敗，請重試。' + (error.response ? error.response.data.message : ''));
        }
      }
    }
  };
  </script>
  
  <style src="./Cart.css" scoped></style>