import { createI18n } from 'vue-i18n';
import en from './locales/en';
import zh from './locales/zh';

const messages = {
    en,
    zh
};

const savedLocale = localStorage.getItem('locale');
const locale = (savedLocale === 'en' || savedLocale === 'zh') ? savedLocale : 'zh';

const i18n = createI18n({
    legacy: false, // Use Composition API
    globalInjection: true, // Enable $t in templates
    locale,
    fallbackLocale: 'en',
    messages,
});

export default i18n;
