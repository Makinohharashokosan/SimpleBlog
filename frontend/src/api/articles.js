import api from './index';

/**
 * 获取所有文章（分页）
 * @param {object} pageable - 分页参数 { page, size, sort }
 */
export const getAllArticles = (pageable) => {
  return api.get('/api/articles', { params: pageable });
};

/**
 * 根据 ID 获取单篇文章详情
 * @param {number} id - 文章 ID
 */
export const getArticleById = (id) => {
  return api.get(`/api/articles/${id}`);
};

/**
 * 创建新文章
 * @param {object} createArticleRequest - 文章数据 { title, content, tags }
 */
export const createArticle = (createArticleRequest) => {
  return api.post('/api/articles', createArticleRequest);
};

/**
 * 更新文章
 * @param {number} id - 文章 ID
 * @param {object} updateArticleRequest - 文章数据 { title, content, tags }
 */
export const updateArticle = (id, updateArticleRequest) => {
  return api.put(`/api/articles/${id}`, updateArticleRequest);
};

/**
 * 搜索文章
 * @param {string} query - 搜索关键词
 * @param {object} pageable - 分页参数
 */
export const searchArticles = (query, pageable) => {
  return api.get('/api/articles/search', { params: { q: query, ...pageable } });
};

/**
 * 获取精选文章列表
 */
export const getFeaturedArticles = () => {
  return api.get('/api/articles/featured');
};

/**
 * 设置文章的精选状态
 * @param {number} id - 文章 ID
 * @param {boolean} featured - 是否精选
 */
export const setArticleFeatured = (id, featured) => {
  // PUT 请求体通常为 null 或空对象，参数通过 query string 传递
  return api.put(`/api/articles/${id}/feature`, null, { params: { featured } });
};

/**
 * 获取热门文章列表
 * @param {number} limit - 获取数量，默认为 5
 */
export const getTrendingArticles = (limit = 5) => {
  return api.get('/api/articles/trending', { params: { limit } });
};

/**
 * 删除文章
 * @param {number} id - 文章 ID
 */
export const deleteArticleById = (id) => {
  return api.delete(`/api/articles/${id}`);
};