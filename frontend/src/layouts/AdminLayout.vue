<template>
  <div class="admin-layout">
    <!-- 1. Stylized Sidebar -->
    <aside class="sidebar">
      <div class="sidebar-header">
        <h2 class="sidebar-title">
          Admin Console
        </h2>
      </div>

      <nav class="admin-nav">
        <router-link
          to="/admin/dashboard"
          class="nav-item"
        >
          <span class="nav-icon">⊞</span>
          <span class="nav-text">{{ $t('admin.nav.dashboard') }}</span>
        </router-link>
        <router-link
          to="/admin/articles"
          class="nav-item"
        >
          <span class="nav-icon">✎</span>
          <span class="nav-text">{{ $t('admin.nav.articles') }}</span>
        </router-link>
        <router-link
          to="/admin/tags"
          class="nav-item"
        >
          <span class="nav-icon">🏷</span>
          <span class="nav-text">{{ $t('admin.nav.tags') }}</span>
        </router-link>
        <router-link
          to="/admin/settings"
          class="nav-item"
        >
          <span class="nav-icon">⚙</span>
          <span class="nav-text">{{ $t('admin.nav.settings') }}</span>
        </router-link>
      </nav>

      <div class="sidebar-footer">
        <div class="controls-wrapper">
          <ThemeSwitcher storage-key="admin-theme" />
          <LanguageSwitcher />
        </div>
        <button
          class="logout-button"
          @click="handleLogout"
        >
          <span class="nav-icon">⎋</span>
          {{ $t('admin.nav.logout') }}
        </button>
      </div>
    </aside>

    <!-- 2. Main Content Area -->
    <main class="main-content">
      <router-view v-slot="{ Component }">
        <transition
          name="fade-slide"
          mode="out-in"
        >
          <component :is="Component" />
        </transition>
      </router-view>
    </main>
  </div>
</template>

<script setup>
import { useAuthStore } from '@/stores/auth';
import { useRouter } from 'vue-router';
import { onMounted } from 'vue';
import ThemeSwitcher from '@/components/common/ThemeSwitcher.vue';
import LanguageSwitcher from '@/components/common/LanguageSwitcher.vue';
import { useTheme } from '@/composables/useTheme';

const authStore = useAuthStore();
const router = useRouter();
const { initTheme } = useTheme('admin-theme');

onMounted(() => {
  initTheme();
});

const handleLogout = () => {
  authStore.logout();
  router.push({ name: 'AdminLogin' });
};
</script>

<style scoped>
/* Admin App Shell Layout */
.admin-layout {
  display: flex;
  height: 100vh; /* Fixed height */
  overflow: hidden; /* No body scroll */
  background-color: var(--color-main-bg);
  color: var(--color-primary-text);
  font-family: var(--font-family-sans-serif);
}

/* 1. Sidebar Styles */
.sidebar {
  width: 260px;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  height: 100%;
  background: rgba(255, 255, 255, 0.05); 
  backdrop-filter: blur(10px);
  -webkit-backdrop-filter: blur(10px);
  border-right: 1px solid var(--color-secondary-structure);
  position: relative;
  z-index: 10;
  transition: width 0.3s ease;
}

html.dark .sidebar {
  background: rgba(0, 0, 0, 0.2);
}

.sidebar-header {
  padding: 40px 32px;
  flex-shrink: 0;
}

.sidebar-title {
  font-family: var(--font-family-display);
  font-size: 20px;
  letter-spacing: 2px;
  text-transform: uppercase;
  color: var(--color-primary-text);
  opacity: 0.9;
}

/* Nav Items */
.admin-nav {
  flex-grow: 1; /* Takes available space */
  overflow-y: auto; /* Scrollable nav if needed */
  display: flex;
  flex-direction: column;
  gap: 8px;
  padding: 0 16px;
}

/* Hide scrollbar for nav */
.admin-nav::-webkit-scrollbar {
  width: 4px;
}
.admin-nav::-webkit-scrollbar-thumb {
  background-color: rgba(136, 136, 136, 0.2);
}

.nav-item {
  display: flex;
  align-items: center;
  gap: 16px;
  padding: 16px 20px;
  text-decoration: none;
  color: var(--color-secondary-structure);
  font-size: 14px;
  letter-spacing: 1px;
  text-transform: uppercase;
  border-radius: 4px;
  transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
  position: relative;
  overflow: hidden;
  flex-shrink: 0;
}

.nav-icon {
  font-size: 18px;
  transition: transform 0.3s ease;
}

/* Hover Effect */
.nav-item::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: var(--color-functional-element);
  transform: scaleX(0);
  transform-origin: left;
  transition: transform 0.3s ease;
  z-index: -1;
  opacity: 0.05;
}

.nav-item:hover {
  color: var(--color-functional-element);
  padding-left: 28px;
}

.nav-item:hover .nav-icon {
  transform: scale(1.2);
}

.nav-item:hover::before {
  transform: scaleX(1);
}

/* Active State */
.nav-item.router-link-active {
  color: var(--color-functional-element);
  font-weight: bold;
}

.nav-item.router-link-active::after {
  content: '';
  position: absolute;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
  width: 4px;
  height: 60%;
  background-color: var(--color-functional-element);
}

/* Footer & Controls */
.sidebar-footer {
  flex-shrink: 0; /* Always visible at bottom */
  padding: 32px;
  border-top: 1px solid rgba(136, 136, 136, 0.1);
  background: inherit; /* Matches sidebar bg */
}

.controls-wrapper {
  display: flex;
  justify-content: space-between;
  margin-bottom: 24px;
  opacity: 0.8;
  transition: opacity 0.3s;
}

.controls-wrapper:hover {
  opacity: 1;
}

.logout-button {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 10px;
  padding: 12px;
  background: transparent;
  border: 1px solid var(--color-secondary-structure);
  color: var(--color-primary-text);
  font-family: var(--font-family-sans-serif);
  font-size: 12px;
  letter-spacing: 1px;
  text-transform: uppercase;
  cursor: pointer;
  transition: all 0.3s ease;
}

.logout-button:hover {
  background-color: var(--color-functional-element);
  color: var(--color-main-bg);
  border-color: var(--color-functional-element);
}

/* 2. Main Content */
.main-content {
  flex-grow: 1; /* Takes remaining width */
  height: 100%; /* Full height */
  overflow-y: auto; /* Independent scrolling */
  padding: 48px 64px;
  position: relative;
}

/* Page Transition Animation */
.fade-slide-enter-active,
.fade-slide-leave-active {
  transition: opacity 0.4s ease, transform 0.4s ease;
}

.fade-slide-enter-from {
  opacity: 0;
  transform: translateY(20px);
}

.fade-slide-leave-to {
  opacity: 0;
  transform: translateY(-20px);
}
</style>