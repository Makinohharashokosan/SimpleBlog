<template>
  <div class="home-page">
    <!-- 1. Hero Section -->
    <header class="hero-section">
      <div class="hero-content">
        <h1 class="blog-title">
          <span class="title-main">{{ $t('home.hero_title') }}</span>
        </h1>
        <p class="blog-subtitle">{{ $t('home.hero_subtitle') }}</p>
        <div class="hero-line"></div>
      </div>
    </header>

    <!-- 2. Featured Section (if available) -->
    <section v-if="featuredArticles.length" class="featured-section">
      <div class="section-header">
        <h2 class="section-title">{{ $t('home.featured') }}</h2>
        <span class="section-subtitle">Curated for you</span>
      </div>
      <div class="featured-grid">
        <ArticleSummaryCard
          v-for="(article, index) in featuredArticles"
          :key="article.id"
          :article="article"
          class="featured-card-wrapper"
          :style="{ '--delay': `${index * 150}ms` }"
        />
      </div>
    </section>

    <!-- 3. Main Content Area (2 Columns) -->
    <div class="main-content">
      <!-- Left Column: Latest Posts -->
      <main class="latest-posts" :style="{ '--delay': '400ms' }">
        <div class="section-header">
          <h2 class="section-title">{{ $t('home.latest') }}</h2>
        </div>
        
        <div class="posts-list">
          <ArticleSummaryCard
            v-for="(article, index) in latestArticles"
            :key="article.id"
            :article="article"
            class="latest-card-wrapper"
            :style="{ '--delay': `${index * 100 + 400}ms` }"
          />
        </div>
        
        <!-- View All Link -->
        <div class="view-all-wrapper">
          <router-link to="/archive" class="view-all-btn">
            {{ $t('common.read_more') }}
            <span class="arrow">→</span>
          </router-link>
        </div>
      </main>

      <!-- Right Column: Sidebar -->
      <aside class="sidebar" :style="{ '--delay': '600ms' }">
        <!-- Trending Module -->
        <div v-if="trendingArticles.length" class="sidebar-module">
          <h3 class="module-title">{{ $t('home.trending') }}</h3>
          <ol class="trending-list">
            <li v-for="article in trendingArticles" :key="article.id" class="trending-item">
              <router-link :to="`/article/${article.id}`" class="trending-link">
                <span class="rank-number">0{{ trendingArticles.indexOf(article) + 1 }}</span>
                <span class="link-text">{{ article.title }}</span>
              </router-link>
            </li>
          </ol>
        </div>
        
        <!-- Tag Cloud Module -->
        <div v-if="tags.length" class="sidebar-module">
          <h3 class="module-title">{{ $t('home.tag_cloud') }}</h3>
          <div class="tag-cloud">
            <router-link
              v-for="tag in tags"
              :key="tag.id"
              :to="`/tags/${tag.name}`"
              class="tag-link"
            >
              <span class="hash">#</span>{{ tag.name }}
            </router-link>
          </div>
        </div>
      </aside>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useSettingsStore } from '@/stores/settings';
import { getFeaturedArticles, getAllArticles, getTrendingArticles } from '@/api/articles';
import { getAllTags } from '@/api/tags';
import ArticleSummaryCard from '@/components/common/ArticleSummaryCard.vue';

const settingsStore = useSettingsStore();

const featuredArticles = ref([]);
const latestArticles = ref([]);
const trendingArticles = ref([]);
const tags = ref([]);

onMounted(async () => {
  await settingsStore.fetchSettings();

  const [featured, latest, trending, allTags] = await Promise.all([
    getFeaturedArticles(),
    getAllArticles({ page: 0, size: 5, sort: 'createdAt,desc' }),
    getTrendingArticles(5),
    getAllTags(),
  ]);

  featuredArticles.value = featured;
  latestArticles.value = latest.content;
  trendingArticles.value = trending;
  tags.value = allTags;
});
</script>

<style scoped>
.home-page {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 40px 100px;
}

/* 1. Hero Section */
.hero-section {
  text-align: center;
  padding: 120px 0 100px;
  opacity: 0;
  animation: fadeIn 1s ease-out forwards;
}

.hero-content {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.title-main {
  font-family: var(--font-family-display); /* Cinzel */
  font-size: 80px;
  font-weight: 400; /* Cinzel is bold by default usually */
  color: var(--color-primary-text);
  letter-spacing: 0.05em;
  display: block;
}

.blog-subtitle {
  font-family: var(--font-family-artistic); /* Pinyon Script */
  font-size: 32px;
  color: var(--color-secondary-structure);
  margin-top: 16px;
  opacity: 0.8;
}

.hero-line {
  width: 2px;
  height: 60px;
  background-color: var(--color-functional-element);
  margin-top: 40px;
  opacity: 0.3;
}

/* Common Section Headers */
.section-header {
  display: flex;
  justify-content: space-between;
  align-items: baseline;
  margin-bottom: 40px;
  border-bottom: 1px solid rgba(136, 136, 136, 0.2);
  padding-bottom: 16px;
}

.section-title {
  font-family: var(--font-family-sans-serif);
  font-size: 24px;
  letter-spacing: 2px;
  text-transform: uppercase;
  color: var(--color-primary-text);
}

.section-subtitle {
  font-family: var(--font-family-serif);
  font-style: italic;
  color: var(--color-secondary-structure);
}

/* 2. Featured Section */
.featured-section {
  margin-bottom: 100px;
}

.featured-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
  gap: 32px;
}

.featured-card-wrapper {
  opacity: 0;
  animation: slideUpFade 0.8s ease-out forwards;
  animation-delay: var(--delay);
}

/* 3. Main Content */
.main-content {
  display: flex;
  gap: 80px;
}

.latest-posts {
  flex: 2;
  opacity: 0;
  animation: slideUpFade 0.8s ease-out forwards;
}

.posts-list {
  display: flex;
  flex-direction: column;
  gap: 48px; /* More breathing room */
}

/* View All Btn */
.view-all-wrapper {
  margin-top: 48px;
  text-align: center;
}
.view-all-btn {
  display: inline-flex;
  align-items: center;
  gap: 12px;
  padding: 16px 32px;
  border: 1px solid var(--color-functional-element);
  color: var(--color-functional-element);
  text-decoration: none;
  text-transform: uppercase;
  font-family: var(--font-family-sans-serif);
  letter-spacing: 2px;
  font-size: 14px;
  font-weight: 600;
  transition: all 0.3s ease;
}
.view-all-btn:hover {
  background-color: var(--color-functional-element);
  color: var(--color-main-bg);
}

/* Sidebar */
.sidebar {
  flex: 1;
  padding-top: 20px;
  opacity: 0;
  animation: fadeIn 1s ease-out forwards;
}

.sidebar-module {
  margin-bottom: 64px;
}

.module-title {
  font-family: var(--font-family-display);
  font-size: 18px;
  letter-spacing: 1px;
  color: var(--color-primary-text);
  margin-bottom: 24px;
  position: relative;
  display: inline-block;
}

.module-title::after {
  content: '';
  position: absolute;
  bottom: -8px;
  left: 0;
  width: 40px;
  height: 2px;
  background-color: var(--color-functional-element);
}

.trending-list {
  list-style: none; /* Custom numbering */
  padding: 0;
}

.trending-item {
  margin-bottom: 24px;
}

.trending-link {
  display: flex;
  align-items: baseline;
  gap: 16px;
  text-decoration: none;
  text-decoration: none;
}

.rank-number {
  font-family: var(--font-family-display);
  font-size: 24px;
  color: rgba(136, 136, 136, 0.2);
  transition: color 0.3s;
}

.link-text {
  font-family: var(--font-family-serif);
  font-size: 16px;
  color: var(--color-primary-text);
  transition: color 0.3s;
  line-height: 1.4;
}

.trending-link:hover .rank-number,
.trending-link:hover .link-text {
  color: var(--color-functional-element);
}

/* Tag Cloud */
.tag-cloud {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
}

.tag-link {
  color: var(--color-secondary-structure);
  text-decoration: none;
  font-family: 'Fira Code', monospace;
  font-size: 13px;
  border: 1px solid rgba(136,136,136, 0.2);
  padding: 8px 16px;
  border-radius: 20px;
  transition: all 0.3s ease;
}

.tag-link:hover {
  border-color: var(--color-functional-element);
  color: var(--color-functional-element);
  transform: translateY(-2px);
}

.hash {
  opacity: 0.5;
  margin-right: 4px;
}

/* Animations */
@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

@keyframes slideUpFade {
  from { opacity: 0; transform: translateY(40px); }
  to { opacity: 1; transform: translateY(0); }
}

@media (max-width: 900px) {
  .title-main { font-size: 48px; }
  .main-content { flex-direction: column; gap: 40px; }
  .featured-grid { grid-template-columns: 1fr; }
}
</style>