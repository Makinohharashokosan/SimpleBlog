<template>
  <div
    class="tags-input-wrapper"
    @click="focusInput"
  >
    <span
      v-for="(tag, index) in modelValue"
      :key="tag"
      class="tag-pill"
    >
      {{ tag }}
      <button
        class="remove-tag-btn"
        @click.stop="removeTag(index)"
      >×</button>
    </span>
    <input
      ref="inputRef"
      v-model="newTag"
      placeholder="添加标签..."
      class="tags-input"
      @keydown.enter.prevent="addTag"
      @keydown.backspace="handleBackspace"
      @blur="addTag"
    >
  </div>
</template>

<script setup>
import { ref } from 'vue';

const props = defineProps({
  modelValue: {
    type: Array,
    default: () => [],
  },
});
const emit = defineEmits(['update:modelValue']);

const newTag = ref('');
const inputRef = ref(null);

const addTag = () => {
  const tagToAdd = newTag.value.trim();
  // Check if tag exists (case-sensitive check on modelValue)
  if (tagToAdd && !props.modelValue.includes(tagToAdd)) {
    // Emit new array with added tag
    emit('update:modelValue', [...props.modelValue, tagToAdd]);
  }
  newTag.value = '';
};

const removeTag = (index) => {
  const newTags = [...props.modelValue];
  newTags.splice(index, 1);
  emit('update:modelValue', newTags);
};

const handleBackspace = () => {
  if (newTag.value === '' && props.modelValue.length > 0) {
    removeTag(props.modelValue.length - 1);
  }
};

const focusInput = () => {
  inputRef.value?.focus();
};
</script>

<style scoped>
.tags-input-wrapper {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 8px;
  border: 1px solid var(--color-secondary-structure);
  padding: 8px 12px;
  border-radius: 4px;
  cursor: text;
}
.tags-input-wrapper:focus-within {
  border-color: var(--color-functional-element);
}

.tag-pill {
  display: inline-flex;
  align-items: center;
  background-color: rgba(136, 136, 136, 0.2); /* semi-transparent secondary color */
  padding: 4px 8px;
  border-radius: 12px;
  font-family: var(--font-family-sans-serif);
  font-size: 14px;
}

.remove-tag-btn {
  background: none;
  border: none;
  color: var(--color-secondary-structure);
  cursor: pointer;
  margin-left: 6px;
  font-size: 16px;
  padding: 0;
  line-height: 1;
}
.remove-tag-btn:hover {
  color: var(--color-functional-element);
}

.tags-input {
  flex-grow: 1;
  border: none;
  outline: none;
  background: transparent;
  padding: 4px 0;
  color: var(--color-primary-text);
  font-family: var(--font-family-serif);
  font-size: 16px;
}
</style>
