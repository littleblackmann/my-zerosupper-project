<template>
  <div class="home-page">
    <section class="home-hero" aria-labelledby="home-title">
      <div class="home-hero-overlay"></div>
      <div class="home-hero-content">
        <p class="hero-eyebrow">TAIPEI · BURGER · GOOD OLD DAYS</p>
        <h1 id="home-title">曾經有四個不想再當社畜的人，<br /><em>開了一間漢堡店。</em></h1>
        <p class="hero-intro">ZERO Supper 不只是一份菜單。這裡留下我們做漢堡、招呼客人，以及一起胡鬧又認真生活過的證明。</p>
        <div class="hero-actions">
          <router-link class="button button-primary" to="/menu">看看當年的菜單</router-link>
          <router-link class="button button-secondary" to="/about">讀我們的故事</router-link>
        </div>
      </div>
      <div class="hero-stamp" aria-hidden="true">
        <span>ZERO</span><strong>SUPPER</strong><small>GOOD BURGERS · GOOD MEMORIES</small>
      </div>
    </section>

    <section class="memory-section section-shell" aria-labelledby="memory-title">
      <div class="section-heading">
        <p class="section-kicker">A BURGER SHOP TO REMEMBER</p>
        <h2 id="memory-title">店收起來了，味道和故事還在。</h2>
      </div>
      <div class="memory-layout">
        <div class="memory-photo" role="img" aria-label="ZERO Supper 漢堡與餐點合照"><span class="photo-note">那時候的餐桌</span></div>
        <div class="memory-copy">
          <p class="lead">有些店不需要永遠營業，也能一直留在記憶裡。</p>
          <p>這個網站保留 ZERO Supper 當年的餐點、夥伴與店址，也把原本的小作品重新整理成一個真的可以使用、可以慢慢長大的地方。</p>
          <div class="memory-numbers" aria-label="ZERO Supper 的三個記憶關鍵字">
            <div><strong>4</strong><span>位創店夥伴</span></div>
            <div><strong>1</strong><span>間漢堡店</span></div>
            <div><strong>∞</strong><span>說不完的回憶</span></div>
          </div>
        </div>
      </div>
    </section>

    <section class="flavour-section">
      <div class="section-shell flavour-shell">
        <div class="section-heading section-heading-light">
          <p class="section-kicker">THE ZERO SUPPER WAY</p>
          <h2>一口漢堡，一段我們認真活過的日子。</h2>
        </div>
        <div class="feature-grid">
          <article><span>01</span><h3>真材實料</h3><p>漢堡、起司、生菜與招牌醬汁，簡單的東西更不能隨便。</p></article>
          <article><span>02</span><h3>四個社畜</h3><p>沒有厲害的起跑點，只有幾個人一起把一件事做完的傻勁。</p></article>
          <article><span>03</span><h3>留下記憶</h3><p>現在不只保存舊照片，也讓當年的網站繼續好好活著。</p></article>
        </div>
      </div>
    </section>

    <section class="visit-cta section-shell">
      <div>
        <p class="section-kicker">ONE MORE BITE</p>
        <h2>想起那顆漢堡了嗎？</h2>
        <p>先從當年的菜單開始逛，或看看這間店曾經在哪裡。</p>
      </div>
      <div class="visit-actions">
        <router-link class="button button-dark" to="/order">開始點餐</router-link>
        <router-link class="text-link" to="/location">查看店址 <span>→</span></router-link>
      </div>
    </section>
  </div>
</template>

<script>
import './HomePage.css';
export default { name: 'HomePage' };
</script>
