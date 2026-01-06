import { createI18n } from 'vue-i18n';

const messages = {
    en: {
        nav: {
            home: 'Home',
            featured: 'Featured',
            archive: 'Archive',
            tags: 'Tags',
            about: 'About',
            search: 'Search',
        },
        titles: {
            featured: 'Featured Articles',
            archive: 'Archive',
            all_tags: 'All Tags',
            tag: 'Tag: {name}',
            search: 'Search',
            about_author: 'About the Author',
        },
        footer: {
            rights: 'All rights reserved.',
        },
        home: {
            hero_title: 'The Art of Code',
            hero_subtitle: 'Exploring the intersection of technology and aesthetics',
            featured: 'Featured Stories',
            latest: 'Latest Posts',
            trending: 'Trending',
            tag_cloud: 'Tag Cloud',
        },
        search: {
            placeholder: 'Enter keywords to search...',
            button: 'Search',
            initial: 'Enter keywords to find articles.',
            loading: 'Searching...',
            no_results: 'No articles found matching "{query}".',
        },
        common: {
            loading: 'Loading...',
            loading_articles: 'Loading articles...',
            no_data: 'No content available.',
            no_featured: 'No featured articles yet.',
            no_archive: 'No articles published yet.',
            no_tags: 'No tags available.',
            read_more: 'Read more',
        },
        admin: {
            login: {
                title: 'Admin Portal',
                username: 'Username',
                password: 'Password',
                submit: 'Enter',
                loading: 'Authenticating...',
                error: 'Invalid credentials'
            },
            nav: {
                dashboard: 'Dashboard',
                articles: 'Articles',
                settings: 'Settings',
                logout: 'Logout'
            },
            articles: {
                title: 'Article Management',
                create: 'New Article',
                edit: 'Edit',
                delete: 'Delete',
                search: 'Search articles...',
                table: {
                    title: 'Title',
                    created: 'Created',
                    views: 'Views',
                    tags: 'Tags',
                    actions: 'Actions'
                },
                status: {
                    featured: 'Featured',
                    normal: 'Standard'
                },
                confirm_delete: 'Are you sure you want to delete this article?'
            },
            settings: {
                title: 'Blog Settings',
                save: 'Save Changes',
                success: 'Settings updated successfully.',
                sections: {
                    general: 'General Info',
                    author: 'Author Profile',
                    social: 'Social Media',
                    display: 'Display Options'
                }
            }
        }
    },
    zh: {
        nav: {
            home: '首页',
            featured: '精选',
            archive: '归档',
            tags: '标签',
            about: '关于',
            search: '搜索',
        },
        titles: {
            featured: '精选文章',
            archive: '文章归档',
            all_tags: '全部标签',
            tag: '标签：{name}',
            search: '搜索',
            about_author: '关于作者',
        },
        footer: {
            rights: '保留所有权利。',
        },
        home: {
            hero_title: '代码的艺术',
            hero_subtitle: '探索技术与美学的交汇点',
            featured: '精选推荐',
            latest: '最新发布',
            trending: '热门文章',
            tag_cloud: '标签云',
        },
        search: {
            placeholder: '输入关键词开始搜索',
            button: '搜索',
            initial: '输入关键词以查找文章。',
            loading: '正在搜索...',
            no_results: '未能找到与 “{query}” 相关的文章。',
        },
        common: {
            loading: '加载中...',
            loading_articles: '正在加载文章...',
            no_data: '暂无内容',
            no_featured: '暂无精选文章',
            no_archive: '尚未发布任何文章',
            no_tags: '暂无标签',
            read_more: '阅读更多',
        },
        admin: {
            login: {
                title: '管理控制台',
                username: '用户名',
                password: '密码',
                submit: '进入系统',
                loading: '验证中...',
                error: '用户名或密码无效'
            },
            nav: {
                dashboard: '仪表盘',
                articles: '文章管理',
                settings: '全局设置',
                logout: '退出登录'
            },
            articles: {
                title: '文章管理',
                create: '新建文章',
                edit: '编辑',
                delete: '删除',
                search: '搜索文章...',
                table: {
                    title: '标题',
                    created: '创建日期',
                    views: '阅读量',
                    tags: '标签',
                    actions: '操作'
                },
                status: {
                    featured: '精选',
                    normal: '普通'
                },
                confirm_delete: '确定要删除这篇文章吗？'
            },
            settings: {
                title: '博客设置',
                save: '保存更改',
                success: '设置已更新',
                sections: {
                    general: '基本信息',
                    author: '作者档案',
                    social: '社交媒体',
                    display: '显示选项'
                }
            }
        }
    }
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
