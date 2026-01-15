// For now, we only need a placeholder for the home page.
// We will add all other public pages later.
const routes = [
  {
    path: '/',
    name: 'Home',
    // We use meta fields to specify layout and access control
    meta: {
      layout: 'PublicLayout',
    },
    // The component will be created in a later step
    component: () => import('@/views/public/PublicHome.vue'),
  },
  {
    path: '/article/:id',
    name: 'ArticleDetail',
    meta: {
      layout: 'PublicLayout',
    },
    component: () => import('@/views/public/PublicArticleDetail.vue'),
  },
  {
    path: '/featured',
    name: 'FeaturedArticles',
    meta: {
      layout: 'PublicLayout',
    },
    component: () => import('@/views/public/PublicFeatured.vue'),
  },
  {
    // Note the dynamic parameter ':tagName'
    path: '/tags/:tagName',
    name: 'TagArticles',
    meta: {
      layout: 'PublicLayout',
    },
    component: () => import('@/views/public/PublicTagArticles.vue'),
  },
  {
    path: '/search',
    name: 'Search',
    meta: {
      layout: 'PublicLayout',
    },
    component: () => import('@/views/public/PublicSearch.vue'),
  },
  {
    path: '/archive',
    name: 'Archive',
    meta: {
      layout: 'PublicLayout',
    },
    component: () => import('@/views/public/PublicArchive.vue'),
  },
  {
    path: '/tags',
    name: 'Tags',
    meta: {
      layout: 'PublicLayout',
    },
    component: () => import('@/views/public/PublicTags.vue'),
  },
  {
    path: '/about',
    name: 'About',
    meta: {
      layout: 'PublicLayout',
    },
    component: () => import('@/views/public/PublicAbout.vue'),
  },
];

export default routes;