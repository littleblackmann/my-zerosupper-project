import { createStore } from 'vuex';

export default createStore({
  state: {
    cart: []
  },
  mutations: {
    addToCart(state, product) {
      const existingItem = state.cart.find(item => item.productId === product.productId);
      if (existingItem) {
        existingItem.quantity++;
      } else {
        state.cart.push({ ...product, quantity: 1 });
      }
    },
    removeFromCart(state, productId) {
      const index = state.cart.findIndex(item => item.productId === productId);
      if (index !== -1) {
        state.cart.splice(index, 1);
      }
    },
    updateCartItemQuantity(state, { productId, quantity }) {
      const item = state.cart.find(item => item.productId === productId);
      if (item) {
        item.quantity = quantity;
      }
    },
    clearCart(state) {
      state.cart = [];
    }
  },
  actions: {
    addToCart({ commit }, product) {
      commit('addToCart', product);
    },
    removeFromCart({ commit }, productId) {
      commit('removeFromCart', productId);
    },
    updateCartItemQuantity({ commit }, payload) {
      commit('updateCartItemQuantity', payload);
    },
    clearCart({ commit }) {
      commit('clearCart');
    }
  },
  getters: {
    cartTotal: state => {
      return state.cart.reduce((total, item) => total + item.price * item.quantity, 0);
    },
    cartItemCount: state => {
      return state.cart.reduce((count, item) => count + item.quantity, 0);
    }
  }
});