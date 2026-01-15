<template>
  <button 
    class="theme-switcher artistic-touch" 
    :aria-label="`Switch to ${nextTheme} mode`" 
    @click="toggleTheme"
  >
    <div class="icon-wrapper">
      <!-- 
        Logic: Show the icon representing the TARGET state.
        If current theme is DARK, show SUN (to switch to light).
        If current theme is LIGHT, show MOON (to switch to dark).
      -->
      
      <!-- Artistic Sun (Classical Starburst) -->
      <svg 
        class="icon sun-art" 
        :class="{ visible: theme === 'dark' }" 
        viewBox="0 0 24 24"
        xmlns="http://www.w3.org/2000/svg"
      >
        <circle
          cx="12"
          cy="12"
          r="4"
          stroke="currentColor"
          stroke-width="1.2"
          fill="none"
        />
        <path
          d="M12 1V4 M12 20V23 M1 12H4 M20 12H23 M4.2 4.2L6.5 6.5 M17.5 17.5L19.8 19.8 M4.2 19.8L6.5 17.5 M17.5 6.5L19.8 4.2"
          stroke="currentColor"
          stroke-width="1.2"
          stroke-linecap="square"
        />
      </svg>
      
      <!-- Artistic Moon (Crescent) -->
      <svg 
        class="icon moon-art" 
        :class="{ visible: theme === 'light' }" 
        viewBox="0 0 24 24"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path
          d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79Z"
          stroke="currentColor"
          stroke-width="1.2"
          stroke-linejoin="round"
          fill="none"
        />
      </svg>
    </div>
  </button>
</template>

<script setup>
import { computed } from 'vue';
import { useTheme } from '@/composables/useTheme';

const props = defineProps({
  storageKey: {
    type: String,
    default: 'theme'
  }
});

const { theme, toggleTheme } = useTheme(props.storageKey);
const nextTheme = computed(() => (theme.value === 'light' ? 'dark' : 'light'));
</script>

<style scoped>
.theme-switcher {
  background: none;
  border: none;
  width: 48px;
  height: 48px;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  color: var(--color-functional-element);
  position: relative;
  overflow: hidden;
  /* Add a subtle decorative ring */
  border: 1px solid transparent;
  border-radius: 50%;
  transition: all var(--animation-duration-normal) ease;
}

.theme-switcher:hover {
  background-color: rgba(125, 125, 125, 0.05);
  border-color: var(--color-secondary-structure);
}

.icon-wrapper {
  position: relative;
  width: 24px;
  height: 24px;
}

.icon {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
  transform: scale(0.5) rotate(-30deg);
  transition: opacity var(--animation-duration-normal) ease,
              transform var(--animation-duration-normal) cubic-bezier(0.34, 1.56, 0.64, 1); /* Bouncy effect */
}

.icon.visible {
  opacity: 1;
  transform: scale(1) rotate(0deg);
}

/* Specific styling adjustments */
.sun-art {
  color: var(--color-functional-element);
}
.moon-art {
  color: var(--color-functional-element);
}
</style>