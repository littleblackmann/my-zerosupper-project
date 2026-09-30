<template>
    <div class="admin-user">
      <h2>後台管理</h2>
  
      <div v-if="!isLoggedIn">
        <form @submit.prevent="handleAdminLogin">
          <div class="form-group">
            <label for="email">管理員郵箱：</label>
            <input type="email" id="email" v-model="email" required />
          </div>
          <div class="form-group">
            <label for="password">密碼：</label>
            <input type="password" id="password" v-model="password" required />
          </div>
          <button type="submit">登入</button>
        </form>
      </div>
  
      <div v-else>
        <h3>歡迎，黑管理員！</h3>
        <button @click="handleLogout">登出</button>
  
        <h4>篩選商品：</h4>
        <div class="filter-bar">
          <input v-model="searchQuery" placeholder="搜尋商品" />
          <select v-model="selectedCategory">
            <option value="">所有類別</option>
            <option value="MENU">菜單</option>
            <option value="FOOD">線上點餐</option>
            <option value="BURGER">漢堡</option>
          </select>
          <button @click="fetchProducts">查詢</button>
        </div>
  
        <h4>管理商品：</h4>
        <div>
          <button @click="showAddProductForm = true">新增商品</button>
          <div v-if="showAddProductForm" class="edit-product-form">
            <h4>新增商品</h4>
            <form @submit.prevent="addProduct">
              <div class="form-group">
                <label for="new-product-name">商品名稱：</label>
                <input id="new-product-name" v-model="newProduct.productName" placeholder="商品名稱" required />
              </div>
              <div class="form-group">
                <label for="new-product-category">類別：</label>
                <select id="new-product-category" v-model="newProduct.category" required>
                  <option value="MENU">菜單</option>
                  <option value="FOOD">線上點餐</option>
                  <option value="BURGER">漢堡</option>
                </select>
              </div>
              <div class="form-group">
                <label for="new-product-image">圖片URL：</label>
                <input id="new-product-image" v-model="newProduct.imageUrl" placeholder="圖片URL" required />
              </div>
              <div class="form-group">
                <label for="new-product-price">價格：</label>
                <input id="new-product-price" type="number" v-model.number="newProduct.price" placeholder="價格" required />
              </div>
              <div class="form-group">
                <label for="new-product-stock">庫存：</label>
                <input id="new-product-stock" type="number" v-model.number="newProduct.stock" placeholder="庫存" required />
              </div>
              <div class="form-group">
                <label for="new-product-description">描述：</label>
                <textarea id="new-product-description" v-model="newProduct.description" placeholder="描述"></textarea>
              </div>
              <div class="form-actions">
                <button type="submit">確認新增</button>
                <button type="button" @click="cancelAddProduct" class="cancel-button">取消</button>
              </div>
            </form>
          </div>
        </div>
  
        <h4>商品列表：</h4>
        <ul class="product-list">
          <li v-for="product in products" :key="product.productId" class="product-item">
            <div class="product-info">
              <strong>{{ product.productName }}</strong> - {{ product.price }} 元
            </div>
            <div class="product-actions">
              <button @click="editProduct(product)">編輯</button>
              <button @click="deleteProduct(product.productId)">刪除</button>
            </div>
          </li>
        </ul>
  
        <div v-if="editingProduct" class="edit-product-form">
          <h4>編輯商品</h4>
          <form @submit.prevent="updateProduct">
            <div class="form-group">
              <label for="edit-product-name">商品名稱：</label>
              <input id="edit-product-name" v-model="editingProduct.productName" placeholder="商品名稱" required />
            </div>
            <div class="form-group">
              <label for="edit-product-category">類別：</label>
              <select id="edit-product-category" v-model="editingProduct.category" required>
                <option value="MENU">菜單</option>
                <option value="FOOD">線上點餐</option>
                <option value="BURGER">漢堡</option>
              </select>
            </div>
            <div class="form-group">
              <label for="edit-product-image">圖片URL：</label>
              <input id="edit-product-image" v-model="editingProduct.imageUrl" placeholder="圖片URL" required />
            </div>
            <div class="form-group">
              <label for="edit-product-price">價格：</label>
              <input id="edit-product-price" type="number" v-model.number="editingProduct.price" placeholder="價格" required />
            </div>
            <div class="form-group">
              <label for="edit-product-stock">庫存：</label>
              <input id="edit-product-stock" type="number" v-model.number="editingProduct.stock" placeholder="庫存" required />
            </div>
            <div class="form-group">
              <label for="edit-product-description">描述：</label>
              <textarea id="edit-product-description" v-model="editingProduct.description" placeholder="描述"></textarea>
            </div>
            <div class="form-actions">
              <button type="submit">確認更新</button>
              <button type="button" @click="cancelEditProduct" class="cancel-button">取消</button>
            </div>
          </form>
        </div>
  
        <div v-if="page.total > limit" class="pagination">
          <button @click="changePage(-1)" :disabled="offset === 0">上一頁</button>
          <button @click="changePage(1)" :disabled="offset + limit >= page.total">下一頁</button>
        </div>

        <h4>近期訂單：</h4>
        <div class="admin-table-wrap">
          <table class="admin-table">
            <thead>
              <tr><th>編號</th><th>會員</th><th>到店時間</th><th>金額</th><th>狀態</th></tr>
            </thead>
            <tbody>
              <tr v-for="order in orders" :key="order.orderId">
                <td>#{{ order.orderId }}</td>
                <td>{{ order.userEmail }}</td>
                <td>{{ order.arrivalDate }} {{ order.arrivalTime }}</td>
                <td>{{ order.totalAmount }} 元</td>
                <td>
                  <select :value="order.status" @change="updateOrderStatus(order, $event.target.value)">
                    <option v-for="status in orderStatuses" :key="status" :value="status">{{ status }}</option>
                  </select>
                </td>
              </tr>
            </tbody>
          </table>
        </div>

        <h4>會員：</h4>
        <ul class="product-list">
          <li v-for="user in users" :key="user.userId" class="product-item">
            <div class="product-info">{{ user.email }} · {{ user.role }}</div>
          </li>
        </ul>
      </div>
    </div>
  </template>
  
  <script>
  import api, { apiErrorMessage, clearSession, saveSession } from '../services/api';
  
  export default {
    name: 'AdminUser',
    data() {
      return {
        email: '',
        password: '',
        isLoggedIn: false,
        products: [],
        orders: [],
        users: [],
        orderStatuses: ['RECEIVED', 'CONFIRMED', 'PREPARING', 'READY', 'COMPLETED', 'CANCELLED'],
        newProduct: {
          productName: '',
          category: '',
          imageUrl: '',
          price: 0,
          stock: 0,
          description: ''
        },
        editingProduct: null,
        showAddProductForm: false,
        selectedCategory: '',
        searchQuery: '',
        orderBy: 'created_date',
        sort: 'desc',
        offset: 0,
        limit: 5,
        page: {
          total: 0,
          results: []
        }
      };
    },
  
    created() {
      this.isLoggedIn = !!localStorage.getItem('userToken') && localStorage.getItem('userRole') === 'ADMIN';
      if (this.isLoggedIn) {
        this.fetchAdministration();
      }
    },
  
    methods: {
      async handleAdminLogin() {
        try {
          const response = await api.post('/auth/login', {
            email: this.email,
            password: this.password
          });
          if (response.status === 200 && response.data.role === 'ADMIN') {
            saveSession(response.data);
            this.isLoggedIn = true;
            this.fetchAdministration();
          } else {
            clearSession();
            alert('這個帳號沒有管理員權限。');
          }
        } catch (error) {
          console.error('登入錯誤:', error.response ? error.response.data : error.message);
          alert(apiErrorMessage(error, '登入失敗，請檢查您的郵箱和密碼。'));
        }
      },
  
      async handleLogout() {
        try {
          await api.post('/auth/logout');
        } catch (error) {
          console.warn('後端登出失敗，仍會清除本機登入狀態。', error);
        }
        clearSession();
        this.isLoggedIn = false;
        this.$router.push('/login');
      },

      fetchAdministration() {
        this.fetchProducts();
        this.fetchOrders();
        this.fetchUsers();
      },
  
      async fetchProducts() {
        try {
          const response = await api.get('/products', {
            params: {
              category: this.selectedCategory || undefined,
              search: this.searchQuery || undefined,
              orderBy: this.orderBy,
              sort: this.sort,
              limit: this.limit,
              offset: this.offset
            }
          });
          this.page = response.data;
          this.products = this.page.results;
        } catch (error) {
          console.error('無法取得商品列表:', error.response ? error.response.data : error.message);
          alert('獲取商品列表失敗。');
        }
      },
  
      async addProduct() {
        if (!this.newProduct.productName || this.newProduct.price <= 0 || this.newProduct.stock < 0) {
          alert('請填寫所有必填欄位且價格和庫存必須大於0！');
          return;
        }
        try {
          const response = await api.post('/admin/products', this.newProduct);
          if (response.status === 201) {
            this.showAddProductForm = false;
            this.newProduct = { productName: '', category: '', imageUrl: '', price: 0, stock: 0, description: '' };
            this.fetchProducts();
            alert('商品新增成功！');
          }
        } catch (error) {
          console.error('新增商品失敗:', error.response ? error.response.data : error.message);
          alert('新增商品失敗。');
        }
      },
  
      cancelAddProduct() {
        this.showAddProductForm = false;
        this.newProduct = { productName: '', category: '', imageUrl: '', price: 0, stock: 0, description: '' };
      },
  
      editProduct(product) {
        this.editingProduct = { ...product };
      },
  
      async updateProduct() {
        try {
          const updatedProduct = {
            productName: this.editingProduct.productName,
            category: this.editingProduct.category,
            imageUrl: this.editingProduct.imageUrl,
            price: this.editingProduct.price,
            stock: this.editingProduct.stock,
            description: this.editingProduct.description
          };
  
          const response = await api.put(`/admin/products/${this.editingProduct.productId}`, updatedProduct);
          if (response.status === 200) {
            const index = this.products.findIndex(p => p.productId === this.editingProduct.productId);
            if (index !== -1) {
              this.products[index] = response.data;
            }
            this.editingProduct = null;
            this.fetchProducts();
            alert('商品更新成功！');
          } else {
            alert(`更新商品失敗。狀態碼: ${response.status}`);
          }
        } catch (error) {
          console.error('更新商品失敗:', error);
          const errorMessage = error.response && error.response.data
            ? JSON.stringify(error.response.data)
            : error.message;
          alert('更新商品失敗。錯誤信息: ' + errorMessage);
        }
      },
  
      cancelEditProduct() {
        this.editingProduct = null;
      },
  
      async deleteProduct(productId) {
        if (confirm('確定要刪除此商品嗎？')) {
          try {
            const response = await api.delete(`/admin/products/${productId}`);
            if (response.status === 204) {
              this.products = this.products.filter(p => p.productId !== productId);
              this.fetchProducts();
              alert('商品刪除成功！');
            }
          } catch (error) {
            console.error('刪除商品失敗:', error);
            alert('刪除商品失敗。錯誤信息: ' + (error.response ? JSON.stringify(error.response.data) : error.message));
          }
        }
      },
  
      changePage(direction) {
        this.offset += direction * this.limit;
        this.fetchProducts();
      },

      async fetchOrders() {
        try {
          const response = await api.get('/admin/orders', { params: { limit: 50, offset: 0 } });
          this.orders = response.data.results;
        } catch (error) {
          console.error('取得訂單失敗:', error);
        }
      },

      async fetchUsers() {
        try {
          const response = await api.get('/admin/users');
          this.users = response.data;
        } catch (error) {
          console.error('取得會員失敗:', error);
        }
      },

      async updateOrderStatus(order, status) {
        try {
          const response = await api.patch(`/admin/orders/${order.orderId}/status`, { status });
          Object.assign(order, response.data);
        } catch (error) {
          alert(apiErrorMessage(error, '更新訂單狀態失敗。'));
          this.fetchOrders();
        }
      }
    }
  };
  </script>
  
  <style scoped>
  @import './AdminUser.css';
  </style>
