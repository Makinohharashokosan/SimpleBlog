// We define all admin-related routes here.
const routes = [
  {
    path: '/admin/login',
    name: 'AdminLogin',
    meta: {
      layout: 'BlankLayout',
    },
    component: () => import('@/views/admin/AdminLogin.vue'),
  },
  {
    path: '/admin/dashboard',
    name: 'AdminDashboard',
    meta: {
      layout: 'AdminLayout',
      requiresAuth: true, // This route requires authentication
    },
    component: () => import('@/views/admin/AdminDashboard.vue'),
  },
  {
    path: '/admin/settings',
    name: 'AdminSettings',
    meta: {
      layout: 'AdminLayout',
      requiresAuth: true,
    },
    component: () => import('@/views/admin/AdminSettings.vue'),
  },
  {
    path: '/admin/articles',
    name: 'AdminArticleList',
    meta: {
      layout: 'AdminLayout',
      requiresAuth: true,
    },
    component: () => import('@/views/admin/AdminArticleList.vue'),
  },
  {
    path: '/admin/articles/new',
    name: 'AdminArticleCreate',
    meta: {
      layout: 'AdminLayout',
      requiresAuth: true,
    },
    component: () => import('@/views/admin/AdminArticleEditor.vue'),
  },
  {
    path: '/admin/articles/edit/:id',
    name: 'AdminArticleEdit',
    meta: {
      layout: 'AdminLayout',
      requiresAuth: true,
    },
    component: () => import('@/views/admin/AdminArticleEditor.vue'),
  },
];

export default routes;