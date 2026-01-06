import api from './index';

/**
 * 用户登录认证
 * @param {object} loginRequest - 包含 username 和 password
 */
export const authenticateUser = (loginRequest) => {
  return api.post('/api/auth/login', loginRequest);
};