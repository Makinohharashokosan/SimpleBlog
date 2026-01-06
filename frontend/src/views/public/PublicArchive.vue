<template>
  <div class="archive-page">
    <header class="page-header">
      <h1 class="page-title">{{ $t('titles.archive') }}</h1>
      <div class="header-line"></div>
    </header>

    <div v-if="isLoading" class="loading-state">
      <div class="spinner"></div>
    </div>
    
    <div v-else-if="archives.length === 0" class="empty-state">
      {{ $t('common.no_archive') }}
    </div>

    <main v-else>
      <section 
        v-for="(yearGroup, groupIndex) in archives" 
        :key="yearGroup.year" 
        class="year-block"
        :style="{ '--delay': `${groupIndex * 150}ms` }"
      >
        <h2 class="year-title">{{ yearGroup.year }}</h2>
        <transition-group tag="ul" name="list" class="articles-list" appear>
          <li
            v-for="(article, index) in yearGroup.articles"
            :key="article.id"
            class="article-item"
            :style="{ '--delay': `${(groupIndex * 100) + (index * 50)}ms` }"
          >
            <router-link :to="`/article/${article.id}`" class="article-entry">
              <span class="date">{{ formatDate(article.createdAt) }}</span>
              <span class="title">{{ article.title }}</span>
            </router-link>
          </li>
        </transition-group>
      </section>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { getAllArticles } from '@/api/articles';

const articles = ref([]);
const isLoading = ref(true);

const archives = computed(() => {
  const grouped = {};
  articles.value.forEach(article => {
    const date = new Date(article.createdAt);
    const year = date.getFullYear();
    if (!grouped[year]) grouped[year] = [];
    grouped[year].push(article);
  });
  return Object.keys(grouped)
    .sort((a, b) => b - a)
    .map(year => ({
      year: year,
      articles: grouped[year],
    }));
});

onMounted(async () => {
  try {
    const response = await getAllArticles({ page: 0, size: 999, sort: 'createdAt,desc' });
    articles.value = response.content;
  } catch (error) {
    console.error("Failed to fetch articles for archive:", error);
  } finally {
    isLoading.value = false;
  }
});

const formatDate = (dateString) => {
  const date = new Date(dateString);
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');
  return `${month}-${day}`;
};
</script>

<style scoped>
.archive-page {
  max-width: 800px;
  margin: 0 auto;
  padding: 80px 20px;
}

/* Header */
.page-header {
  text-align: center;
  margin-bottom: 80px;
  animation: fadeIn 0.8s ease-out;
}

.page-title {
  font-family: var(--font-family-display);
  font-size: 48px;
  color: var(--color-primary-text);
  letter-spacing: 2px;
}

.header-line {
  width: 40px;
  height: 2px;
  background: var(--color-functional-element);
  margin: 20px auto 0;
  opacity: 0.5;
}

/* Year Section */
.year-block {
  margin-bottom: 64px;
  opacity: 0;
  animation: slideUpFade 0.6s ease-out forwards;
  animation-delay: var(--delay);
}

.year-title {
  font-family: var(--font-family-display);
  font-size: 80px;
  color: rgba(136, 136, 136, 0.1);
  line-height: 1;
  margin-bottom: -40px;
  position: relative;
  z-index: 0;
  transform: translateX(-20px);
}

/* List */
.articles-list {
  list-style: none;
  padding: 0;
  position: relative;
  z-index: 1;
}

.article-item {
  margin-bottom: 24px;
  opacity: 0;
  animation: slideInRight 0.5s ease-out forwards;
  animation-delay: var(--delay);
}

.article-entry {
  display: flex;
  align-items: baseline;
  gap: 24px;
  text-decoration: none;
  padding: 8px 0;
  border-bottom: 1px dashed rgba(136, 136, 136, 0.2);
  transition: all 0.3s ease;
}

.article-entry:hover {
  border-bottom-color: var(--color-functional-element);
  padding-left: 10px;
}

.date {
  font-family: 'Fira Code', monospace;
  font-size: 14px;
  color: var(--color-functional-element);
}

.title {
  font-family: var(--font-family-serif);
  font-size: 18px;
  color: var(--color-primary-text);
  transition: color 0.3s;
}

.article-entry:hover .title {
  color: var(--color-functional-element);
}

/* States */
.loading-state, .empty-state {
  text-align: center;
  padding: 80px 0;
  color: var(--color-secondary-structure);
  font-family: var(--font-family-serif);
}

.spinner {
  width: 40px;
  height: 40px;
  border: 3px solid var(--color-card-shadow);
  border-top-color: var(--color-functional-element);
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin: 0 auto;
}

@keyframes spin { to { transform: rotate(360deg); } }
@keyframes fadeIn { from { opacity: 0; } to { opacity: 1; } }
@keyframes slideUpFade {
  from { opacity: 0; transform: translateY(40px); }
  to { opacity: 1; transform: translateY(0); }
}
@keyframes slideInRight {
  from { opacity: 0; transform: translateX(-20px); }
  to { opacity: 1; transform: translateX(0); }
}
</style>