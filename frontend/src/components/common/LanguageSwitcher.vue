<template>
  <button @click="toggleLanguage" class="lang-switcher" :title="`Switch to ${nextLocale.toUpperCase()}`">
    {{ currentLocale.toUpperCase() }}
  </button>
</template>

<script setup>
import { computed } from 'vue';
import { useI18n } from 'vue-i18n';

const { locale } = useI18n();

const currentLocale = computed(() => locale.value);
const nextLocale = computed(() => (locale.value === 'zh' ? 'en' : 'zh'));

const toggleLanguage = () => {
  locale.value = nextLocale.value;
  localStorage.setItem('locale', locale.value); // Persist selection
};
</script>

<style scoped>
.lang-switcher {
  background: none;
  border: none;
  font-family: var(--font-family-display); /* Use the artistic font */
  font-size: 14px;
  font-weight: 700;
  color: var(--color-functional-element);
  cursor: pointer;
  padding: 4px 8px;
  letter-spacing: 1px;
  transition: opacity var(--animation-duration-fast);
}

.lang-switcher:hover {
  opacity: 0.7;
}
</style>
