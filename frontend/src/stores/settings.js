import { defineStore } from 'pinia';
import { getAllSettings } from '@/api/settings';

export const useSettingsStore = defineStore('settings', {
  state: () => ({
    config: {}, // 存储博客配置项
    isLoaded: false, // 标记是否已从 API 加载过
  }),

  actions: {
    /**
     * 异步获取博客设置，如果尚未加载
     */
    async fetchSettings() {
      // 如果已经加载过，则直接返回，不再发请求
      if (this.isLoaded) {
        return;
      }
      try {
        const settingsData = await getAllSettings();
        this.config = settingsData;
        this.isLoaded = true;
      } catch (error) {
        console.error('Failed to fetch blog settings:', error);
        // 可以在这里处理加载失败的情况
      }
    },
  },
});