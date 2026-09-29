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
import { mapState } from 'vuex';

export default {
  data() {
    return {
      creditCardNumber: ''
    };
  },
  computed: {
    ...mapState({ cartItems: 'cart' })
  },
  methods: {
    processPayment() {
      // 模擬付款成功，並發送訂單請求
      const userId = localStorage.getItem('userId');

      if (!userId) {
        this.$router.push('/login');
        return;
      }

      axios.post(`/api/users/${userId}/orders`, {
        cartItems: this.cartItems, // 將購物車商品發送到後端
      }).then(response => {
        this.$router.push(`/receipt/${response.data.orderId}`);
      });
    }
  }
};
</script>
