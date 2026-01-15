import api from './index';

/**
 * 获取所有博客设置
 */
export const getAllSettings = () => {
  return api.get('/api/settings');
};

/**
 * 更新所有博客设置
 * @param {object} settingsData - 包含所有设置项的对象
 */
export const updateAllSettings = (settingsData) => {
  // 假设 API 端点是 PUT /api/settings
  return api.put('/api/settings', settingsData);
};