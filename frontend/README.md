# Vue Art Blog Template

![License](https://img.shields.io/badge/license-MIT-blue.svg) ![Vue](https://img.shields.io/badge/vue-3.x-green.svg)

[English](./README_EN.md) | [中文说明](./README.md)

一个极具艺术感、高美学标准的 Vue 3 博客模板。
融合了 **玻璃拟态 (Glassmorphism)**、**平滑动画 (Smooth Animations)** 与 **禅模式 (Zen Mode)** 编辑体验。

## ✨ 特性 (Features)

- 🎨 **前卫设计**: 全局采用高对比度排版、PinYin Script 艺术字体与 Cinzel 标题，配合精致的玻璃拟态背景。
- 🌓 **深色模式**: 完美适配的 Light/Dark 主题切换，支持系统自动检测。
- 🌏 **国际化 (i18n)**: 内置中英双语切换，支持扩展更多语言。
- 🧘 **禅模式编辑器**: 专为写作设计的极简后台编辑器，支持 Markdown 实时预览。
- 📱 **完全响应式**: 移动端、平板、桌面端全覆盖适配。
- ⚡ **高性能**: 基于 Vue 3 + Pinia + Vue Router 4 构建，极速加载。

## 🛠️ 技术栈 (Tech Stack)

- **Framework**: Vue 3 (Composition API)
- **State Management**: Pinia
- **Routing**: Vue Router 4
- **Styling**: Native CSS Variables (No heavy frameworks), Glassmorphism techniques
- **HTTP Client**: Axios
- **i18n**: Vue I18n

## 🚀 快速开始 (Getting Started)

### 1. 克隆项目
```bash
git clone https://github.com/makinohharashoko/SimpleBlog.git
cd SimpleBlog/frontend
```

### 2. 安装依赖
```bash
npm install
```

### 3. 运行开发服务器
```bash
npm run serve
```
访问 `http://localhost:8080` 查看效果。

### 4. 编译生产版本
```bash
npm run build
```

## 📂 项目结构 (Project Structure)

```
src/
├── api/             # API 请求封装
├── assets/          # 静态资源 (图片, 字体)
├── components/      # 公共组件 (ThemeSwitcher, ArticleCards...)
├── composables/     # 组合式函数 (useTheme...)
├── layouts/         # 布局组件 (AdminLayout, PublicLayout)
├── locales/         # 国际化语言包
├── router/          # 路由配置
├── stores/          # Pinia 状态管理
├── views/           # 页面视图
│   ├── admin/       # 后台管理页面
│   └── public/      # 前台展示页面
└── App.vue
```

## 🤝 贡献 (Contributing)

欢迎提交 Issue 和 Pull Request！
1. Fork 本仓库
2. 创建你的特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交你的修改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 📄 开源协议 (License)

本项目基于 [MIT](LICENSE) 协议开源。
