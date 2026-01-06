<template>
  <div class="search-page">
    <header class="page-header">
      <h1 class="page-title">{{ $t('titles.search') }}</h1>
      <div class="header-line"></div>
    </header>

    <!-- Search Form -->
    <div class="search-container">
      <form @submit.prevent="performSearch(0)" class="search-form">
        <input
          v-model="query"
          type="search"
          :placeholder="$t('search.placeholder')"
          class="search-input"
          autofocus
        />
        <button type="submit" class="search-icon-btn">
          <span class="icon">⌕</span>
        </button>
        <div class="input-border"></div>
      </form>
    </div>

    <!-- Results Area -->
    <main class="results-area">
      <div v-if="searchState === 'loading'" class="loading-state">
        <div class="spinner"></div>
        <p>{{ $t('search.loading') }}</p>
      </div>
      
      <div v-else-if="searchState === 'no-results'" class="empty-state">
        <span class="icon">?</span>
        <p>{{ $t('search.no_results', { query: lastQuery }) }}</p>
      </div>

      <div v-else-if="searchState === 'initial'" class="initial-state">
        <p>{{ $t('search.initial') }}</p>
      </div>

      <div v-else-if="searchState === 'has-results'">
        <transition-group tag="div" name="list" class="articles-grid" appear>
          <ArticleSummaryCard
            v-for="(article, index) in articles"
            :key="article.id"
            :article="article"
            class="article-card-wrapper"
            :style="{ '--delay': `${index * 100}ms` }"
          />
        </transition-group>

        <div class="pagination-wrapper">
          <BasePagination
            :current-page="pageNumber"
            :is-first="isFirstPage"
            :is-last="isLastPage"
            @page-change="performSearch"
          />
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { searchArticles } from '@/api/articles';
import ArticleSummaryCard from '@/components/common/ArticleSummaryCard.vue';
import BasePagination from '@/components/common/BasePagination.vue';

const query = ref('');
const lastQuery = ref('');
const articles = ref([]);
const searchState = ref('initial');
const pageNumber = ref(0);
const isFirstPage = ref(true);
const isLastPage = ref(false);

const performSearch = async (page = 0) => {
  if (!query.value.trim()) return;

  searchState.value = 'loading';
  lastQuery.value = query.value;
  
  try {
    const response = await searchArticles(query.value, { page, size: 8 });
    if (response.content && response.content.length > 0) {
      articles.value = response.content;
      pageNumber.value = response.number;
      isFirstPage.value = response.first;
      isLastPage.value = response.last;
      searchState.value = 'has-results';
    } else {
      articles.value = [];
      searchState.value = 'no-results';
    }
  } catch (error) {
    console.error("Failed to search articles:", error);
    searchState.value = 'no-results';
  }
};
</script>

<style scoped>
.search-page {
  max-width: 1000px;
  margin: 0 auto;
  padding: 80px 20px;
}

/* Header */
.page-header {
  text-align: center;
  margin-bottom: 60px;
  animation: fadeIn 0.8s ease-out;
}

.page-title {
  font-family: var(--font-family-display);
  font-size: 48px;
  color: var(--color-primary-text);
}

.header-line {
  width: 40px;
  height: 2px;
  background: var(--color-functional-element);
  margin: 20px auto 0;
  opacity: 0.5;
}

/* Search Form */
.search-container {
  max-width: 600px;
  margin: 0 auto 80px;
  animation: slideUpFade 0.8s ease-out;
}

.search-form {
  position: relative;
  display: flex;
  align-items: center;
}

.search-input {
  width: 100%;
  border: none;
  background: transparent;
  padding: 16px 48px 16px 16px; /* Space for icon */
  font-family: var(--font-family-serif);
  font-size: 24px;
  color: var(--color-primary-text);
  outline: none;
}

.search-input::placeholder {
  color: var(--color-secondary-structure);
  opacity: 0.3;
}

.search-icon-btn {
  position: absolute;
  right: 8px;
  background: none;
  border: none;
  font-size: 32px;
  color: var(--color-functional-element);
  cursor: pointer;
  transition: transform 0.2s ease;
}

.search-icon-btn:hover {
  transform: scale(1.1);
}

.input-border {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 1px;
  background: var(--color-secondary-structure);
  opacity: 0.3;
  transition: all 0.3s ease;
}

.search-input:focus ~ .input-border {
  height: 2px;
  background: var(--color-functional-element);
  opacity: 1;
  box-shadow: 0 4px 10px rgba(0,0,0,0.05);
}

/* Results */
.articles-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
  gap: 32px;
}

.article-card-wrapper {
  opacity: 0;
  animation: slideUpFade 0.6s ease-out forwards;
  animation-delay: var(--delay);
}

.pagination-wrapper {
  margin-top: 64px;
}

/* States */
.loading-state, .empty-state, .initial-state {
  text-align: center;
  padding: 60px 0;
  color: var(--color-secondary-structure);
  font-family: var(--font-family-serif);
  font-size: 18px;
  animation: fadeIn 0.5s ease-out;
}

.empty-state .icon {
  font-size: 48px;
  display: block;
  margin-bottom: 24px;
  opacity: 0.2;
}

.spinner {
  width: 40px;
  height: 40px;
  border: 3px solid var(--color-card-shadow);
  border-top-color: var(--color-functional-element);
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin: 0 auto 24px;
}

@keyframes spin { to { transform: rotate(360deg); } }
@keyframes fadeIn { from { opacity: 0; } to { opacity: 1; } }
@keyframes slideUpFade {
  from { opacity: 0; transform: translateY(30px); }
  to { opacity: 1; transform: translateY(0); }
}
</style>