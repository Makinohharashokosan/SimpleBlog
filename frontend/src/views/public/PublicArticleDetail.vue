<template>
  <div class="article-detail-page">
    <div v-if="isLoading" class="loading-state">正在加载文章...</div>
    <div v-else-if="error" class="error-state">无法加载文章，请稍后重试。</div>
    
    <div v-else-if="article" class="article-container">
      <!-- 1. Article Header (outside the iframe) -->
      <header class="article-header">
        <h1 class="article-title">{{ article.title }}</h1>
        <div class="article-metadata">
          <span>{{ settings.authorName }}</span>
          <span class="separator">·</span>
          <span>{{ formatDate(article.createdAt) }}</span>
          <span class="separator">·</span>
          <span>浏览 {{ article.viewCount }} 次</span>
        </div>
      </header>

      <!-- 2. Embedded Browser (iframe) for custom HTML content -->
      <iframe
        :srcdoc="article.content"
        class="article-iframe"
        frameborder="0"
        scrolling="no"
        @load="resizeIframe"
      ></iframe>

      <!-- 3. Article Footer (Tags, outside the iframe) -->
      <footer v-if="article.tags && article.tags.length" class="article-footer">
        <div class="tags-list">
          <router-link
            v-for="tag in article.tags"
            :key="tag.id"
            :to="`/tags/${tag.name}`"
            class="tag-link"
          >
            <span class="hash">#</span>{{ tag.name }}
          </router-link>
        </div>
      </footer>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useRoute } from 'vue-router';
import { getArticleById } from '@/api/articles';
import { useSettingsStore } from '@/stores/settings';

const route = useRoute();
const settingsStore = useSettingsStore();

const article = ref(null);
const isLoading = ref(true);
const error = ref(false);

const settings = computed(() => settingsStore.config);

onMounted(async () => {
  // ... (script logic remains the same)
  const articleId = route.params.id;
  try {
    await settingsStore.fetchSettings();
    const response = await getArticleById(articleId);
    article.value = response;
  } catch (err) {
    console.error("Failed to fetch article details:", err);
    error.value = true;
  } finally {
    isLoading.value = false;
  }
});

const formatDate = (dateString) => {
  const options = { year: 'numeric', month: 'long', day: 'numeric' };
  return new Date(dateString).toLocaleDateString('zh-CN', options);
};

// Function to dynamically resize iframe based on its content height
const resizeIframe = (event) => {
  const iframe = event.target;
  // Use scrollHeight to get the full height of the content
  if (iframe.contentWindow && iframe.contentWindow.document.body) {
    iframe.style.height = iframe.contentWindow.document.body.scrollHeight + 'px';
  }
};
</script>
<style scoped>
.article-detail-page {
  max-width: var(--layout-max-width-content);
  margin: 0 auto;
  padding: 48px 20px;
}
.loading-state, .error-state {
  text-align: center;
  font-family: var(--font-family-serif);
  color: var(--color-secondary-structure);
  font-size: 18px;
  padding: 80px 0;
}

/* Article Header */
.article-header {
  margin-bottom: 48px;
}
.article-title {
  font-family: var(--font-family-sans-serif);
  font-size: 48px;
  font-weight: bold;
  color: var(--color-primary-text);
  line-height: 1.2;
  margin-bottom: 24px;
}
.article-metadata {
  font-family: var(--font-family-serif);
  font-size: 14px;
  color: var(--color-secondary-structure);
}
.article-metadata .separator {
  margin: 0 8px;
}

/* Article Body */
/* We use a deep selector (>>>) to style the content coming from v-html */
.article-body :deep(p) {
  font-family: var(--font-family-serif);
  font-size: 17px;
  line-height: 1.8;
  color: var(--color-primary-text);
  margin-bottom: 24px;
}
.article-body :deep(h2),
.article-body :deep(h3) {
  font-family: var(--font-family-sans-serif);
  font-weight: bold;
  color: var(--color-primary-text);
  margin: 40px 0 20px;
}
.article-body :deep(a) { /* Link Style inside content */
  color: var(--color-primary-text);
  text-decoration: none;
  border-bottom: 1px solid var(--color-functional-element);
}
.article-body :deep(pre) {
  background-color: rgba(136, 136, 136, 0.1);
  padding: 16px;
  border-radius: 4px;
  overflow-x: auto;
  font-family: 'Fira Code', monospace;
}
.article-body :deep(code) {
  font-family: 'Fira Code', monospace;
}

/* Article Footer (Tags) */
.article-footer {
  margin-top: 48px;
  padding-top: 24px;
  border-top: 1px solid var(--color-secondary-structure);
}
.tags-list {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
}
.tag-link {
  color: var(--color-functional-element);
  text-decoration: none;
  font-family: var(--font-family-sans-serif);
  position: relative;
}
.tag-link .hash {
  color: var(--color-secondary-structure);
}
.tag-link::after {
  content: '';
  position: absolute;
  width: 0;
  height: 1px;
  bottom: -2px;
  left: 0;
  background-color: var(--color-functional-element);
  transition: width var(--animation-duration-fast) ease-out;
}
.tag-link:hover::after {
  width: 100%;
}
</style>