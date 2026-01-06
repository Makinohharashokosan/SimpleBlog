html
<template>
  <div class="list-page">
    <header class="page-header">
      <h1 class="page-title">标签：{{ tagName }}</h1>
    </header>

    <div v-if="isLoading" class="state-info">正在加载文章...</div>
    
    <div v-else-if="articles.length === 0" class="state-info">
      该标签下暂无文章
    </div>

    <main v-else>
      <transition-group tag="div" name="stagger-fade" class="articles-list" appear>
        <ArticleSummaryCard
          v-for="(article, index) in articles"
          :key="article.id"
          :article="article"
          :style="{ '--delay': `${index * 50}ms` }"
        />
      </transition-group>

      <BasePagination
        :current-page="pageNumber"
        :is-first="isFirstPage"
        :is-last="isLastPage"
        @page-change="fetchTaggedArticles"
      />
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRoute } from 'vue-router';
import { getArticlesByTag } from '@/api/tags';
import ArticleSummaryCard from '@/components/common/ArticleSummaryCard.vue';
import BasePagination from '@/components/common/BasePagination.vue';

const route = useRoute();
const articles = ref([]);
const isLoading = ref(true);

// Pagination state
const pageNumber = ref(0);
const isFirstPage = ref(true);
const isLastPage = ref(false);
const tagName = ref(route.params.tagName);

const fetchTaggedArticles = async (page = 0) => {
  isLoading.value = true;
  try {
    const response = await getArticlesByTag(tagName.value, { page, size: 10 });
    articles.value = response.content;
    pageNumber.value = response.number;
    isFirstPage.value = response.first;
    isLastPage.value = response.last;
  } catch (error) {
    console.error(`Failed to fetch articles for tag ${tagName.value}:`, error);
  } finally {
    isLoading.value = false;
  }
};

onMounted(() => {
  fetchTaggedArticles();
});

// Watch for route changes to handle navigation between different tag pages
watch(
  () => route.params.tagName,
  (newTagName) => {
    if (newTagName) {
      tagName.value = newTagName;
      fetchTaggedArticles();
    }
  }
);
</script>

<style scoped>
/* Scoped styles are identical to PublicFeatured.vue */
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
  transition-delay: var(--delay);
}

.stagger-fade-enter-from,
.stagger-fade-leave-to {
  opacity: 0;
  transform: translateY(20px);
}
</style>