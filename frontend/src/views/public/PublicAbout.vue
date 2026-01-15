<template>
  <div class="about-page">
    <header class="page-header">
      <h1 class="page-title">
        {{ $t('titles.about_author') }}
      </h1>
    </header>
    
    <main
      v-if="isLoaded"
      class="profile-card"
    >
      <div class="avatar-wrapper">
        <img
          :src="settings.authorAvatarUrl"
          :alt="settings.authorName"
          class="avatar"
          referrerpolicy="no-referrer"
        >
      </div>
      <div class="profile-info">
        <h2 class="author-name">
          {{ settings.authorName }}
        </h2>
        <p
          v-if="settings.authorBio"
          class="author-bio"
        >
          {{ settings.authorBio }}
        </p>
        
        <div
          v-if="settings.socialLinks && settings.socialLinks.length"
          class="social-links"
        >
          <a
            v-for="link in settings.socialLinks"
            :key="link.platform"
            :href="link.url"
            target="_blank"
            rel="noopener noreferrer"
            class="social-link"
            :title="link.platform"
          >
            <!-- 
              For a real project, you would use an icon library (like FontAwesome or custom SVGs)
              to display icons based on link.platform.
              For now, we'll just display the platform name as text.
            -->
            {{ link.platform }}
          </a>
        </div>
      </div>
    </main>
    
    <div
      v-else
      class="state-info"
    >
      {{ $t('common.loading') }}
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted } from 'vue';
import { useSettingsStore } from '@/stores/settings';

const settingsStore = useSettingsStore();
const settings = computed(() => settingsStore.config);
const isLoaded = computed(() => settingsStore.isLoaded);

onMounted(() => {
  // fetchSettings will only run the API call if the data isn't already in the store
  settingsStore.fetchSettings();
});
</script>

<style scoped>
.about-page {
  max-width: var(--layout-max-width-content);
  margin: 0 auto;
  padding: 48px 20px;
}

.page-header { margin-bottom: 48px; }
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

.profile-card {
  display: flex;
  align-items: flex-start;
  gap: 32px;
}

.avatar-wrapper {
  flex-shrink: 0;
}

.avatar {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  object-fit: cover;
  border: 2px solid var(--color-secondary-structure);
}

.profile-info {
  flex-grow: 1;
}

.author-name {
  font-family: var(--font-family-sans-serif);
  font-size: 28px;
  font-weight: bold;
  color: var(--color-primary-text);
}

.author-bio {
  font-family: var(--font-family-serif);
  font-size: 16px;
  line-height: 1.8;
  color: var(--color-primary-text);
  margin-top: 12px;
}

.social-links {
  display: flex;
  flex-wrap: wrap;
  gap: 24px;
  margin-top: 24px;
}

.social-link {
  font-family: var(--font-family-sans-serif);
  font-size: 14px;
  color: var(--color-secondary-structure);
  text-decoration: none;
  transition: color var(--animation-duration-fast) ease-out;
}

.social-link:hover {
  color: var(--color-functional-element);
}
</style>