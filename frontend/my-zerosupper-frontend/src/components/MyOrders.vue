<template>
  <section class="orders-page">
    <h1>我的訂單</h1>
    <p v-if="loading">載入中...</p>
    <p v-else-if="error" class="error-message">{{ error }}</p>
    <p v-else-if="orders.length === 0">目前還沒有訂單。</p>
    <article v-for="order in orders" :key="order.orderId" class="order-card">
      <header>
        <strong>訂單 #{{ order.orderId }}</strong>
        <span class="status">{{ statusLabel(order.status) }}</span>
      </header>
      <p>到店時間：{{ order.arrivalDate }} {{ order.arrivalTime }}</p>
      <ul>
        <li v-for="item in order.items" :key="`${order.orderId}-${item.productId}`">
          {{ item.productName }} × {{ item.quantity }} — {{ item.lineTotal }} 元
        </li>
      </ul>
      <strong>總計：{{ order.totalAmount }} 元</strong>
    </article>
  </section>
</template>

<script>
import api, { apiErrorMessage } from '../services/api';

export default {
  name: 'MyOrders',
  data() {
    return {
      orders: [],
      loading: true,
      error: '',
    };
  },
  async created() {
    try {
      const response = await api.get('/orders/me');
      this.orders = response.data;
    } catch (error) {
      this.error = apiErrorMessage(error, '訂單載入失敗。');
    } finally {
      this.loading = false;
    }
  },
  methods: {
    statusLabel(status) {
      return {
        RECEIVED: '已收到',
        CONFIRMED: '已確認',
        PREPARING: '製作中',
        READY: '可取餐',
        COMPLETED: '已完成',
        CANCELLED: '已取消',
      }[status] || status;
    },
  },
};
</script>

<style scoped>
.orders-page { max-width: 900px; margin: 0 auto; padding: 2rem 1rem 5rem; }
.order-card { margin: 1rem 0; padding: 1.25rem; border-radius: 14px; background: rgba(255, 255, 255, 0.94); box-shadow: 0 5px 18px rgba(0, 0, 0, 0.1); }
.order-card header { display: flex; justify-content: space-between; gap: 1rem; }
.status { color: #80511f; font-weight: 700; }
.error-message { color: #a32626; }
</style>
