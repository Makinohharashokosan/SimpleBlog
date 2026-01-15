import { createRouter, createWebHistory } from 'vue-router';
import { useAuthStore } from '@/stores/auth';

// Import route modules
import publicRoutes from './publicRoutes';
import adminRoutes from './adminRoutes';

const routes = [
  ...publicRoutes,
  ...adminRoutes,
  // TODO: Add a 404 Not Found route later
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

// --- Navigation Guard ---
// This function runs before each navigation.
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth);

  // If the route requires auth and the user is not authenticated
  if (requiresAuth && !authStore.isAuthenticated) {
    // Redirect to the login page
    next({ name: 'AdminLogin' });
  } else {
    // Otherwise, allow navigation
    next();
  }
});

export default router;