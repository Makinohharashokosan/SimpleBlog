<template>
  <router-link
    :to="`/article/${article.id}`"
    class="card-link"
  >
    <article class="summary-card">
      <div class="card-meta">
        <span class="date">{{ formatDate(article.createdAt) }}</span>
      </div>
      
      <h2 class="title">
        {{ article.title }}
      </h2>
      
      <div
        v-if="article.tags && article.tags.length"
        class="tags-list"
      >
        <span
          v-for="tag in article.tags"
          :key="tag.id"
          class="tag-badge"
        >
          #{{ tag.name }}
        </span>
      </div>
      
      <div class="read-more">
        <span>Read Article</span>
        <span class="arrow">→</span>
      </div>
    </article>
  </router-link>
</template>

<script setup>
import { useI18n } from 'vue-i18n';

const { locale } = useI18n();

defineProps({
  article: {
    type: Object,
    required: true,
  },
});

const formatDate = (dateString) => {
  const options = { year: 'numeric', month: 'long', day: 'numeric' };
  return new Date(dateString).toLocaleDateString(locale.value === 'zh' ? 'zh-CN' : 'en-US', options);
};
</script>

<style scoped>
.card-link {
  text-decoration: none;
  display: block;
}

.summary-card {
  padding: 32px;
  background-color: var(--color-main-bg);
  border: 1px solid var(--color-card-shadow);
  position: relative;
  transition: all 0.4s cubic-bezier(0.25, 0.8, 0.25, 1);
  overflow: hidden;
}

/* Hover Effect: Lift & Border */
.card-link:hover .summary-card {
  transform: translateY(-8px);
  box-shadow: 0 16px 40px var(--color-card-shadow);
  border-color: var(--color-functional-element);
}

.card-meta {
  margin-bottom: 16px;
}

.date {
  font-family: var(--font-family-serif);
  font-size: 13px;
  text-transform: uppercase;
  letter-spacing: 1px;
  color: var(--color-secondary-structure);
}

.title {
  font-family: var(--font-family-sans-serif); /* Montserrat */
  font-size: 28px;
  font-weight: 700;
  line-height: 1.3;
  color: var(--color-primary-text);
  margin-bottom: 20px;
  transition: color 0.3s ease;
}

.tags-list {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-bottom: 32px;
}

.tag-badge {
  font-family: 'Fira Code', monospace;
  font-size: 12px;
  color: var(--color-secondary-structure);
  transition: color 0.3s ease;
}

.card-link:hover .tag-badge {
  color: var(--color-functional-element);
}

/* Read More Indicator */
.read-more {
  font-family: var(--font-family-sans-serif);
  font-size: 12px;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 2px;
  color: var(--color-functional-element);
  display: flex;
  align-items: center;
  gap: 8px;
  opacity: 0;
  transform: translateX(-10px);
  transition: all 0.4s ease;
}

.arrow {
  font-size: 16px;
}

.card-link:hover .read-more {
  opacity: 1;
  transform: translateX(0);
}
</style>