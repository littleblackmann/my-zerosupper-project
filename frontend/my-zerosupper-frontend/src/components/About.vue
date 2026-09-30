<template>
  <div class="about-page">
    <section class="about-hero">
      <div class="about-hero-inner">
        <p class="page-kicker">OUR STORY · 四個社畜的故事</p>
        <h1>不想一輩子只替別人打卡，<br />所以我們開了一間漢堡店。</h1>
        <p>沒有偉大的商業計畫，只有四個人、一點不服輸，還有對好吃漢堡的執著。</p>
      </div>
    </section>

    <section class="story-section section-shell">
      <div class="story-label">THE BEGINNING</div>
      <div class="story-copy">
        <h2>一間店，是四個人的共同作品。</h2>
        <p>ZERO Supper 是我們離開日常軌道、認真拼過一次的證明。從想菜單、做餐、招呼客人到每天收店，所有狼狽與快樂都是真的。</p>
        <p>店雖然已經成為過去，但這些照片和網站還能把那段日子留住。這裡不是一本完美的品牌履歷，而是一封寫給當年自己的信。</p>
      </div>
    </section>

    <section class="founders-section">
      <div class="section-shell">
        <div class="about-section-heading">
          <p class="page-kicker">THE CREW</p>
          <h2>四位創店夥伴</h2>
          <p>稱號先保留「社畜」，因為那就是故事開始的樣子。</p>
        </div>

        <div class="founder-grid">
          <article v-for="(founder, index) in founders" :key="founder.image" class="founder-card" :class="{ 'founder-card-offset': index % 2 === 1 }">
            <div class="founder-photo-wrap">
              <img :src="founder.image" :alt="`ZERO Supper 創店夥伴 ${index + 1}`" />
              <span>{{ String(index + 1).padStart(2, '0') }}</span>
            </div>
            <div class="founder-info">
              <p>FOUNDING CREW</p>
              <h3>社畜 {{ index + 1 }}</h3>
            </div>
          </article>
        </div>
      </div>
    </section>

    <section class="about-quote section-shell">
      <span aria-hidden="true">“</span>
      <blockquote>我們沒有逃離生活，只是曾經一起把想做的事情，真的做了出來。</blockquote>
      <router-link class="about-menu-link" to="/menu">接著看當年的菜單 →</router-link>
    </section>
  </div>
</template>

<script>
import './About.css';
import society1 from '@/assets/society1.jpg';
import society2 from '@/assets/society2.jpg';
import society3 from '@/assets/society3.jpg';
import society4 from '@/assets/society4.jpg';

export default {
  name: 'AboutUs',
  data() {
    return {
      founders: [
        { image: society1 },
        { image: society2 },
        { image: society3 },
        { image: society4 },
      ],
    };
  },
};
</script>
