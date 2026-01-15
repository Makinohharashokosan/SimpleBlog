<template>
  <div class="tags-page">
    <header class="page-header">
      <h1 class="page-title">
        {{ $t('admin.tags.title', 'Tags Management') }}
      </h1>
    </header>

    <!-- Loading State -->
    <div
      v-if="isLoading"
      class="loading-state"
    >
      <div class="spinner" />
      <span>{{ $t('admin.login.loading') }}</span>
    </div>

    <!-- Empty State -->
    <div
      v-else-if="tags.length === 0"
      class="empty-state"
    >
      <p class="empty-text">
        {{ $t('common.no_data', 'No tags found') }}
      </p>
    </div>

    <!-- Tags List -->
    <div
      v-else
      class="list-container"
    >
      <div class="list-header">
        <span class="col-id">ID</span>
        <span class="col-name">{{ $t('admin.tags.name', 'Name') }}</span>
        <span class="col-actions">{{ $t('admin.articles.table.actions') }}</span>
      </div>

      <transition-group
        name="list"
        tag="div"
        class="tag-rows"
      >
        <div 
          v-for="(tag, index) in tags" 
          :key="tag.id" 
          class="tag-row"
          :style="{ '--delay': `${index * 30}ms` }"
        >
          <div class="col-id">
            #{{ tag.id }}
          </div>
          <div class="col-name">
            <span class="tag-name">{{ tag.name }}</span>
          </div>
          <div class="col-actions">
            <button
              class="action-btn edit"
              :title="$t('admin.articles.edit')"
              @click="promptRename(tag)"
            >
              ✎
            </button>
            <button
              class="action-btn delete"
              :title="$t('admin.articles.delete')"
              @click="promptDelete(tag)"
            >
              ✖
            </button>
          </div>
        </div>
      </transition-group>
    </div>

    <!-- Rename Modal -->
    <BaseModal
      :show="isRenameModalVisible"
      :title="$t('admin.tags.rename', 'Rename Tag')"
      @close="closeRenameModal"
      @confirm="confirmRename"
    >
      <div class="modal-content">
        <BaseInput
          v-model="renameValue"
          :placeholder="$t('admin.tags.name', 'Tag Name')"
          auto-focus
          @keyup.enter="confirmRename"
        />
      </div>
    </BaseModal>

    <!-- Delete Confirmation Modal -->
    <BaseModal
      :show="isDeleteModalVisible"
      :title="$t('admin.tags.delete', 'Delete Tag')"
      @close="closeDeleteModal"
      @confirm="confirmDelete"
    >
      <p class="modal-text">
        {{ $t('admin.tags.confirm_delete', 'Are you sure you want to delete this tag?') }}
        <br>
        <strong class="highlight-text">#{{ tagToAction?.name }}</strong>
      </p>
    </BaseModal>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { getAllTags, updateTag, deleteTag } from '@/api/tags';
import BaseModal from '@/components/base/BaseModal.vue';
import BaseInput from '@/components/base/BaseInput.vue';


const isLoading = ref(true);
const tags = ref([]);

const isRenameModalVisible = ref(false);
const isDeleteModalVisible = ref(false);
const tagToAction = ref(null);
const renameValue = ref('');

const fetchTags = async () => {
  isLoading.value = true;
  try {
    const response = await getAllTags();
    // Assuming API returns array or page. Based on JSON: items: TagResponse
    // getAllTags in tags.js calls /api/tags which returns array directly according to OpenApi
    tags.value = response;
  } catch (error) {
    console.error("Failed to fetch tags:", error);
  } finally {
    isLoading.value = false;
  }
};

onMounted(() => {
  fetchTags();
});

// Rename Logic
const promptRename = (tag) => {
  tagToAction.value = tag;
  renameValue.value = tag.name;
  isRenameModalVisible.value = true;
};

const closeRenameModal = () => {
  isRenameModalVisible.value = false;
  tagToAction.value = null;
  renameValue.value = '';
};

const confirmRename = async () => {
  if (!tagToAction.value || !renameValue.value.trim()) return;
  
  try {
    await updateTag(tagToAction.value.id, renameValue.value.trim());
    // Update local state to avoid refetch flashes if possible, or just refetch
    fetchTags();
    closeRenameModal();
  } catch (error) {
    console.error("Failed to rename tag:", error);
    alert("Failed to rename tag.");
  }
};

// Delete Logic
const promptDelete = (tag) => {
  tagToAction.value = tag;
  isDeleteModalVisible.value = true;
};

const closeDeleteModal = () => {
  isDeleteModalVisible.value = false;
  tagToAction.value = null;
};

const confirmDelete = async () => {
  if (!tagToAction.value) return;
  try {
    await deleteTag(tagToAction.value.id);
    fetchTags();
    closeDeleteModal();
  } catch (error) {
    console.error("Failed to delete tag:", error);
    alert("Failed to delete tag. It might be in use.");
  }
};
</script>

<style scoped>
.tags-page {
  max-width: var(--layout-max-width-dashboard);
  margin: 0 auto;
  padding: 20px;
  animation: fadeIn 0.8s ease-out;
}

.page-header {
  margin-bottom: 48px;
}

.page-title {
  font-family: var(--font-family-display);
  font-size: 32px;
  color: var(--color-primary-text);
  letter-spacing: 1px;
}

/* List Styles */
.list-container {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.list-header {
  display: grid;
  grid-template-columns: 80px 1fr 120px;
  padding: 0 24px 16px;
  border-bottom: 1px solid var(--color-secondary-structure);
  font-family: var(--font-family-sans-serif);
  font-size: 12px;
  text-transform: uppercase;
  letter-spacing: 2px;
  color: var(--color-secondary-structure);
}

.tag-row {
  display: grid;
  grid-template-columns: 80px 1fr 120px;
  align-items: center;
  padding: 20px 24px;
  background-color: var(--color-main-bg);
  border: 1px solid var(--color-card-shadow);
  transition: all 0.3s ease;
  
  /* Animation */
  opacity: 0;
  animation: slideUpFade 0.5s ease-out forwards;
  animation-delay: var(--delay);
}

.tag-row:hover {
  transform: translateX(8px);
  border-color: var(--color-functional-element);
  box-shadow: -4px 4px 0 var(--color-card-shadow);
}

.col-id {
  font-family: 'Fira Code', monospace;
  color: var(--color-secondary-structure);
  font-size: 14px;
}

.col-name {
  font-family: var(--font-family-sans-serif);
  font-weight: 600;
  color: var(--color-primary-text);
  font-size: 16px;
}

.col-actions {
  display: flex;
  gap: 16px;
}

.action-btn {
  background: none;
  border: none;
  font-size: 18px;
  cursor: pointer;
  transition: transform 0.2s ease, color 0.2s ease;
  color: var(--color-secondary-structure);
}

.action-btn:hover {
  transform: scale(1.2);
}

.action-btn.edit:hover { color: var(--color-functional-element); }
.action-btn.delete:hover { color: #e57373; }

/* Modal Content */
.modal-content {
  padding: 10px 0;
}
.modal-text {
  font-size: 16px;
  line-height: 1.6;
  text-align: center;
}
.highlight-text {
  color: var(--color-functional-element);
}

/* Loading & Empty */
.loading-state, .empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 16px;
  padding: 80px 0;
  color: var(--color-secondary-structure);
}
.empty-text {
  font-family: var(--font-family-artistic);
  font-size: 24px;
}
.spinner {
  width: 40px;
  height: 40px;
  border: 3px solid var(--color-card-shadow);
  border-top-color: var(--color-functional-element);
  border-radius: 50%;
  animation: spin 1s linear infinite;
}

/* Animations */
@keyframes spin { to { transform: rotate(360deg); } }
@keyframes fadeIn { from { opacity: 0; } to { opacity: 1; } }
@keyframes slideUpFade {
  from { opacity: 0; transform: translateY(10px); }
  to { opacity: 1; transform: translateY(0); }
}

.list-enter-active, .list-leave-active { transition: all 0.5s ease; }
.list-enter-from, .list-leave-to { opacity: 0; transform: translateX(-20px); }
</style>
