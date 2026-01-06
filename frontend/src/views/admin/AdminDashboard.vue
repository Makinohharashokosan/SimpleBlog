<template>
  <div class="dashboard-container">
    <!-- 1. 欢迎横幅 -->
    <header class="welcome-banner" v-if="settings.authorName">
      <h1 class="welcome-title">
        <span class="greeting">Welcome Back,</span>
        <span class="name">{{ settings.authorName }}</span>
      </h1>
      <p class="welcome-subtitle">The world awaits your next masterpiece.</p>
    </header>

    <!-- 2. 核心数据统计 -->
    <section class="stats-grid">
      <div 
        v-for="(stat, index) in statsCards" 
        :key="stat.label" 
        class="stat-card"
        :style="{ '--delay': `${index * 100}ms` }"
      >
        <div class="stat-content">
          <span class="stat-value">{{ stat.value }}</span>
          <span class="stat-label">{{ stat.label }}</span>
        </div>
        <div class="stat-decorative-line"></div>
      </div>
    </section>

    <!-- 3. 快捷操作 -->
    <section class="quick-actions">
      <h2 class="section-title">Quick Actions</h2>
      <div class="actions-wrapper">
        <button class="action-btn" @click="$router.push('/admin/articles/new')">
          <span class="icon">✎</span>
          Create Article
        </button>
        <button class="action-btn outline" @click="$router.push('/admin/settings')">
          <span class="icon">⚙</span>
          Settings
        </button>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue';
import { useSettingsStore } from '@/stores/settings';
import { getStats } from '@/api/stats';

const settingsStore = useSettingsStore();
const settings = computed(() => settingsStore.config);

// 统计数据状态
const statsData = ref({
  totalArticles: 0,
  totalViews: 0,
  totalTags: 0
});

// 计算属性生成卡片数据，方便遍历渲染
const statsCards = computed(() => [
  { label: 'Articles Published', value: statsData.value.totalArticles },
  { label: 'Total Views', value: statsData.value.totalViews.toLocaleString() },
  { label: 'Active Tags', value: statsData.value.totalTags }
]);

onMounted(async () => {
  // 确保设置已加载（获取作者名）
  if (!settingsStore.isLoaded) {
    await settingsStore.fetchSettings();
  }

  // 获取统计数据
  try {
    const data = await getStats();
    if (data) {
      statsData.value = {
        totalArticles: data.totalArticles || 0,
        totalViews: data.totalViews || 0,
        totalTags: data.totalTags || 0
      };
    }
  } catch (error) {
    console.error("Failed to load dashboard stats:", error);
  }
});
</script>

<style scoped>
.dashboard-container {
  max-width: var(--layout-max-width-dashboard);
  margin: 0 auto;
  padding: 40px;
  animation: fadeIn 0.8s ease-out;
}

/* 1. 欢迎横幅 - 极具艺术感 */
.welcome-banner {
  margin-bottom: 64px;
  text-align: left;
  position: relative;
}

.welcome-title {
  display: flex;
  flex-direction: column;
  line-height: 1.2;
}

.greeting {
  font-family: var(--font-family-display); /* Cinzel */
  font-size: 24px;
  letter-spacing: 4px;
  text-transform: uppercase;
  color: var(--color-secondary-structure);
  margin-bottom: 8px;
}

.name {
  font-family: var(--font-family-artistic); /* Pinyon Script */
  font-size: 80px;
  color: var(--color-primary-text);
  margin-left: -4px; /* Visual correction for script font */
  background: linear-gradient(45deg, var(--color-primary-text) 30%, var(--color-secondary-structure) 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.welcome-subtitle {
  font-family: var(--font-family-sans-serif);
  font-size: 16px;
  color: var(--color-secondary-structure);
  margin-top: 16px;
  letter-spacing: 1px;
  opacity: 0.8;
}

/* 2. 统计卡片 - 高对比、大数字 */
.stats-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 32px;
  margin-bottom: 64px;
}

.stat-card {
  background-color: var(--color-main-bg);
  border: 1px solid var(--color-card-shadow);
  padding: 32px;
  position: relative;
  overflow: hidden;
  transition: transform 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.27), 
              box-shadow 0.4s ease;
  
  /* 入场动画 */
  opacity: 0;
  animation: slideUpFade 0.6s ease-out forwards;
  animation-delay: var(--delay);
}

.stat-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 16px 32px var(--color-card-shadow);
  border-color: var(--color-functional-element);
}

.stat-content {
  position: relative;
  z-index: 2;
}

.stat-value {
  display: block;
  font-family: var(--font-family-display);
  font-size: 64px;
  line-height: 1;
  font-weight: 700;
  color: var(--color-primary-text);
  margin-bottom: 8px;
}

.stat-label {
  font-family: var(--font-family-sans-serif);
  font-size: 14px;
  text-transform: uppercase;
  letter-spacing: 2px;
  color: var(--color-secondary-structure);
}

/* 装饰性元素 */
.stat-card::after {
  content: '';
  position: absolute;
  top: 0;
  right: 0;
  width: 100px;
  height: 100px;
  background: linear-gradient(135deg, transparent 50%, rgba(0,0,0,0.02) 50%);
  transition: transform 0.4s ease;
}

.stat-card:hover::after {
  transform: scale(1.5);
}

.stat-decorative-line {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 0;
  height: 3px;
  background-color: var(--color-functional-element);
  transition: width 0.4s ease;
}

.stat-card:hover .stat-decorative-line {
  width: 100%;
}

/* 3. 快捷操作 */
.section-title {
  font-family: var(--font-family-display);
  font-size: 20px;
  margin-bottom: 24px;
  color: var(--color-primary-text);
}

.actions-wrapper {
  display: flex;
  gap: 24px;
}

.action-btn {
  display: inline-flex;
  align-items: center;
  gap: 12px;
  padding: 16px 32px;
  font-family: var(--font-family-sans-serif);
  font-size: 16px;
  font-weight: 600;
  letter-spacing: 1px;
  text-transform: uppercase;
  cursor: pointer;
  transition: all 0.3s ease;
  
  /* 实心风格 */
  background-color: var(--color-functional-element);
  color: var(--color-main-bg); /* 反色文本 */
  border: 1px solid var(--color-functional-element);
}

.action-btn:hover {
  background-color: transparent;
  color: var(--color-functional-element);
}

/* 描边风格 */
.action-btn.outline {
  background-color: transparent;
  color: var(--color-functional-element);
  border: 1px solid var(--color-secondary-structure);
}

.action-btn.outline:hover {
  border-color: var(--color-functional-element);
  background-color: rgba(0,0,0,0.02);
}

.icon {
  font-size: 18px;
}

/* Keyframe Animations */
@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

@keyframes slideUpFade {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 响应式调整 */
@media (max-width: 900px) {
  .stats-grid {
    grid-template-columns: 1fr;
  }
  .name {
    font-size: 60px;
  }
}
</style>
