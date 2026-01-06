<template>
  <div class="tags-page">
    <header class="page-header">
      <h1 class="page-title">{{ $t('titles.all_tags') }}</h1>
      <div class="header-line"></div>
    </header>

    <div v-if="isLoading" class="loading-state">
      <div class="spinner"></div>
    </div>
    
    <div v-else-if="tags.length === 0" class="empty-state">
      {{ $t('common.no_tags') }}
    </div>

    <main v-else class="tags-container">
      <transition-group name="pop" tag="div" class="tags-cloud" appear>
        <router-link
          v-for="(tag, index) in tags"
          :key="tag.id"
          :to="`/tags/${tag.name}`"
          class="tag-item"
          :style="{ '--delay': `${index * 30}ms` }"
        >
          <span class="hash">#</span>
          <span class="name">{{ tag.name }}</span>
        </router-link>
      </transition-group>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getAllTags } from '@/api/tags';

const tags = ref([]);
const isLoading = ref(true);

onMounted(async () => {
  try {
    const response = await getAllTags();
    tags.value = response.sort((a, b) => a.name.localeCompare(b.name));
  } catch (error) {
    console.error("Failed to fetch tags:", error);
  } finally {
    isLoading.value = false;
  }
});
</script>

<style scoped>
.tags-page {
  max-width: 1000px;
  margin: 0 auto;
  padding: 80px 20px;
}

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

.tags-cloud {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 24px;
}

.tag-item {
  display: inline-flex;
  align-items: center;
  padding: 12px 24px;
  text-decoration: none;
  background: rgba(255, 255, 255, 0.05); /* Glass-ish */
  border: 1px solid var(--color-secondary-structure);
  border-radius: 50px;
  transition: all 0.3s cubic-bezier(0.175, 0.885, 0.32, 1.27);
  box-shadow: 0 4px 6px rgba(0,0,0,0.05);
  
  /* Animation */
  opacity: 0;
  transform: scale(0.8);
  animation: popIn 0.5s cubic-bezier(0.175, 0.885, 0.32, 1.27) forwards;
  animation-delay: var(--delay);
}

.tag-item:hover {
  transform: translateY(-5px) scale(1.05);
  border-color: var(--color-functional-element);
  box-shadow: 0 10px 20px rgba(0,0,0,0.1);
}

.hash {
  color: var(--color-functional-element);
  margin-right: 8px;
  font-weight: bold;
}

.name {
  font-family: var(--font-family-sans-serif);
  font-size: 16px;
  color: var(--color-primary-text);
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
@keyframes popIn {
  to { opacity: 1; transform: scale(1); }
}
</style>