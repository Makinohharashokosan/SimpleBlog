<template>
  <div class="tags-input-wrapper" @click="focusInput">
    <span v-for="(tag, index) in tags" :key="tag" class="tag-pill">
      {{ tag }}
      <button @click.stop="removeTag(index)" class="remove-tag-btn">×</button>
    </span>
    <input
      ref="inputRef"
      v-model="newTag"
      @keydown.enter.prevent="addTag"
      @keydown.backspace="handleBackspace"
      placeholder="添加标签..."
      class="tags-input"
    />
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

const tags = ref([...props.modelValue]);
const newTag = ref('');
const inputRef = ref(null);

const addTag = () => {
  const tagToAdd = newTag.value.trim();
  if (tagToAdd && !tags.value.includes(tagToAdd)) {
    tags.value.push(tagToAdd);
    emit('update:modelValue', tags.value);
  }
  newTag.value = '';
};

const removeTag = (index) => {
  tags.value.splice(index, 1);
  emit('update:modelValue', tags.value);
};

const handleBackspace = () => {
  if (newTag.value === '' && tags.value.length > 0) {
    removeTag(tags.value.length - 1);
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
