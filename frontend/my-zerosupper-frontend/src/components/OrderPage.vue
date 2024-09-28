// OrderPage.vue
<template>
  <div>
    <h1>線上點餐</h1>
    <div v-for="product in products" :key="product.product_id">
      <img :src="product.image_url" :alt="product.product_name" />
      <h2>{{ product.product_name }}</h2>
      <p>價格: {{ product.price }} 元</p>
      <button @click="add_to_cart(product)">加入購物車</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      products: []
    };
  },
  mounted() {
    this.get_products();
  },
  methods: {
    get_products() {
      axios.get('/api/products')
        .then(response => {
          this.products = response.data;
        });
    },
    add_to_cart(product) {
      // 加入購物車的邏輯
    }
  }
};
</script>
