import api from './index';

/**
 * 获取博客统计数据
 * GET /api/stats
 * @returns {Promise<Object>} { totalArticles, totalTags, totalViews }
 */
export const getStats = () => {
    return api.get('/api/stats');
};
