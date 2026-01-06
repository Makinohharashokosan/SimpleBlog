<template>
  <div class="editor-page">
    <!-- Floating Header -->
    <header class="editor-header" :class="{ scrolled: isScrolled }">
      <div class="header-left">
        <router-link to="/admin/articles" class="back-link">
          <span class="icon">←</span> {{ $t('admin.nav.articles') }}
        </router-link>
      </div>
      
      <div class="header-center" v-if="isScrolled">
        <span class="scrolled-title">{{ article.title || 'Untitled Article' }}</span>
      </div>

      <div class="header-right">
        <span class="save-status" v-if="isSaving">{{ $t('common.loading') }}</span>
        <button @click="handleSave" :disabled="isSaving" class="publish-btn">
          {{ saveButtonText }}
        </button>
      </div>
    </header>

    <!-- Main Editor Area -->
    <main class="editor-container">
      <div v-if="isLoading" class="loading-state">
        <div class="spinner"></div>
      </div>
      
      <div v-else class="editor-content">
        <!-- Title Input -->
        <input
          type="text"
          v-model="article.title"
          :placeholder="$t('admin.articles.table.title')"
          class="title-input"
          ref="titleInput"
        />
        
        <!-- Metadata Bar -->
        <div class="meta-bar">
          <div class="meta-item">
            <span class="meta-icon">#</span>
             <!-- Reusing TagsInput but wrapping it for style -->
            <div class="tags-wrapper">
              <TagsInput v-model="article.tags" placeholder="Add tags..." />
            </div>
          </div>
        </div>

        <!-- Markdown Editor -->
        <div class="content-wrapper">
          <textarea
            v-model="article.content"
            placeholder="Tell your story..."
            class="markdown-area"
            @input="adjustHeight"
            ref="contentTextarea"
          ></textarea>
        </div>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, onUnmounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { getArticleById, createArticle, updateArticle } from '@/api/articles';
import TagsInput from '@/components/common/TagsInput.vue';
import { useI18n } from 'vue-i18n';

const { t } = useI18n();
const route = useRoute();
const router = useRouter();

const isLoading = ref(false);
const isSaving = ref(false);
const isScrolled = ref(false);
const article = ref({
  title: '',
  content: '',
  tags: [],
});
const contentTextarea = ref(null);

const articleId = route.params.id;
const isEditMode = computed(() => !!articleId);

const saveButtonText = computed(() => {
  if (isSaving.value) return t('admin.login.loading');
  return isEditMode.value ? t('admin.articles.edit') : t('admin.articles.create');
});

// Scroll detection for sticky header effect
const handleScroll = () => {
  isScrolled.value = window.scrollY > 50;
};

onMounted(async () => {
  window.addEventListener('scroll', handleScroll);
  
  if (isEditMode.value) {
    isLoading.value = true;
    try {
      const response = await getArticleById(articleId);
      article.value = {
        title: response.title,
        content: response.content,
        // Assuming API returns object array for tags
        tags: response.tags.map(tag => tag.name),
      };
      // Adjust textarea height after loading content
      setTimeout(adjustHeight, 100);
    } catch (error) {
      console.error("Failed to fetch article:", error);
      router.push('/admin/articles');
    } finally {
      isLoading.value = false;
    }
  }
});

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll);
});

const adjustHeight = () => {
  const el = contentTextarea.value;
  if (el) {
    el.style.height = 'auto';
    el.style.height = el.scrollHeight + 'px';
  }
};

const handleSave = async () => {
  isSaving.value = true;
  try {
    const payload = {
      title: article.value.title,
      content: article.value.content,
      tags: article.value.tags,
    };
    if (isEditMode.value) {
      await updateArticle(articleId, payload);
    } else {
      await createArticle(payload);
    }
    router.push('/admin/articles');
  } catch (error) {
    console.error("Failed to save article:", error);
    alert("Failed to save. Please try again.");
  } finally {
    isSaving.value = false;
  }
};
</script>

<style scoped>
.editor-page {
  min-height: 100vh;
  background-color: var(--color-main-bg);
}

/* 1. Editor Header (Sticky) */
.editor-header {
  position: sticky;
  top: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 40px;
  background-color: transparent;
  transition: all 0.3s ease;
  z-index: 100;
}

.editor-header.scrolled {
  background-color: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  border-bottom: 1px solid rgba(0,0,0,0.05);
  padding: 12px 40px;
}

html.dark .editor-header.scrolled {
  background-color: rgba(0, 0, 0, 0.8);
  border-bottom-color: rgba(255,255,255,0.05);
}

.back-link {
  color: var(--color-secondary-structure);
  text-decoration: none;
  font-family: var(--font-family-sans-serif);
  font-size: 14px;
  text-transform: uppercase;
  letter-spacing: 1px;
  transition: color 0.2s;
}

.back-link:hover {
  color: var(--color-functional-element);
}

.scrolled-title {
  font-family: var(--font-family-sans-serif);
  font-weight: bold;
  font-size: 14px;
  opacity: 0;
  animation: fadeIn 0.3s forwards;
}

.publish-btn {
  background-color: var(--color-functional-element);
  color: var(--color-main-bg);
  border: none;
  padding: 10px 24px;
  border-radius: 20px;
  font-family: var(--font-family-sans-serif);
  font-weight: 600;
  font-size: 12px;
  text-transform: uppercase;
  letter-spacing: 1px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.publish-btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}

.publish-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

/* 2. Main Container */
.editor-container {
  max-width: 800px; /* Focus mode width */
  margin: 0 auto 100px;
  padding: 40px 20px;
  animation: fadeIn 0.8s ease-out;
}

/* 3. Typography & Inputs */
.title-input {
  width: 100%;
  border: none;
  background: transparent;
  outline: none;
  font-family: var(--font-family-display); /* Cinzel for title */
  font-size: 48px; 
  color: var(--color-primary-text);
  margin-bottom: 24px;
  line-height: 1.2;
}

.title-input::placeholder {
  color: var(--color-secondary-structure);
  opacity: 0.3;
}

/* Meta Bar (Tags) */
.meta-bar {
  display: flex;
  align-items: center;
  margin-bottom: 40px;
  border-bottom: 1px solid rgba(136,136,136, 0.1);
  padding-bottom: 20px;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 12px;
  flex: 1;
}

.meta-icon {
  font-family: var(--font-family-artistic);
  font-size: 24px;
  color: var(--color-functional-element);
}

.tags-wrapper {
  flex: 1;
}

/* Markdown Area */
.markdown-area {
  width: 100%;
  min-height: 400px;
  border: none;
  background: transparent;
  outline: none;
  resize: none; /* Auto-resizing via JS */
  font-family: 'Merriweather', var(--font-family-serif); /* Serif for long form reading */
  font-size: 18px;
  line-height: 1.8;
  color: var(--color-primary-text);
  overflow: hidden;
}

.markdown-area::placeholder {
  color: var(--color-secondary-structure);
  font-style: italic;
  opacity: 0.5;
}

/* Status Loading */
.loading-state {
  display: flex;
  justify-content: center;
  padding: 40px;
}
.spinner {
  width: 30px;
  height: 30px;
  border: 2px solid var(--color-card-shadow);
  border-top-color: var(--color-functional-element);
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

@keyframes spin { to { transform: rotate(360deg); } }
@keyframes fadeIn { from { opacity: 0; } to { opacity: 1; } }
</style>
