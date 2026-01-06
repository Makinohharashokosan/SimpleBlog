import { defineStore } from 'pinia';
import { authenticateUser } from '@/api/auth';

// 使用 defineStore 创建一个 store
// 第一个参数是 store 的唯一 ID
export const useAuthStore = defineStore('auth', {
  // state 定义了 store 的数据
  state: () => ({
    // 尝试从 localStorage 读取 token，实现持久化登录
    token: localStorage.getItem('authToken') || null,
    isAuthenticated: !!localStorage.getItem('authToken'),
  }),

  // actions 定义了可以修改 state 的方法
  actions: {
    /**
     * 用户登录
     * @param {object} credentials - 包含 username 和 password
     */
    async login(credentials) {
      try {
        // 调用 API 进行认证
        // 假设成功后返回的数据结构是 { token: '...' }
        const response = await authenticateUser(credentials);

        // 从响应中获取 token
        const token = response.token; // 请根据您后端实际返回的字段调整
        if (!token) {
          throw new Error("Login response did not contain a token.");
        }

        // 更新 state
        this.token = token;
        this.isAuthenticated = true;

        // 将 token 存入 localStorage
        localStorage.setItem('authToken', token);

        return response;
      } catch (error) {
        // 如果登录失败，确保状态是登出的
        this.logout();
        // 抛出错误，以便 UI 层可以捕获并显示提示
        throw error;
      }
    },

    /**
     * 用户登出
     */
    logout() {
      // 清空 state
      this.token = null;
      this.isAuthenticated = false;

      // 从 localStorage 移除 token
      localStorage.removeItem('authToken');
    },
  },
});