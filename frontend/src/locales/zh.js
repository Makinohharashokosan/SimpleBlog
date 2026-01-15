export default {
    nav: {
        home: '首页',
        featured: '精选',
        archive: '归档',
        tags: '标签',
        about: '关于',
        search: '搜索',
        admin: '管理',
    },
    titles: {
        featured: '精选文章',
        archive: '文章档案',
        all_tags: '全部标签',
        tag: '标签：{name}',
        search: '搜索',
        about_author: '关于作者',
    },
    footer: {
        rights: '保留所有权利。',
    },
    home: {
        hero_title: 'MakinoharaShoko空间',
        hero_subtitle: '技术与美学的交汇点',
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
        related_stories: '相关推荐',
        views: '{count} 次阅读',
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
            tags: '标签管理',
            settings: '全局设置',
            logout: '退出登录'
        },
        dashboard: {
            welcome: '欢迎回来，',
            subtitle: '世界期待着您的下一部杰作。',
            stats: {
                articles: '已发布文章',
                views: '总浏览量',
                tags: '活跃标签'
            },
            quick_actions: {
                title: '快捷操作',
                create_article: '撰写文章',
                settings: '系统设置'
            }
        },
        articles: {
            title: '文章管理',
            create: '新建文章',
            edit: '保存',
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
        tags: {
            title: '标签管理',
            name: '名称',
            rename: '重命名标签',
            delete: '删除标签',
            confirm_delete: '确定要删除这个标签吗？'
        },
        settings: {
            title: '博客设置',
            save: '保存更改',
            success: '设置已更新',
            sections: {
                general: '基本信息',
                author: '作者档案',
                social: '社交媒体',
                display: '显示选项',
                security: '账户安全'
            },
            security: {
                old_password: '当前密码',
                new_password: '新密码',
                confirm_password: '确认新密码',
                save_btn: '更新密码',
                success: '密码修改成功',
                error_mismatch: '两次输入的密码不一致',
                change_username: '修改用户名',
                new_username: '新用户名',
                current_password: '当前密码',
                update_username_btn: '更新用户名',
                username_success: '用户名已更新，请重新登录。'
            }
        }
    }
};
