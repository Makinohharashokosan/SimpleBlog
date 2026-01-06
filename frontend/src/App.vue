<template>
  <!-- 
    This is the root component of the application.
    Its primary role is to manage the overall layout.
  -->
  <component :is="layoutComponent">
    <!-- 
      The <router-view> is where the content of the current page is rendered.
      We use the v-slot API to wrap the page component with a <transition> element.
      This enables animated transitions between pages globally.
    -->
    <router-view v-slot="{ Component }">
      <transition name="page-fade" mode="out-in">
        <!-- The actual page component (e.g., PublicHome.vue, AdminDashboard.vue) gets rendered here -->
        <component :is="Component" />
      </transition>
    </router-view>
  </component>
</template>

<script setup>
import { computed } from 'vue';
import { useRoute } from 'vue-router';

// Import all layout components for dynamic selection.
import PublicLayout from '@/layouts/PublicLayout.vue';
import AdminLayout from '@/layouts/AdminLayout.vue';
import BlankLayout from '@/layouts/BlankLayout.vue';

const route = useRoute();

// A map to associate layout names from the router with their components.
const layouts = {
  PublicLayout,
  AdminLayout,
  BlankLayout,
};

// This computed property dynamically determines which layout to use based on the route's meta field.
const layoutComponent = computed(() => {
  const layoutName = route.meta.layout;
  return layouts[layoutName] || PublicLayout; // Default to PublicLayout if not specified.
});
</script>

<style>
/*
  Global styles for the page transition animation.
  This creates a "fade and slight slide-up" effect for all route changes.
  The 'name' attribute in the <transition> tag ("page-fade") corresponds to these class names.
*/
.page-fade-enter-active,
.page-fade-leave-active {
  /*
    Defines the duration and easing function for both entering and leaving animations.
    We use our CSS variables for consistency.
  */
  transition: opacity var(--animation-duration-normal) ease, 
              transform var(--animation-duration-normal) ease;
}

.page-fade-enter-from,
.page-fade-leave-to {
  /*
    Defines the starting state for an entering element and the ending state for a leaving element.
    - opacity: 0 -> The element is fully transparent.
    - transform: translateY(16px) -> The element is shifted down by 16px.
  */
  opacity: 0;
  transform: translateY(16px);
}
</style>