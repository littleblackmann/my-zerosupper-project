<template>
  <div class="location-page">
    <section class="location-copy">
      <div class="location-inner">
        <p class="location-kicker">WHERE IT ALL HAPPENED</p>
        <h1>故事發生在<br />南京東路的小巷裡。</h1>
        <p class="location-lead">台北市松山區南京東路五段 23 巷 6 弄 29 號</p>
        <p class="location-note">店已成為回憶，地址留在這裡，紀念我們曾經在這個地方認真生活過。</p>
        <a class="map-button" href="https://www.google.com/maps/search/?api=1&query=台北市松山區南京東路五段23巷6弄29號" target="_blank" rel="noreferrer">用 Google 地圖開啟 <span>↗</span></a>
      </div>
    </section>

    <section class="map-panel" aria-label="ZERO Supper 舊址地圖">
      <iframe
        title="ZERO Supper 舊址地圖"
        src="https://www.google.com/maps?q=台北市松山區南京東路五段23巷6弄29號&output=embed"
        loading="lazy"
        referrerpolicy="no-referrer-when-downgrade"
      ></iframe>
      <div class="map-label"><span>ZERO Supper</span><strong>舊店址</strong></div>
    </section>
  </div>
</template>

<script>
import './Location.css';
export default { name: 'LocationComponent' };
</script>
