<template>
  <textarea
    :value="modelValue"
    @input="$emit('update:modelValue', $event.target.value)"
    class="base-textarea"
    rows="3"
  ></textarea>
</template>

<script setup>
defineProps({
  modelValue: String,
});
defineEmits(['update:modelValue']);
</script>

<style scoped>
.base-textarea {
  width: 100%;
  border: 1px solid var(--color-secondary-structure);
  border-radius: 4px; /* A slight radius for textareas */
  background-color: transparent;
  padding: 8px 12px;
  font-size: 16px;
  font-family: var(--font-family-serif);
  color: var(--color-primary-text);
  outline: none;
  resize: vertical;
  transition: border-color var(--animation-duration-fast) ease-in-out;
}
.base-textarea:focus {
  border-color: var(--color-functional-element);
}
</style>