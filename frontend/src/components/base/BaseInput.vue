<template>
  <input
    :value="modelValue"
    class="base-input"
    @input="$emit('update:modelValue', $event.target.value)"
  >
</template>

<script setup>
defineProps({
  modelValue: [String, Number],
});
defineEmits(['update:modelValue']);
</script>

<style scoped>
.base-input {
  width: 100%;
  border: none;
  border-bottom: 1px solid var(--color-secondary-structure);
  background-color: transparent;
  padding: 8px 0;
  font-size: 16px;
  font-family: var(--font-family-serif);
  color: var(--color-primary-text);
  outline: none;
  transition: border-color var(--animation-duration-fast) ease-in-out;
}
.base-input:focus {
  border-bottom-color: var(--color-functional-element);
}
</style>