<template>
  <div class="article-list-page">
    <!-- Header -->
    <header class="page-header">
      <h1 class="page-title">{{ $t('admin.articles.title') }}</h1>
      <router-link to="/admin/articles/new" class="create-btn">
        <span class="icon">✎</span>
        {{ $t('admin.articles.create') }}
      </router-link>
    </header>

    <!-- Search / Filter Bar (Placeholder for future) -->
    <!-- <div class="filter-bar">...</div> -->

    <!-- Loading State -->
    <div v-if="isLoading" class="loading-state">
      <div class="spinner"></div>
      <span>{{ $t('admin.login.loading') }}</span>
    </div>

    <!-- Empty State -->
    <div v-else-if="articles.length === 0" class="empty-state">
      <p class="empty-text">{{ $t('common.no_archive') }}</p>
      <router-link to="/admin/articles/new" class="create-btn large">
        {{ $t('admin.articles.create') }}
      </router-link>
    </div>
    
    <!-- Articles List (Styled Table) -->
    <div v-else class="list-container">
      <transition-group name="list" tag="div" class="article-rows">
        <div class="list-header" key="header">
          <span class="col-title">{{ $t('admin.articles.table.title') }}</span>
          <span class="col-date">{{ $t('admin.articles.table.created') }}</span>
          <span class="col-views">{{ $t('admin.articles.table.views') }}</span>
          <span class="col-status">{{ $t('admin.articles.status.featured') }}</span>
          <span class="col-actions">{{ $t('admin.articles.table.actions') }}</span>
        </div>

        <div 
          v-for="(article, index) in articles" 
          :key="article.id" 
          class="article-row"
          :style="{ '--delay': `${index * 50}ms` }"
        >
          <div class="col-title">
            <span class="article-title">{{ article.title }}</span>
            <div class="article-tags">
              <span v-for="tag in article.tags" :key="tag.id" class="tag-badge">#{{ tag.name }}</span>
            </div>
          </div>
          
          <div class="col-date">{{ formatDate(article.createdAt) }}</div>
          
          <div class="col-views">{{ article.viewCount }}</div>
          
          <div class="col-status">
            <BaseToggleSwitch
              :model-value="article.featured"
              @update:model-value="toggleFeatured(article, $event)"
              class="featured-switch"
            />
          </div>
          
          <div class="col-actions">
            <router-link :to="`/admin/articles/edit/${article.id}`" class="action-btn edit" :title="$t('admin.articles.edit')">
              ✎
            </router-link>
            <button @click="promptDelete(article)" class="action-btn delete" :title="$t('admin.articles.delete')">
              ✖
            </button>
          </div>
        </div>
      </transition-group>
    </div>

    <!-- Pagination -->
    <BasePagination
      v-if="!isLoading && articles.length > 0"
      :current-page="pageNumber"
      :is-first="isFirstPage"
      :is-last="isLastPage"
      @page-change="fetchArticles"
      class="pagination-wrapper"
    />

    <!-- Delete Confirmation Modal -->
    <BaseModal
      :show="isModalVisible"
      :title="$t('admin.articles.delete')"
      @close="isModalVisible = false"
      @confirm="confirmDelete"
    >
      <p class="modal-text">
        {{ $t('admin.articles.confirm_delete') }}
        <br>
        <strong class="highlight-text">「{{ articleToDelete?.title }}」</strong>
      </p>
    </BaseModal>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getAllArticles, setArticleFeatured, deleteArticleById } from '@/api/articles';
import BaseToggleSwitch from '@/components/base/BaseToggleSwitch.vue';
import BaseModal from '@/components/base/BaseModal.vue';
import BasePagination from '@/components/common/BasePagination.vue';
import { useI18n } from 'vue-i18n';

const { locale } = useI18n();
const isLoading = ref(true);
const articles = ref([]);
const pageNumber = ref(0);
const isFirstPage = ref(true);
const isLastPage = ref(false);

const isModalVisible = ref(false);
const articleToDelete = ref(null);

const fetchArticles = async (page = 0) => {
  isLoading.value = true;
  try {
    const response = await getAllArticles({ page, size: 8, sort: 'createdAt,desc' }); // Reduced size for better visuals
    articles.value = response.content;
    pageNumber.value = response.number;
    isFirstPage.value = response.first;
    isLastPage.value = response.last;
  } catch (error) {
    console.error("Failed to fetch articles:", error);
  } finally {
    isLoading.value = false;
  }
};

onMounted(() => {
  fetchArticles();
});

const formatDate = (dateString) => {
  const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
  return new Date(dateString).toLocaleDateString(locale.value === 'zh' ? 'zh-CN' : 'en-US', options);
};

const toggleFeatured = async (article, newFeaturedState) => {
  const originalState = article.featured;
  try {
    article.featured = newFeaturedState;
    await setArticleFeatured(article.id, newFeaturedState);
  } catch (error) {
    article.featured = originalState;
    console.error("Failed to update featured status:", error);
  }
};

const promptDelete = (article) => {
  articleToDelete.value = article;
  isModalVisible.value = true;
};

const confirmDelete = async () => {
  if (!articleToDelete.value) return;
  try {
    await deleteArticleById(articleToDelete.value.id);
    isModalVisible.value = false;
    fetchArticles(pageNumber.value);
  } catch (error) {
    console.error("Failed to delete article:", error);
  }
};
</script>

<style scoped>
.article-list-page {
  max-width: var(--layout-max-width-dashboard);
  margin: 0 auto;
  padding: 20px;
  animation: fadeIn 0.8s ease-out;
}

/* Header */
.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 48px;
}

.page-title {
  font-family: var(--font-family-display);
  font-size: 32px;
  color: var(--color-primary-text);
  letter-spacing: 1px;
}

.create-btn {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 12px 24px;
  background-color: var(--color-functional-element);
  color: var(--color-main-bg);
  text-decoration: none;
  font-family: var(--font-family-sans-serif);
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 1px;
  transition: all 0.3s ease;
  border: 1px solid var(--color-functional-element);
}

.create-btn:hover {
  background-color: transparent;
  color: var(--color-functional-element);
}

/* List / Grid Layout mimicking a Table */
.list-container {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.list-header {
  display: grid;
  grid-template-columns: 3fr 1.5fr 1fr 1fr 1fr;
  padding: 0 24px 16px;
  border-bottom: 1px solid var(--color-secondary-structure);
  font-family: var(--font-family-sans-serif);
  font-size: 12px;
  text-transform: uppercase;
  letter-spacing: 2px;
  color: var(--color-secondary-structure);
}

.article-row {
  display: grid;
  grid-template-columns: 3fr 1.5fr 1fr 1fr 1fr;
  align-items: center;
  padding: 24px;
  background-color: var(--color-main-bg);
  border: 1px solid var(--color-card-shadow);
  transition: all 0.3s ease;
  
  /* Animation */
  opacity: 0;
  animation: slideUpFade 0.5s ease-out forwards;
  animation-delay: var(--delay);
}

.article-row:hover {
  transform: translateX(8px);
  border-color: var(--color-functional-element);
  box-shadow: -4px 4px 0 var(--color-card-shadow);
}

/* Columns */
.col-title {
  display: flex;
  flex-direction: column;
  gap: 8px;
  padding-right: 16px;
}

.article-title {
  font-family: var(--font-family-sans-serif);
  font-size: 18px;
  font-weight: 600;
  color: var(--color-primary-text);
}

.article-tags {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.tag-badge {
  font-size: 12px;
  color: var(--color-secondary-structure);
  font-family: 'Fira Code', monospace;
}

.col-date, .col-views {
  font-family: var(--font-family-serif);
  color: var(--color-secondary-structure);
  font-size: 15px;
}

.col-status {
  display: flex;
  align-items: center;
}

/* Actions */
.col-actions {
  display: flex;
  gap: 16px;
  justify-content: flex-start;
}

.action-btn {
  background: none;
  border: none;
  font-size: 18px;
  cursor: pointer;
  transition: transform 0.2s ease, color 0.2s ease;
  color: var(--color-secondary-structure);
}

.action-btn:hover {
  transform: scale(1.2);
}

.action-btn.edit:hover { color: var(--color-functional-element); }
.action-btn.delete:hover { color: #e57373; }

/* Empty State */
.empty-state {
  text-align: center;
  padding: 80px 0;
}
.empty-text {
  font-family: var(--font-family-artistic);
  font-size: 32px;
  color: var(--color-secondary-structure);
  margin-bottom: 24px;
}

/* Loading */
.loading-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
  padding: 80px 0;
  color: var(--color-secondary-structure);
}

.spinner {
  width: 40px;
  height: 40px;
  border: 3px solid var(--color-card-shadow);
  border-top-color: var(--color-functional-element);
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

/* Modal Content */
.modal-text {
  font-size: 16px;
  line-height: 1.6;
  text-align: center;
}
.highlight-text {
  color: var(--color-functional-element);
}

/* Animations */
@keyframes spin { to { transform: rotate(360deg); } }
@keyframes fadeIn { from { opacity: 0; } to { opacity: 1; } }
@keyframes slideUpFade {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

.list-enter-active, .list-leave-active { transition: all 0.5s ease; }
.list-enter-from, .list-leave-to { opacity: 0; transform: translateX(-20px); }
</style>
