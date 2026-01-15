<template>
  <button
    class="toggle-switch"
    :class="{ 'is-active': modelValue }"
    role="switch"
    :aria-checked="modelValue"
    @click="$emit('update:modelValue', !modelValue)"
  >
    <span class="knob" />
  </button>
</template>

<script setup>
defineProps({
  modelValue: Boolean,
});
defineEmits(['update:modelValue']);
</script>

<style scoped>
.toggle-switch {
  position: relative;
  display: inline-block;
  width: 40px;
  height: 20px;
  border-radius: 10px;
  background-color: transparent;
  border: 1px solid var(--color-secondary-structure);
  cursor: pointer;
  transition: background-color var(--animation-duration-fast) ease-in-out,
              border-color var(--animation-duration-fast) ease-in-out;
}

.toggle-switch.is-active {
  background-color: var(--color-functional-element);
  border-color: var(--color-functional-element);
}

.knob {
  position: absolute;
  top: 2px;
  left: 2px;
  width: 14px;
  height: 14px;
  border-radius: 50%;
  background-color: var(--color-secondary-structure);
  transition: transform var(--animation-duration-fast) ease-in-out,
              background-color var(--animation-duration-fast) ease-in-out;
}

.toggle-switch.is-active .knob {
  transform: translateX(20px);
  background-color: var(--color-main-bg);
}
</style>
