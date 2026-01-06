<template>
  <div class="public-layout">
    <!-- Sticky Glass Header -->
    <header class="public-header" :class="{ scrolled: isScrolled }">
      <nav class="main-nav">
        <router-link to="/" class="nav-link" active-class="active">
          <span class="nav-text">{{ $t('nav.home') }}</span>
          <span class="nav-indicator"></span>
        </router-link>
        <router-link to="/featured" class="nav-link" active-class="active">
          <span class="nav-text">{{ $t('nav.featured') }}</span>
          <span class="nav-indicator"></span>
        </router-link>
        <router-link to="/archive" class="nav-link" active-class="active">
          <span class="nav-text">{{ $t('nav.archive') }}</span>
          <span class="nav-indicator"></span>
        </router-link>
        <router-link to="/tags" class="nav-link" active-class="active">
          <span class="nav-text">{{ $t('nav.tags') }}</span>
          <span class="nav-indicator"></span>
        </router-link>
        <router-link to="/about" class="nav-link" active-class="active">
          <span class="nav-text">{{ $t('nav.about') }}</span>
          <span class="nav-indicator"></span>
        </router-link>
        <router-link to="/search" class="nav-link" active-class="active">
          <span class="nav-text">{{ $t('nav.search') }}</span>
          <span class="nav-indicator"></span>
        </router-link>
      </nav>
      
      <div class="header-actions">
        <!-- New fancy switchers should be used here if compatible, assuming they are -->
        <LanguageSwitcher />
        <span class="divider"></span>
        <ThemeSwitcher storage-key="public-theme" />
      </div>
    </header>

    <main class="content">
      <router-view v-slot="{ Component }">
        <transition name="fade-slide" mode="out-in">
          <component :is="Component" />
        </transition>
      </router-view>
    </main>

    <footer class="public-footer">
      <div class="footer-line"></div>
      <p class="copyright">{{ copyrightText }} · {{ $t('footer.rights') }}</p>
    </footer>
  </div>
</template>

<script setup>
import { computed, ref, onMounted, onUnmounted } from 'vue';
import { useSettingsStore } from '@/stores/settings';
import ThemeSwitcher from '@/components/common/ThemeSwitcher.vue';
import LanguageSwitcher from '@/components/common/LanguageSwitcher.vue';
import { useTheme } from '@/composables/useTheme';

const settingsStore = useSettingsStore();
const copyrightText = computed(() => settingsStore.config.copyrightText);
const { initTheme } = useTheme('public-theme');

const isScrolled = ref(false);

const handleScroll = () => {
  isScrolled.value = window.scrollY > 20;
};

onMounted(() => {
  initTheme();
  window.addEventListener('scroll', handleScroll);
});

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll);
});
</script>

<style scoped>
.public-layout {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  background-color: var(--color-main-bg);
  transition: background-color 0.5s ease;
}

/* Header */
.public-header {
  position: sticky;
  top: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 32px 64px;
  z-index: 100;
  transition: all 0.4s cubic-bezier(0.25, 0.8, 0.25, 1);
  background: transparent;
}

.public-header.scrolled {
  padding: 16px 64px;
  background-color: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(16px);
  -webkit-backdrop-filter: blur(16px);
  border-bottom: 1px solid rgba(0,0,0,0.05);
}

html.dark .public-header.scrolled {
  background-color: rgba(0, 0, 0, 0.8);
  border-bottom-color: rgba(255,255,255,0.05);
}

/* Nav */
.main-nav {
  display: flex;
  gap: 40px;
}

.nav-link {
  text-decoration: none;
  font-family: var(--font-family-display); /* Cinzel */
  font-size: 14px;
  letter-spacing: 2px;
  text-transform: uppercase;
  color: var(--color-secondary-structure);
  position: relative;
  transition: color 0.3s ease;
  padding: 8px 0;
}

.nav-text {
  position: relative;
  z-index: 2;
}

.nav-indicator {
  position: absolute;
  bottom: 0;
  left: 50%;
  width: 0;
  height: 2px;
  background-color: var(--color-functional-element);
  transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
  transform: translateX(-50%);
  opacity: 0;
}

.nav-link:hover .nav-text,
.nav-link.active .nav-text {
  color: var(--color-functional-element);
}

.nav-link:hover .nav-indicator,
.nav-link.active .nav-indicator {
  width: 100%;
  opacity: 1;
}

/* Actions */
.header-actions {
  display: flex;
  align-items: center;
  gap: 24px;
}

.divider {
  width: 1px;
  height: 24px;
  background-color: var(--color-secondary-structure);
  opacity: 0.3;
}

/* Content */
.content {
  flex-grow: 1;
}

/* Footer */
.public-footer {
  padding: 64px 48px 48px;
  text-align: center;
  position: relative;
}

.footer-line {
  width: 60px;
  height: 2px;
  background-color: var(--color-functional-element);
  margin: 0 auto 32px;
  opacity: 0.5;
}

.copyright {
  font-family: var(--font-family-serif);
  font-size: 14px;
  letter-spacing: 1px;
  color: var(--color-secondary-structure);
  opacity: 0.8;
}

/* Transitions */
.fade-slide-enter-active,
.fade-slide-leave-active {
  transition: opacity 0.5s ease, transform 0.5s ease;
}

.fade-slide-enter-from {
  opacity: 0;
  transform: translateY(20px);
}

.fade-slide-leave-to {
  opacity: 0;
  transform: translateY(-20px);
}

@media (max-width: 768px) {
  .public-header { padding: 20px; }
  .main-nav { gap: 16px; font-size: 12px; }
}
</style>