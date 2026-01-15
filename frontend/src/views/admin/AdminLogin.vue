<template>
  <div class="login-container">
    <!-- Artistic Background Elements -->
    <div class="bg-shape shape-1" />
    <div class="bg-shape shape-2" />
    <div class="bg-shape shape-3" />

    <!-- Global Controls -->
    <div class="global-controls">
      <ThemeSwitcher storage-key="admin-theme" />
      <LanguageSwitcher />
    </div>

    <div
      class="login-card"
      :class="{ 'shake': hasError }"
    >
      <header class="login-header">
        <h1 class="title">
          {{ $t('admin.login.title') }}
        </h1>
        <p class="subtitle">
          Enter the realm of creation
        </p>
      </header>
      
      <form
        class="login-form"
        @submit.prevent="handleLogin"
      >
        <div class="input-group">
          <input 
            v-model="username" 
            type="text"
            required
            class="input-field"
            placeholder=" "
          >
          <label class="input-label">{{ $t('admin.login.username') }}</label>
          <span class="input-highlight" />
        </div>
        
        <div class="input-group">
          <input 
            v-model="password" 
            type="password"
            required
            class="input-field"
            placeholder=" "
          >
          <label class="input-label">{{ $t('admin.login.password') }}</label>
          <span class="input-highlight" />
        </div>
        
        <transition name="fade">
          <div
            v-if="errorMessage"
            class="error-message"
          >
            <span class="error-icon">⚠</span>
            {{ $t('admin.login.error') }}
          </div>
        </transition>

        <button
          type="submit"
          class="login-button"
          :disabled="isLoading"
        >
          <span class="btn-text">{{ isLoading ? $t('admin.login.loading') : $t('admin.login.submit') }}</span>
          <div class="btn-bg" />
        </button>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import ThemeSwitcher from '@/components/common/ThemeSwitcher.vue';
import LanguageSwitcher from '@/components/common/LanguageSwitcher.vue';
import { useTheme } from '@/composables/useTheme';

const { initTheme } = useTheme('admin-theme');

onMounted(() => {
  initTheme();
});

const username = ref('');
const password = ref('');

const errorMessage = ref(null);
const isLoading = ref(false);
const hasError = ref(false);

const router = useRouter();
const authStore = useAuthStore();

const handleLogin = async () => {
  isLoading.value = true;
  errorMessage.value = null;
  hasError.value = false;

  try {
    await authStore.login({
      username: username.value,
      password: password.value,
    });
    // Slight delay to show success state if we had one, but routing is fast enough
    router.push({ name: 'AdminDashboard' });
  } catch (error) {
    errorMessage.value = 'Invalid credentials';
    hasError.value = true;
    setTimeout(() => {
      hasError.value = false;
    }, 500);
  } finally {
    isLoading.value = false;
  }
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-color: var(--color-main-bg);
  position: relative;
  overflow: hidden;
  transition: background-color 0.5s ease;
}

/* Artistic Background Shapes */
.bg-shape {
  position: absolute;
  border-radius: 50%;
  filter: blur(80px);
  z-index: 0;
  opacity: 0.6;
}

.shape-1 {
  width: 500px;
  height: 500px;
  background: radial-gradient(circle, rgba(66, 184, 131, 0.4) 0%, rgba(0,0,0,0) 70%); /* Vue Green-ish */
  top: -100px;
  left: -100px;
  animation: float 10s infinite ease-in-out alternate;
}

.shape-2 {
  width: 400px;
  height: 400px;
  background: radial-gradient(circle, rgba(53, 73, 94, 0.4) 0%, rgba(0,0,0,0) 70%); /* Vue Dark Blue */
  bottom: -50px;
  right: -50px;
  animation: float 12s infinite ease-in-out alternate-reverse;
}

.shape-3 {
  width: 300px;
  height: 300px;
  background: radial-gradient(circle, rgba(100, 100, 100, 0.2) 0%, rgba(0,0,0,0) 70%);
  top: 40%;
  left: 30%;
  animation: pulse 8s infinite ease-in-out;
}

@keyframes float {
  0% { transform: translate(0, 0); }
  100% { transform: translate(30px, 50px); }
}

@keyframes pulse {
  0%, 100% { transform: scale(1); opacity: 0.3; }
  50% { transform: scale(1.2); opacity: 0.5; }
}

/* Glassmorphism Card */
.login-card {
  width: 400px;
  padding: 48px;
  background: rgba(255, 255, 255, 0.1); 
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.2);
  border-radius: 16px;
  box-shadow: 0 20px 50px rgba(0, 0, 0, 0.15);
  z-index: 10;
  position: relative;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

html.dark .login-card {
  background: rgba(0, 0, 0, 0.4);
  border-color: rgba(255, 255, 255, 0.05);
}

.login-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 30px 60px rgba(0, 0, 0, 0.2);
}

/* Header */
.login-header {
  text-align: center;
  margin-bottom: 48px;
}

.title {
  font-family: var(--font-family-display); /* Cinzel */
  font-size: 32px;
  font-weight: 700;
  color: var(--color-primary-text);
  margin-bottom: 8px;
  letter-spacing: 2px;
  text-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.subtitle {
  font-family: var(--font-family-artistic); /* Pinyon Script */
  font-size: 24px; /* Large script needs size */
  color: var(--color-secondary-structure);
  opacity: 0.8;
}

/* Form */
.login-form {
  display: flex;
  flex-direction: column;
  gap: 32px; /* More space */
}

.input-group {
  position: relative;
}

.input-field {
  width: 100%;
  border: none;
  border-bottom: 1px solid rgba(136, 136, 136, 0.5);
  background: transparent;
  padding: 12px 0;
  font-size: 16px;
  color: var(--color-primary-text);
  outline: none;
  font-family: var(--font-family-sans-serif);
  transition: all 0.3s ease;
}

.input-label {
  position: absolute;
  top: 12px;
  left: 0;
  font-size: 16px;
  color: var(--color-secondary-structure);
  pointer-events: none;
  transition: all 0.3s ease;
  font-family: var(--font-family-sans-serif);
}

/* Animated Highlight Bar */
.input-highlight {
  position: absolute;
  bottom: 0;
  left: 0;
  width: 0;
  height: 2px;
  background-color: var(--color-functional-element);
  transition: width 0.4s ease;
}

/* Input Focus States (Float Label) */
.input-field:focus ~ .input-label,
.input-field:not(:placeholder-shown) ~ .input-label {
  top: -20px;
  font-size: 12px;
  color: var(--color-functional-element);
  font-weight: bold;
}

.input-field:focus {
  border-bottom-color: transparent;
}

.input-field:focus ~ .input-highlight {
  width: 100%;
}

/* Error Message */
.error-message {
  color: #ff5252;
  font-size: 14px;
  font-family: var(--font-family-sans-serif);
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  margin-top: -10px;
}

/* Button */
.login-button {
  position: relative;
  width: 100%;
  padding: 16px;
  background: transparent;
  border: 1px solid var(--color-functional-element);
  outline: none;
  cursor: pointer;
  overflow: hidden;
  transition: all 0.4s ease;
}

.btn-text {
  position: relative;
  z-index: 2;
  font-family: var(--font-family-sans-serif);
  font-size: 16px;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 2px;
  color: var(--color-primary-text);
  transition: color 0.4s ease;
}

.btn-bg {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: var(--color-functional-element);
  transform: translateX(-101%);
  transition: transform 0.4s cubic-bezier(0.25, 0.8, 0.25, 1);
  z-index: 1;
}

.login-button:hover .btn-bg {
  transform: translateX(0);
}

.login-button:hover .btn-text {
  color: var(--color-main-bg); /* Inverse color */
}

/* Loading/Disabled State */
.login-button:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

/* Shake Animation */
.shake {
  animation: shake 0.5s ease-in-out;
}

@keyframes shake {
  0%, 100% { transform: translateX(0); }
  10%, 30%, 50%, 70%, 90% { transform: translateX(-8px); }
  20%, 40%, 60%, 80% { transform: translateX(8px); }
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
}

.global-controls {
  position: absolute;
  top: 32px;
  right: 32px;
  display: flex;
  gap: 16px;
  z-index: 20;
}
</style>