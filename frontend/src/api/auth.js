import api from './index';

/**
 * 用户登录认证
 * @param {object} loginRequest - 包含 username 和 password
 */
export const authenticateUser = (loginRequest) => {
  return api.post('/api/auth/login', loginRequest);
};

/**
 * 修改密码
 * @param {object} passwordData - { oldPassword, newPassword }
 */
export const changePassword = (passwordData) => {
  return api.post('/api/auth/change-password', passwordData);
};

/**
 * 修改用户名
 * @param {object} data - { newUsername, password }
 */
export const changeUsername = (data) => {
  return api.post('/api/auth/change-username', data);
};