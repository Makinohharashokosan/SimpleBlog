<template>
  <div class="pagination-controls">
    <button
      :disabled="isFirst"
      class="pagination-btn"
      @click="changePage(currentPage - 1)"
    >
      上一页
    </button>
    <button
      :disabled="isLast"
      class="pagination-btn"
      @click="changePage(currentPage + 1)"
    >
      下一页
    </button>
  </div>
</template>
<script>
export default {
    name: 'BasePagination', // add this line
    props: {
        currentPage: Number,
        isFirst: Boolean,
        isLast: Boolean,
    },
    emits: ['page-change'],
    setup(props, { emit }) {
        const changePage = (page) => {
            emit('page-change', page);
        };
        return {
            changePage,
        };
    },
};
</script>
<style scoped>
.pagination-controls {
  display: flex;
  justify-content: space-between;
  margin-top: 40px;
}
.pagination-btn {
  /* This is a button following the Button interaction model from the design doc */
  background: none;
  border: none;
  color: var(--color-functional-element);
  font-family: var(--font-family-sans-serif);
  font-size: 16px;
  cursor: pointer;
  position: relative;
  padding-bottom: 4px;
}
.pagination-btn::after {
  content: '';
  position: absolute;
  bottom: 0;
  left: 50%;
  right: 50%;
  height: 2px;
  background-color: var(--color-functional-element);
  transition: left var(--animation-duration-fast) ease-out, 
              right var(--animation-duration-fast) ease-out;
}
.pagination-btn:hover::after {
  left: 0;
  right: 0;
}
.pagination-btn:disabled {
  color: var(--color-secondary-structure);
  cursor: not-allowed;
}
.pagination-btn:disabled::after {
  background-color: transparent;
}
</style>
