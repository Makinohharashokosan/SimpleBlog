<template>
  <div class="settings-page">
    <header class="page-header">
      <h1 class="page-title">{{ $t('admin.settings.title') }}</h1>
      <p class="page-subtitle">Personalize your digital sanctuary.</p>
    </header>

    <div v-if="!isLoading" class="settings-form">
      <!-- 1. General Info -->
      <section class="settings-section" :style="{ '--delay': '0ms' }">
        <div class="section-header">
          <h2 class="section-title">{{ $t('admin.settings.sections.general') }}</h2>
          <div class="section-line"></div>
        </div>
        
        <div class="form-grid">
          <div class="form-group span-full">
            <label class="form-label">Blog Title</label>
            <BaseInput v-model="form.blogTitle" class="glass-input" />
          </div>
          
          <div class="form-group span-full">
            <label class="form-label">Subtitle</label>
            <BaseTextarea v-model="form.blogSubtitle" class="glass-input" rows="2" />
          </div>

          <div class="form-group">
            <label class="form-label">Logo URL</label>
            <BaseInput v-model="form.logoUrl" class="glass-input" />
          </div>
          
          <div class="form-group">
            <label class="form-label">Favicon URL</label>
            <BaseInput v-model="form.faviconUrl" class="glass-input" />
          </div>
        </div>
      </section>

      <!-- 2. Author Profile -->
      <section class="settings-section" :style="{ '--delay': '100ms' }">
        <div class="section-header">
          <h2 class="section-title">{{ $t('admin.settings.sections.author') }}</h2>
          <div class="section-line"></div>
        </div>
        
        <div class="form-grid">
          <div class="form-group">
            <label class="form-label">Name</label>
            <BaseInput v-model="form.authorName" class="glass-input" />
          </div>
          
          <div class="form-group">
            <label class="form-label">Avatar URL</label>
            <BaseInput v-model="form.authorAvatarUrl" class="glass-input" />
          </div>
          
          <div class="form-group span-full">
            <label class="form-label">Bio</label>
            <BaseTextarea v-model="form.authorBio" class="glass-input" rows="3" />
          </div>
        </div>
      </section>

      <!-- 3. Social Links -->
      <section class="settings-section" :style="{ '--delay': '200ms' }">
        <div class="section-header">
          <h2 class="section-title">{{ $t('admin.settings.sections.social') }}</h2>
          <div class="section-line"></div>
        </div>
        
        <div class="social-list">
          <transition-group name="list">
            <div v-for="(link, index) in form.socialLinks" :key="index" class="social-row">
              <BaseInput v-model="link.platform" placeholder="Platform (e.g. GitHub)" class="glass-input small" />
              <BaseInput v-model="link.url" placeholder="https://..." class="glass-input" />
              <button @click="removeSocialLink(index)" class="icon-btn remove">×</button>
            </div>
          </transition-group>
        </div>
        
        <button @click="addSocialLink" class="add-btn">
          <span class="plus">+</span> Add Social Link
        </button>
      </section>
      
    </div>
    <div v-else class="loading-state">Loading settings...</div>

    <!-- Sticky Action Bar -->
    <transition name="slide-up">
      <div class="sticky-action-bar" v-if="!isLoading">
        <div class="status-indicator" :class="{ modified: isModified }">
          <div class="dot"></div>
          <span>{{ saveStatusText }}</span>
        </div>
        <button @click="saveSettings" :disabled="!isModified || isSaving" class="save-btn">
          {{ isSaving ? 'Saving...' : $t('admin.settings.save') }}
        </button>
      </div>
    </transition>
  </div>
</template>

<script setup>
import { ref, onMounted, computed, watch } from 'vue';
import { useSettingsStore } from '@/stores/settings';
import { updateAllSettings } from '@/api/settings';
import BaseInput from '@/components/base/BaseInput.vue';
import BaseTextarea from '@/components/base/BaseTextarea.vue';
import { useI18n } from 'vue-i18n';

const { t } = useI18n();
const settingsStore = useSettingsStore();
const isLoading = ref(true);
const isSaving = ref(false);
const form = ref({ socialLinks: [] });
const originalFormState = ref('');
const saveStatusText = ref(t('admin.settings.success'));

onMounted(async () => {
  await settingsStore.fetchSettings();
  form.value = JSON.parse(JSON.stringify(settingsStore.config));
  originalFormState.value = JSON.stringify(form.value);
  isLoading.value = false;
});

const isModified = computed(() => {
  return JSON.stringify(form.value) !== originalFormState.value;
});

watch(isModified, (modified) => {
  if (modified) {
    saveStatusText.value = 'Unsaved changes';
  } else {
    saveStatusText.value = t('admin.settings.success');
  }
});

const addSocialLink = () => {
  form.value.socialLinks.push({ platform: '', url: '' });
};

const removeSocialLink = (index) => {
  form.value.socialLinks.splice(index, 1);
};

const saveSettings = async () => {
  isSaving.value = true;
  saveStatusText.value = 'Saving...';
  try {
    const updatedSettings = await updateAllSettings(form.value);
    settingsStore.config = updatedSettings;
    form.value = JSON.parse(JSON.stringify(updatedSettings));
    originalFormState.value = JSON.stringify(form.value);
    saveStatusText.value = t('admin.settings.success');
  } catch (error) {
    console.error("Failed to save settings:", error);
    saveStatusText.value = 'Failed to save.';
  } finally {
    isSaving.value = false;
  }
};
</script>

<style scoped>
.settings-page {
  max-width: 900px;
  margin: 0 auto;
  padding-bottom: 120px;
  animation: fadeIn 0.8s ease-out;
}

.page-header {
  margin-bottom: 64px;
  text-align: center;
}

.page-title {
  font-family: var(--font-family-display);
  font-size: 48px;
  color: var(--color-primary-text);
  margin-bottom: 8px;
}

.page-subtitle {
  font-family: var(--font-family-artistic);
  font-size: 24px;
  color: var(--color-secondary-structure);
  opacity: 0.8;
}

/* Sections */
.settings-section {
  background: rgba(255, 255, 255, 0.05); /* Glass base */
  border: 1px solid rgba(136, 136, 136, 0.2);
  border-radius: 2px;
  padding: 40px;
  margin-bottom: 40px;
  opacity: 0;
  animation: slideUpFade 0.6s ease-out forwards;
  animation-delay: var(--delay);
  transition: transform 0.3s ease, border-color 0.3s ease;
}

.settings-section:hover {
  transform: translateY(-2px);
  border-color: var(--color-functional-element);
}

.section-header {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 32px;
}

.section-title {
  font-family: var(--font-family-sans-serif);
  font-size: 18px;
  text-transform: uppercase;
  letter-spacing: 2px;
  color: var(--color-primary-text);
  flex-shrink: 0;
}

.section-line {
  height: 1px;
  background: var(--color-secondary-structure);
  flex-grow: 1;
  opacity: 0.3;
}

/* Form Styles */
.form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 32px;
}

.form-group.span-full {
  grid-column: 1 / -1;
}

.form-label {
  display: block;
  font-family: var(--font-family-sans-serif);
  font-size: 12px;
  font-weight: 600;
  text-transform: uppercase;
  color: var(--color-secondary-structure);
  margin-bottom: 8px;
}

/* Social List */
.social-row {
  display: flex;
  gap: 16px;
  margin-bottom: 16px;
  align-items: center;
}

.icon-btn.remove {
  background: none;
  border: 1px solid rgba(229, 115, 115, 0.5);
  color: #e57373;
  width: 32px;
  height: 32px;
  border-radius: 50%;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  transition: all 0.2s ease;
}

.icon-btn.remove:hover {
  background: #e57373;
  color: white;
}

.add-btn {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  background: transparent;
  border: 1px dashed var(--color-secondary-structure);
  color: var(--color-secondary-structure);
  padding: 12px 24px;
  font-family: var(--font-family-sans-serif);
  text-transform: uppercase;
  font-size: 12px;
  letter-spacing: 1px;
  cursor: pointer;
  transition: all 0.3s ease;
  margin-top: 16px;
}

.add-btn:hover {
  border-color: var(--color-functional-element);
  color: var(--color-functional-element);
  background: rgba(0,0,0,0.02);
}

/* Sticky Bar */
.sticky-action-bar {
  position: fixed;
  bottom: 24px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  align-items: center;
  gap: 24px;
  padding: 16px 32px;
  background: rgba(20, 20, 20, 0.8); /* Dark glass */
  backdrop-filter: blur(12px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 50px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.2);
  z-index: 100;
}

html video .sticky-action-bar { /* Fallback or specific override if needed */ }
html:not(.dark) .sticky-action-bar {
    background: rgba(255, 255, 255, 0.8);
    border-color: rgba(0, 0, 0, 0.05);
}

.status-indicator {
  display: flex;
  align-items: center;
  gap: 8px;
  font-family: var(--font-family-mono, monospace);
  font-size: 12px;
  color: var(--color-secondary-structure);
}

.dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: var(--color-secondary-structure);
  transition: background 0.3s ease;
}

.status-indicator.modified .dot {
  background: #ffb74d; /* Orange for modified */
}

.save-btn {
  background: var(--color-functional-element);
  color: var(--color-main-bg);
  border: none;
  padding: 12px 32px;
  border-radius: 24px;
  font-family: var(--font-family-sans-serif);
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 1px;
  cursor: pointer;
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.save-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
  background: var(--color-secondary-structure);
}

.save-btn:not(:disabled):hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}

/* Animations */
@keyframes fadeIn { from { opacity: 0; } to { opacity: 1; } }
@keyframes slideUpFade {
  from { opacity: 0; transform: translateY(20px); }
  to { opacity: 1; transform: translateY(0); }
}

.list-enter-active, .list-leave-active { transition: all 0.3s ease; }
.list-enter-from, .list-leave-to { opacity: 0; transform: translateY(-10px); }

.slide-up-enter-active, .slide-up-leave-active { transition: all 0.4s cubic-bezier(0.25, 1, 0.5, 1); }
.slide-up-enter-from, .slide-up-leave-to { transform: translate(-50%, 100%); opacity: 0; }

/* Responsive */
@media (max-width: 768px) {
  .form-grid { grid-template-columns: 1fr; }
  .sticky-action-bar { width: 90%; bottom: 16px; }
}
</style>