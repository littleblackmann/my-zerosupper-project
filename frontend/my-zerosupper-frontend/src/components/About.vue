<template>
    <div class="home">
      <h2>社畜介紹</h2>
      <p>四個社畜創立了這家漢堡店，為了不想再繼續當社畜所以拼了一波。</p>
      <div class="founders">
        <div class="founder">
          <p>社畜1</p>
          <img src="@/assets/society1.jpg" alt="社畜1" />
        </div>
        <div class="founder">
          <p>社畜2</p>
          <img src="@/assets/society2.jpg" alt="社畜2" />
        </div>
        <div class="founder">
          <p>社畜3</p>
          <img src="@/assets/society3.jpg" alt="社畜3" />
        </div>
        <div class="founder">
          <p>社畜4</p>
          <img src="@/assets/society4.jpg" alt="社畜4" />
        </div>
      </div>
    </div>
</template>
  
  <script>
  export default {
    name: 'AboutUs' // 設定組件的名稱為 AboutUs
  }
  </script>
  
  <style>
  /* 設定整體頁面的樣式 */
  .home {
    color: white; /* 設定文字顏色為白色 */
    text-align: center; /* 文字置中 */
  }
  
  /* 設定創始人區塊的樣式 */
  .founders {
    display: flex; /* 使用 Flexbox 排版 */
    justify-content: center; /* 使內容在水平方向上置中 */
    flex-wrap: wrap; /* 允許子元素換行 */
    margin-top: 55px; /* 增加上邊距 */
  }
  
  /* 設定每個創始人的樣式 */
  .founder {
    margin: 30px; /* 增加外邊距 */
    text-align: center; /* 文字置中 */
  }
  
  /* 設定創始人圖片的樣式 */
  .founders img {
    width: 300px; /* 設定圖片的寬度 */
    height: 450px; /* 設定圖片的高度 */
    object-fit: cover; /* 確保圖片按比例縮放並填充容器 */
    border-radius: 20px; /* 設定圖片邊角圓滑 */
  }
  
  /* 調整創始人標題的字體大小 */
  .founder p {
    font-size: 36px; /* 設定字體大小 */
  }
  
  /* 調整副標題的字體大小 */
  h2 {
    font-size: 36px; /* 設定字體大小 */
  }
  
  /* 調整段落文字的字體大小 */
  p {
    font-size: 21px; /* 設定字體大小 */
  }
  </style>
  