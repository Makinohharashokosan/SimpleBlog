<template>
  <div class="list-page">
    <header class="page-header">
      <h1 class="page-title">{{ $t('titles.featured') }}</h1>
    </header>

    <div v-if="isLoading" class="state-info">{{ $t('common.loading') }}</div>
    
    <div v-else-if="articles.length === 0" class="state-info">
      {{ $t('common.no_featured') }}
    </div>

    <main v-else>
      <!-- 
        TransitionGroup provides animation for list rendering.
        We'll make each card stagger in with a slight delay.
      -->
      <transition-group tag="div" name="stagger-fade" class="articles-list" appear>
        <ArticleSummaryCard
          v-for="(article, index) in articles"
          :key="article.id"
          :article="article"
          :style="{ '--delay': `${index * 50}ms` }"
        />
      </transition-group>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getFeaturedArticles } from '@/api/articles';
import ArticleSummaryCard from '@/components/common/ArticleSummaryCard.vue';

const articles = ref([]);
const isLoading = ref(true);

onMounted(async () => {
  try {
    const response = await getFeaturedArticles();
    articles.value = response;
  } catch (error) {
    console.error("Failed to fetch featured articles:", error);
  } finally {
    isLoading.value = false;
  }
});
</script>

<style scoped>
.list-page {
  max-width: var(--layout-max-width-list);
  margin: 0 auto;
  padding: 48px 20px;
}

.page-header {
  margin-bottom: 48px;
}

.page-title {
  font-family: var(--font-family-sans-serif);
  font-size: 36px;
  font-weight: bold;
  color: var(--color-primary-text);
}

.state-info {
  text-align: center;
  font-family: var(--font-family-serif);
  color: var(--color-secondary-structure);
  font-size: 18px;
  padding: 80px 0;
}

.articles-list {
  display: grid;
  gap: 24px;
}

/* Staggered list animation */
.stagger-fade-enter-active,
.stagger-fade-leave-active {
  transition: opacity 0.5s ease, transform 0.5s ease;
  transition-delay: var(--delay); /* Custom property for stagger delay */
}

.stagger-fade-enter-from,
.stagger-fade-leave-to {
  opacity: 0;
  transform: translateY(20px);
}
</style>