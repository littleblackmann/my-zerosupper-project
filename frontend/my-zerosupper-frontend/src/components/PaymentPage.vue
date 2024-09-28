<template>
  <div>
    <h1>付款頁面</h1>
    <form @submit.prevent="processPayment">
      <label for="card">信用卡號</label>
      <input type="text" v-model="creditCardNumber" id="card" required />
      <button type="submit">付款</button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      creditCardNumber: ''
    };
  },
  methods: {
    processPayment() {
      // 模擬付款成功，並發送訂單請求
      axios.post(`/api/users/${userId}/orders`, {
        cartItems: this.cartItems, // 將購物車商品發送到後端
      }).then(response => {
        this.$router.push(`/receipt/${response.data.orderId}`);
      });
    }
  }
};
</script>
