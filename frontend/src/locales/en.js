export default {
    nav: {
        home: 'Home',
        featured: 'Featured',
        archive: 'Archive',
        tags: 'Tags',
        about: 'About',
        search: 'Search',
        admin: 'Admin',
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
        related_stories: 'Related Stories',
        views: '{count} Views',
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
            tags: 'Tags',
            settings: 'Settings',
            logout: 'Logout'
        },
        dashboard: {
            welcome: 'Welcome Back,',
            subtitle: 'The world awaits your next masterpiece.',
            stats: {
                articles: 'Articles Published',
                views: 'Total Views',
                tags: 'Active Tags'
            },
            quick_actions: {
                title: 'Quick Actions',
                create_article: 'Create Article',
                settings: 'Settings'
            }
        },
        articles: {
            title: 'Article Management',
            create: 'New Article',
            edit: 'Save',
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
        tags: {
            title: 'Tags Management',
            name: 'Name',
            rename: 'Rename Tag',
            delete: 'Delete Tag',
            confirm_delete: 'Are you sure you want to delete this tag?'
        },
        settings: {
            title: 'Blog Settings',
            save: 'Save Changes',
            success: 'Settings updated successfully.',
            sections: {
                general: 'General Info',
                author: 'Author Profile',
                social: 'Social Media',
                display: 'Display Options',
                security: 'Security' // Should check if this causes duplicate keys if strict
            },
            security: {
                old_password: 'Current Password',
                new_password: 'New Password',
                confirm_password: 'Confirm New Password',
                save_btn: 'Update Password',
                success: 'Password changed successfully',
                error_mismatch: 'Passwords do not match',
                change_username: 'Change Username',
                new_username: 'New Username',
                current_password: 'Current Password',
                update_username_btn: 'Update Username',
                username_success: 'Username updated. Please login again.'
            }
        }
    }
};
