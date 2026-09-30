<template>
  <div class="admin-page">
    <section v-if="!isLoggedIn" class="admin-login-shell">
      <div class="admin-login-card">
        <div class="login-mark">ZS</div>
        <p class="eyebrow">ZERO SUPPER CONTROL ROOM</p>
        <h1>店主登入</h1>
        <p class="login-copy">登入後管理菜單、訂單與會員資料。</p>

        <form @submit.prevent="handleAdminLogin" class="admin-login-form">
          <label>
            <span>管理員帳號</span>
            <input v-model.trim="email" type="text" autocomplete="username" placeholder="輸入管理員帳號" required />
          </label>
          <label>
            <span>密碼</span>
            <input v-model="password" type="password" autocomplete="current-password" placeholder="輸入密碼" required />
          </label>
          <button class="button button-primary button-full" type="submit">進入後台</button>
        </form>
      </div>
    </section>

    <div v-else class="admin-shell">
      <aside class="admin-sidebar">
        <div class="admin-brand">
          <div class="admin-brand-mark">ZS</div>
          <div>
            <strong>ZERO Supper</strong>
            <span>店舖管理中心</span>
          </div>
        </div>

        <nav class="admin-nav" aria-label="後台功能">
          <button :class="{ active: activeSection === 'overview' }" type="button" @click="activeSection = 'overview'">
            <span class="nav-symbol">⌂</span>
            <span>營運概覽</span>
          </button>
          <button :class="{ active: activeSection === 'products' }" type="button" @click="activeSection = 'products'">
            <span class="nav-symbol">▦</span>
            <span>商品管理</span>
            <span class="nav-count">{{ page.total }}</span>
          </button>
          <button :class="{ active: activeSection === 'orders' }" type="button" @click="activeSection = 'orders'">
            <span class="nav-symbol">≡</span>
            <span>訂單管理</span>
            <span class="nav-count">{{ orders.length }}</span>
          </button>
          <button :class="{ active: activeSection === 'members' }" type="button" @click="activeSection = 'members'">
            <span class="nav-symbol">●</span>
            <span>會員資料</span>
            <span class="nav-count">{{ users.length }}</span>
          </button>
        </nav>

        <div class="sidebar-account">
          <div class="account-avatar">{{ userInitial(adminName) }}</div>
          <div class="account-copy">
            <strong>{{ adminName }}</strong>
            <span>Administrator</span>
          </div>
          <button class="logout-icon" type="button" aria-label="登出" title="登出" @click="handleLogout">↗</button>
        </div>
      </aside>

      <div class="admin-workspace">
        <header class="admin-topbar">
          <div>
            <p class="eyebrow">STORE ADMINISTRATION</p>
            <h1>{{ sectionTitle }}</h1>
          </div>
          <div class="topbar-actions">
            <span class="today-label">{{ todayLabel }}</span>
            <button class="button button-ghost" type="button" :disabled="refreshing" @click="fetchAdministration">
              {{ refreshing ? '更新中…' : '重新整理' }}
            </button>
          </div>
        </header>

        <div v-if="notice" class="notice" :class="`notice-${noticeType}`" role="status">
          <span>{{ notice }}</span>
          <button type="button" aria-label="關閉訊息" @click="notice = ''">×</button>
        </div>

        <div class="admin-content">
          <section v-if="activeSection === 'overview'" class="admin-section">
            <div class="welcome-banner">
              <div>
                <p class="eyebrow light">WELCOME BACK</p>
                <h2>嗨，{{ adminName }}</h2>
                <p>今天也一起把 ZERO Supper 顧好。商品、訂單與會員狀況都整理在這裡了。</p>
              </div>
              <button class="button button-gold" type="button" @click="openAddProduct">＋ 新增商品</button>
            </div>

            <div class="stat-grid">
              <article class="stat-card">
                <div class="stat-icon stat-icon-green">▦</div>
                <div>
                  <span>上架商品</span>
                  <strong>{{ page.total }}</strong>
                  <small>目前菜單品項</small>
                </div>
              </article>
              <article class="stat-card">
                <div class="stat-icon stat-icon-gold">≡</div>
                <div>
                  <span>近期訂單</span>
                  <strong>{{ orders.length }}</strong>
                  <small>最近 50 筆內</small>
                </div>
              </article>
              <article class="stat-card">
                <div class="stat-icon stat-icon-red">$</div>
                <div>
                  <span>近期營業額</span>
                  <strong>{{ formatCurrency(recentRevenue) }}</strong>
                  <small>已排除取消訂單</small>
                </div>
              </article>
              <article class="stat-card">
                <div class="stat-icon stat-icon-cream">●</div>
                <div>
                  <span>會員人數</span>
                  <strong>{{ users.length }}</strong>
                  <small>包含管理員帳號</small>
                </div>
              </article>
            </div>

            <div class="dashboard-grid">
              <article class="panel panel-wide">
                <div class="panel-heading">
                  <div>
                    <p class="eyebrow">RECENT ORDERS</p>
                    <h2>近期訂單</h2>
                  </div>
                  <button class="text-button" type="button" @click="activeSection = 'orders'">查看全部 →</button>
                </div>

                <div v-if="orders.length" class="order-summary-list">
                  <div v-for="order in orders.slice(0, 5)" :key="order.orderId" class="order-summary-row">
                    <div class="order-number">#{{ order.orderId }}</div>
                    <div class="order-customer">
                      <strong>{{ order.userEmail || '現場顧客' }}</strong>
                      <span>{{ formatArrival(order) }}</span>
                    </div>
                    <strong class="order-price">{{ formatCurrency(order.totalAmount) }}</strong>
                    <span class="status-badge" :class="`status-${statusClass(order.status)}`">{{ statusLabel(order.status) }}</span>
                  </div>
                </div>
                <div v-else class="empty-state compact">
                  <div class="empty-icon">◎</div>
                  <strong>還沒有訂單</strong>
                  <span>顧客完成點餐後，最新訂單會出現在這裡。</span>
                </div>
              </article>

              <article class="panel">
                <div class="panel-heading">
                  <div>
                    <p class="eyebrow">MENU SNAPSHOT</p>
                    <h2>菜單速覽</h2>
                  </div>
                  <button class="text-button" type="button" @click="activeSection = 'products'">管理 →</button>
                </div>

                <div v-if="products.length" class="menu-snapshot">
                  <div v-for="product in products.slice(0, 4)" :key="product.productId" class="snapshot-item">
                    <div class="snapshot-thumb">
                      <span>ZS</span>
                      <img v-if="product.imageUrl" :src="product.imageUrl" :alt="product.productName" @error="handleBrokenImage" />
                    </div>
                    <div>
                      <strong>{{ product.productName }}</strong>
                      <span>{{ categoryLabel(product.category) }} · 庫存 {{ product.stock }}</span>
                    </div>
                    <strong>{{ formatCurrency(product.price) }}</strong>
                  </div>
                </div>
                <div v-else class="empty-state compact">
                  <div class="empty-icon">▦</div>
                  <strong>菜單還是空的</strong>
                  <span>先新增第一項招牌餐點吧。</span>
                </div>
              </article>
            </div>
          </section>

          <section v-else-if="activeSection === 'products'" class="admin-section">
            <div class="section-heading">
              <div>
                <p class="eyebrow">MENU MANAGEMENT</p>
                <h2>商品管理</h2>
                <p>更新餐點內容、售價與庫存，前台菜單會同步顯示。</p>
              </div>
              <button class="button button-primary" type="button" @click="openAddProduct">＋ 新增商品</button>
            </div>

            <div class="filter-panel">
              <label class="search-field">
                <span class="search-icon">⌕</span>
                <input v-model.trim="searchQuery" type="search" placeholder="搜尋商品名稱" @keyup.enter="applyProductFilters" />
              </label>
              <select v-model="selectedCategory" aria-label="商品類別">
                <option value="">所有類別</option>
                <option value="MENU">菜單</option>
                <option value="FOOD">線上點餐</option>
                <option value="BURGER">漢堡</option>
              </select>
              <button class="button button-dark" type="button" @click="applyProductFilters">套用篩選</button>
            </div>

            <div v-if="products.length" class="product-grid">
              <article v-for="product in products" :key="product.productId" class="product-card">
                <div class="product-image">
                  <div class="product-image-fallback">ZERO<br />SUPPER</div>
                  <img v-if="product.imageUrl" :src="product.imageUrl" :alt="product.productName" @error="handleBrokenImage" />
                  <span class="category-chip">{{ categoryLabel(product.category) }}</span>
                </div>
                <div class="product-card-body">
                  <div class="product-title-row">
                    <h3>{{ product.productName }}</h3>
                    <strong>{{ formatCurrency(product.price) }}</strong>
                  </div>
                  <p>{{ product.description || '尚未填寫商品介紹。' }}</p>
                  <div class="stock-row">
                    <span>庫存狀態</span>
                    <strong :class="{ low: product.stock <= 5 }">{{ product.stock }} 份</strong>
                  </div>
                </div>
                <div class="product-card-actions">
                  <button class="button button-outline" type="button" @click="editProduct(product)">編輯內容</button>
                  <button class="icon-button danger" type="button" aria-label="刪除商品" title="刪除商品" @click="deleteProduct(product.productId)">×</button>
                </div>
              </article>
            </div>

            <div v-else class="empty-state large">
              <div class="empty-icon">▦</div>
              <strong>找不到符合條件的商品</strong>
              <span>換個關鍵字或清除類別篩選後再試一次。</span>
              <button class="text-button" type="button" @click="clearProductFilters">清除篩選</button>
            </div>

            <div v-if="page.total > limit" class="pagination">
              <button class="button button-outline" type="button" :disabled="offset === 0" @click="changePage(-1)">← 上一頁</button>
              <span>第 {{ currentPage }} 頁，共 {{ totalPages }} 頁</span>
              <button class="button button-outline" type="button" :disabled="offset + limit >= page.total" @click="changePage(1)">下一頁 →</button>
            </div>
          </section>

          <section v-else-if="activeSection === 'orders'" class="admin-section">
            <div class="section-heading">
              <div>
                <p class="eyebrow">ORDER MANAGEMENT</p>
                <h2>訂單管理</h2>
                <p>掌握顧客到店時間，並即時更新餐點製作進度。</p>
              </div>
              <span class="section-total">共 {{ orders.length }} 筆</span>
            </div>

            <div v-if="orders.length" class="table-panel">
              <div class="admin-table-wrap">
                <table class="admin-table">
                  <thead>
                    <tr>
                      <th>訂單編號</th>
                      <th>會員</th>
                      <th>預計到店</th>
                      <th>金額</th>
                      <th>訂單狀態</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="order in orders" :key="order.orderId">
                      <td><strong>#{{ order.orderId }}</strong></td>
                      <td>
                        <div class="table-user">
                          <span>{{ userInitial(order.userEmail) }}</span>
                          <div>
                            <strong>{{ order.userEmail || '現場顧客' }}</strong>
                            <small>會員訂單</small>
                          </div>
                        </div>
                      </td>
                      <td>{{ formatArrival(order) }}</td>
                      <td><strong>{{ formatCurrency(order.totalAmount) }}</strong></td>
                      <td>
                        <select class="status-select" :class="`status-${statusClass(order.status)}`" :value="order.status" @change="updateOrderStatus(order, $event.target.value)">
                          <option v-for="status in orderStatuses" :key="status" :value="status">{{ statusLabel(status) }}</option>
                        </select>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>

            <div v-else class="empty-state large">
              <div class="empty-icon">◎</div>
              <strong>目前沒有訂單</strong>
              <span>新訂單進來後就能在這裡追蹤處理進度。</span>
            </div>
          </section>

          <section v-else class="admin-section">
            <div class="section-heading">
              <div>
                <p class="eyebrow">MEMBER DIRECTORY</p>
                <h2>會員資料</h2>
                <p>查看目前註冊帳號與後台權限。</p>
              </div>
              <span class="section-total">共 {{ users.length }} 位</span>
            </div>

            <div v-if="users.length" class="member-grid">
              <article v-for="user in users" :key="user.userId" class="member-card">
                <div class="member-avatar">{{ userInitial(user.username || user.email) }}</div>
                <div class="member-main">
                  <h3>{{ user.username || user.email || '未命名會員' }}</h3>
                  <p>{{ user.email || '尚未設定電子郵件' }}</p>
                  <span v-if="user.createdAt">加入於 {{ formatDate(user.createdAt) }}</span>
                </div>
                <span class="role-badge" :class="{ admin: user.role === 'ADMIN' }">{{ roleLabel(user.role) }}</span>
              </article>
            </div>

            <div v-else class="empty-state large">
              <div class="empty-icon">●</div>
              <strong>目前沒有會員資料</strong>
              <span>顧客完成註冊後會顯示在這裡。</span>
            </div>
          </section>
        </div>
      </div>
    </div>

    <div v-if="showProductForm" class="modal-backdrop" @click.self="closeProductForm">
      <section class="product-modal" role="dialog" aria-modal="true" :aria-label="editingProductId ? '編輯商品' : '新增商品'">
        <header class="modal-header">
          <div>
            <p class="eyebrow">{{ editingProductId ? 'EDIT ITEM' : 'NEW ITEM' }}</p>
            <h2>{{ editingProductId ? '編輯商品' : '新增商品' }}</h2>
          </div>
          <button class="modal-close" type="button" aria-label="關閉" @click="closeProductForm">×</button>
        </header>

        <form class="product-form" @submit.prevent="submitProduct">
          <label class="field-wide">
            <span>商品名稱</span>
            <input v-model.trim="productForm.productName" type="text" placeholder="例如：經典牛肉堡" required />
          </label>
          <label>
            <span>商品類別</span>
            <select v-model="productForm.category" required>
              <option value="MENU">菜單</option>
              <option value="FOOD">線上點餐</option>
              <option value="BURGER">漢堡</option>
            </select>
          </label>
          <label>
            <span>售價</span>
            <div class="input-prefix"><span>NT$</span><input v-model.number="productForm.price" type="number" min="1" step="1" required /></div>
          </label>
          <label>
            <span>庫存</span>
            <input v-model.number="productForm.stock" type="number" min="0" step="1" required />
          </label>
          <label class="field-wide">
            <span>圖片網址</span>
            <input v-model.trim="productForm.imageUrl" type="url" placeholder="https://example.com/burger.jpg" required />
          </label>
          <label class="field-wide">
            <span>商品介紹</span>
            <textarea v-model.trim="productForm.description" rows="4" placeholder="簡短介紹口味與特色"></textarea>
          </label>

          <div class="modal-actions field-wide">
            <button class="button button-outline" type="button" @click="closeProductForm">取消</button>
            <button class="button button-primary" type="submit">{{ editingProductId ? '儲存變更' : '新增商品' }}</button>
          </div>
        </form>
      </section>
    </div>
  </div>
</template>

<script>
import api, { apiErrorMessage, clearSession, saveSession } from '../services/api';

const emptyProduct = () => ({
  productName: '',
  category: 'FOOD',
  imageUrl: '',
  price: 0,
  stock: 0,
  description: ''
});

export default {
  name: 'AdminUser',

  data() {
    return {
      email: '',
      password: '',
      isLoggedIn: false,
      activeSection: 'overview',
      refreshing: false,
      notice: '',
      noticeType: 'success',
      noticeTimer: null,
      products: [],
      orders: [],
      users: [],
      orderStatuses: ['RECEIVED', 'CONFIRMED', 'PREPARING', 'READY', 'COMPLETED', 'CANCELLED'],
      productForm: emptyProduct(),
      editingProductId: null,
      showProductForm: false,
      selectedCategory: '',
      searchQuery: '',
      orderBy: 'created_date',
      sort: 'desc',
      offset: 0,
      limit: 6,
      page: {
        total: 0,
        results: []
      }
    };
  },

  computed: {
    adminName() {
      return localStorage.getItem('userEmail') || 'littleblack';
    },

    sectionTitle() {
      return {
        overview: '營運概覽',
        products: '商品管理',
        orders: '訂單管理',
        members: '會員資料'
      }[this.activeSection];
    },

    todayLabel() {
      return new Intl.DateTimeFormat('zh-TW', {
        month: 'long',
        day: 'numeric',
        weekday: 'short'
      }).format(new Date());
    },

    recentRevenue() {
      return this.orders
        .filter(order => order.status !== 'CANCELLED')
        .reduce((total, order) => total + Number(order.totalAmount || 0), 0);
    },

    currentPage() {
      return Math.floor(this.offset / this.limit) + 1;
    },

    totalPages() {
      return Math.max(1, Math.ceil(this.page.total / this.limit));
    }
  },

  created() {
    this.isLoggedIn = !!localStorage.getItem('userToken') && localStorage.getItem('userRole') === 'ADMIN';
    if (this.isLoggedIn) {
      this.fetchAdministration();
    }
  },

  beforeUnmount() {
    window.clearTimeout(this.noticeTimer);
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
          this.showNotice('這個帳號沒有管理員權限。', 'error');
        }
      } catch (error) {
        console.error('登入錯誤:', error.response ? error.response.data : error.message);
        this.showNotice(apiErrorMessage(error, '登入失敗，請檢查帳號和密碼。'), 'error');
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

    async fetchAdministration() {
      this.refreshing = true;
      try {
        await Promise.allSettled([this.fetchProducts(), this.fetchOrders(), this.fetchUsers()]);
      } finally {
        this.refreshing = false;
      }
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
        this.products = this.page.results || [];
      } catch (error) {
        console.error('無法取得商品列表:', error.response ? error.response.data : error.message);
        this.showNotice('無法取得商品列表，請確認後端服務是否啟動。', 'error');
      }
    },

    applyProductFilters() {
      this.offset = 0;
      this.fetchProducts();
    },

    clearProductFilters() {
      this.searchQuery = '';
      this.selectedCategory = '';
      this.offset = 0;
      this.fetchProducts();
    },

    openAddProduct() {
      this.editingProductId = null;
      this.productForm = emptyProduct();
      this.showProductForm = true;
    },

    editProduct(product) {
      this.editingProductId = product.productId;
      this.productForm = {
        productName: product.productName || '',
        category: product.category || 'FOOD',
        imageUrl: product.imageUrl || '',
        price: Number(product.price || 0),
        stock: Number(product.stock || 0),
        description: product.description || ''
      };
      this.showProductForm = true;
    },

    closeProductForm() {
      this.showProductForm = false;
      this.editingProductId = null;
      this.productForm = emptyProduct();
    },

    async submitProduct() {
      if (!this.productForm.productName || this.productForm.price <= 0 || this.productForm.stock < 0) {
        this.showNotice('請填寫商品名稱，並確認售價與庫存數量。', 'error');
        return;
      }

      try {
        if (this.editingProductId) {
          await api.put(`/admin/products/${this.editingProductId}`, this.productForm);
          this.showNotice('商品內容已更新。');
        } else {
          await api.post('/admin/products', this.productForm);
          this.showNotice('新商品已加入菜單。');
        }
        this.closeProductForm();
        await this.fetchProducts();
      } catch (error) {
        console.error('儲存商品失敗:', error.response ? error.response.data : error.message);
        this.showNotice(apiErrorMessage(error, '商品儲存失敗，請稍後再試。'), 'error');
      }
    },

    async deleteProduct(productId) {
      if (!window.confirm('確定要刪除此商品嗎？這個動作無法復原。')) {
        return;
      }

      try {
        await api.delete(`/admin/products/${productId}`);
        if (this.products.length === 1 && this.offset > 0) {
          this.offset = Math.max(0, this.offset - this.limit);
        }
        await this.fetchProducts();
        this.showNotice('商品已刪除。');
      } catch (error) {
        console.error('刪除商品失敗:', error);
        this.showNotice(apiErrorMessage(error, '商品刪除失敗。'), 'error');
      }
    },

    changePage(direction) {
      this.offset = Math.max(0, this.offset + direction * this.limit);
      this.fetchProducts();
      window.scrollTo({ top: 0, behavior: 'smooth' });
    },

    async fetchOrders() {
      try {
        const response = await api.get('/admin/orders', { params: { limit: 50, offset: 0 } });
        this.orders = response.data.results || [];
      } catch (error) {
        console.error('取得訂單失敗:', error);
      }
    },

    async fetchUsers() {
      try {
        const response = await api.get('/admin/users');
        this.users = response.data || [];
      } catch (error) {
        console.error('取得會員失敗:', error);
      }
    },

    async updateOrderStatus(order, status) {
      try {
        const response = await api.patch(`/admin/orders/${order.orderId}/status`, { status });
        Object.assign(order, response.data);
        this.showNotice(`訂單 #${order.orderId} 已更新為「${this.statusLabel(status)}」。`);
      } catch (error) {
        this.showNotice(apiErrorMessage(error, '更新訂單狀態失敗。'), 'error');
        this.fetchOrders();
      }
    },

    showNotice(message, type = 'success') {
      this.notice = message;
      this.noticeType = type;
      window.clearTimeout(this.noticeTimer);
      this.noticeTimer = window.setTimeout(() => {
        this.notice = '';
      }, 4200);
    },

    formatCurrency(value) {
      return new Intl.NumberFormat('zh-TW', {
        style: 'currency',
        currency: 'TWD',
        maximumFractionDigits: 0
      }).format(Number(value || 0));
    },

    formatArrival(order) {
      if (!order.arrivalDate && !order.arrivalTime) return '尚未設定';
      return [order.arrivalDate, order.arrivalTime].filter(Boolean).join(' ');
    },

    formatDate(value) {
      const date = new Date(value);
      return Number.isNaN(date.getTime()) ? '未知日期' : date.toLocaleDateString('zh-TW');
    },

    categoryLabel(category) {
      return {
        MENU: '菜單',
        FOOD: '線上點餐',
        BURGER: '漢堡'
      }[category] || category || '未分類';
    },

    statusLabel(status) {
      return {
        RECEIVED: '已收到',
        CONFIRMED: '已確認',
        PREPARING: '製作中',
        READY: '可取餐',
        COMPLETED: '已完成',
        CANCELLED: '已取消'
      }[status] || status || '未知';
    },

    statusClass(status) {
      return String(status || 'unknown').toLowerCase();
    },

    roleLabel(role) {
      return role === 'ADMIN' ? '管理員' : '一般會員';
    },

    userInitial(value) {
      return String(value || 'Z').trim().charAt(0).toUpperCase();
    },

    handleBrokenImage(event) {
      event.currentTarget.style.display = 'none';
    }
  }
};
</script>

<style scoped>
@import './AdminUser.css';
</style>
