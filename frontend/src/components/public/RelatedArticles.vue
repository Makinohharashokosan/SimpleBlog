<template>
  <div
    v-if="relatedArticles.length > 0"
    class="related-articles"
  >
    <h3 class="related-title">
      {{ $t('common.related_stories') }}
    </h3>
    <div class="related-list">
      <router-link
        v-for="article in relatedArticles"
        :key="article.id"
        :to="`/article/${article.id}`"
        class="related-card"
      >
        <div class="card-content">
          <h4 class="card-title">
            {{ article.title }}
          </h4>
          <div class="card-meta">
            <span
              v-if="article.viewCount"
              class="views"
            >{{ $t('common.views', { count: article.viewCount }) }}</span>
            <span
              v-if="article.tags && article.tags.length"
              class="tags"
            >
              · {{ article.tags[0].name }}
            </span>
          </div>
        </div>
      </router-link>
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue';
import { getAllArticles } from '@/api/articles';
import { getRelatedArticles } from '@/utils/recommendation';

const props = defineProps({
  currentArticle: {
    type: Object,
    required: true
  }
});

const relatedArticles = ref([]);
const isLoading = ref(false);

const fetchAndCalculateRelated = async () => {
  if (!props.currentArticle) return;
  
  isLoading.value = true;
  try {
    // In a real app with many articles, we might want a specific API endpoint.
    // Here we fetch a batch of recent articles to find related ones.
    const response = await getAllArticles({ page: 0, size: 20 });
    const candidates = response.data?.content || response.content || []; 
    
    relatedArticles.value = getRelatedArticles(props.currentArticle, candidates, 4);
  } catch (error) {
    console.error("Failed to load related articles:", error);
  } finally {
    isLoading.value = false;
  }
};

watch(() => props.currentArticle, () => {
  fetchAndCalculateRelated();
}, { immediate: true });

</script>

<style scoped>
.related-articles {
  margin-top: 8px;
  padding: 24px;
  background-color: var(--color-main-bg);
  border-left: 1px solid var(--color-secondary-structure);
}

.related-title {
  font-family: var(--font-family-display, 'Cinzel');
  font-size: 18px;
  margin-bottom: 24px;
  color: var(--color-primary-text);
  text-transform: uppercase;
  letter-spacing: 1px;
}

.related-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.related-card {
  text-decoration: none;
  display: block;
  transition: transform 0.2s ease;
}

.related-card:hover {
  transform: translateX(4px);
}

.card-title {
  font-family: var(--font-family-sans-serif);
  font-size: 14px;
  font-weight: 600;
  line-height: 1.4;
  color: var(--color-primary-text);
  margin: 0 0 8px 0;
}

.related-card:hover .card-title {
  color: var(--color-functional-element);
}

.card-meta {
  font-family: var(--font-family-serif);
  font-size: 12px;
  color: var(--color-secondary-structure);
}
</style>
