import { ref, watch } from 'vue';

// Store theme refs in a map to share state across components using the same key
const themeStates = new Map();

export function useTheme(storageKey = 'theme') {
  // If state for this key doesn't exist, create it
  if (!themeStates.has(storageKey)) {
    const savedTheme = localStorage.getItem(storageKey) || 'light';
    themeStates.set(storageKey, ref(savedTheme));
  }

  const theme = themeStates.get(storageKey);

  const applyTheme = (newTheme) => {
    const htmlEl = document.documentElement;
    if (newTheme === 'dark') {
      htmlEl.classList.add('dark');
    } else {
      htmlEl.classList.remove('dark');
    }
  };

  // Watch for changes and update localStorage
  // We do NOT automatically apply to DOM here in a global watcher because 
  // multiple themes might exist. We only apply when THIS specific useTheme instance is active/mounted.
  watch(theme, (newTheme) => {
    localStorage.setItem(storageKey, newTheme);
    applyTheme(newTheme);
  });

  // Function to manually initialize/apply this theme (call on mount of a Layout)
  const initTheme = () => {
    applyTheme(theme.value);
  };

  const toggleTheme = () => {
    theme.value = theme.value === 'light' ? 'dark' : 'light';
  };

  return {
    theme,
    toggleTheme,
    initTheme
  };
}