<template>
  <transition name="modal-fade">
    <div
      v-if="show"
      class="modal-backdrop"
      @click.self="$emit('close')"
    >
      <div class="modal-content">
        <header class="modal-header">
          <h3 class="modal-title">
            {{ title }}
          </h3>
        </header>
        <section class="modal-body">
          <slot>This is the default body.</slot>
        </section>
        <footer class="modal-footer">
          <button
            class="btn-secondary"
            @click="$emit('close')"
          >
            取消
          </button>
          <button
            class="btn-primary"
            @click="$emit('confirm')"
          >
            确认
          </button>
        </footer>
      </div>
    </div>
  </transition>
</template>

<script setup>
defineProps({
  show: Boolean,
  title: {
    type: String,
    default: '确认操作',
  },
});
defineEmits(['close', 'confirm']);
</script>

<style scoped>
.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.6);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  width: 500px;
  background-color: var(--color-main-bg);
  box-shadow: 0 5px 15px rgba(0,0,0,0.3);
  display: flex;
  flex-direction: column;
}

.modal-header, .modal-footer, .modal-body {
  padding: 24px;
}

.modal-header {
  border-bottom: 1px solid var(--color-secondary-structure);
}

.modal-title {
  font-family: var(--font-family-sans-serif);
  font-size: 20px;
  font-weight: bold;
  color: var(--color-primary-text);
}

.modal-body {
  font-family: var(--font-family-serif);
  color: var(--color-primary-text);
  line-height: 1.8;
}

.modal-footer {
  border-top: 1px solid var(--color-secondary-structure);
  display: flex;
  justify-content: flex-end;
  gap: 16px;
}

.btn-primary, .btn-secondary {
  border: none;
  background: none;
  font-family: var(--font-family-sans-serif);
  font-size: 16px;
  cursor: pointer;
  padding: 8px 16px;
}

.btn-secondary { /* Styled as a Link */
  color: var(--color-secondary-structure);
}
.btn-secondary:hover {
  color: var(--color-functional-element);
}

.btn-primary { /* Styled as a Button */
  background-color: var(--color-functional-element);
  color: var(--color-main-bg);
}
.btn-primary:hover {
  opacity: 0.85;
}

/* Transitions */
.modal-fade-enter-active, .modal-fade-leave-active {
  transition: opacity var(--animation-duration-normal);
}
.modal-fade-enter-from, .modal-fade-leave-to {
  opacity: 0;
}
</style>
