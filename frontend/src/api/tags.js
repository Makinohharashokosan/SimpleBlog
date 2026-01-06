import api from './index';

/**
 * 获取所有标签
 */
export const getAllTags = () => {
  return api.get('/api/tags');
};

/**
 * 根据标签名获取文章列表
 * @param {string} tagName - 标签名称
 * @param {object} pageable - 分页参数 { page, size, sort }
 */
export const getArticlesByTag = (tagName, pageable) => {
  return api.get(`/api/tags/${tagName}/articles`, { params: pageable });
};