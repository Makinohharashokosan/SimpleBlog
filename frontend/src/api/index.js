import axios from 'axios';
// 导入我们刚刚创建的 auth store
import { useAuthStore } from '@/stores/auth';

const api = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 10000,
});

api.interceptors.request.use(
  config => {
    // 在请求发送前，获取 auth store
    const authStore = useAuthStore();

    // 如果 token 存在，则添加到 Authorization 请求头中
    if (authStore.token) {
      config.headers.Authorization = `Bearer ${authStore.token}`;
    }

    return config;
  },
  error => {
    return Promise.reject(error);
  }
);

api.interceptors.response.use(
  response => response.data,
  error => {
    console.error('API Error:', error.response || error.message);
    return Promise.reject(error);
  }
);

export default api;