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
git clone https://github.com/your-username/vue-art-blog-template.git
cd vue-art-blog-template
```

### 2. 安装依赖
```bash
npm install
```

### 3. 运行开发服务器
```bash
npm run serve
```
访问 `http://localhost:8081` 查看效果。

### 4. 编译生产版本
```bash
npm run build
```

## ⚙️ 配置指南 (Configuration)

### 环境变量
项目根目录下支持 `.env` 文件配置。

| 变量名 | 描述 | 默认值 |
| :--- | :--- | :--- |
| `VUE_APP_API_BASE_URL` | 后端 API 接口地址 | `/api` |
| `VUE_APP_TITLE` | 网站标题 | `Vue Art Blog` |

### 国际化 (i18n)
在 `src/locales/` 目录下添加或修改 `zh.js` / `en.js` 即可更新翻译文本。
若要添加新语言，请在 `src/locales/` 新建对应的 `.js` 文件，并在 `src/i18n.js` 中引入。

## 📂 项目结构 (Project Structure)

```
src/
├── api/             # API 请求封装
├── assets/          # 静态资源 (图片, 字体)
├── components/      # 组件库
│   ├── base/        # 基础组件 (BaseButton, BasePagination...)
│   ├── common/      # 通用业务组件 (ArticleSummaryCard, ThemeSwitcher...)
│   └── public/      # 前台特定组件
├── composables/     # 组合式函数 (useTheme...)
├── layouts/         # 布局组件 (AdminLayout, PublicLayout)
├── locales/         # 国际化语言包 (en.js, zh.js)
├── router/          # 路由配置
├── stores/          # Pinia 状态管理
├── views/           # 页面视图
│   ├── admin/       # 后台管理页面
│   └── public/      # 前台展示页面
├── i18n.js          # I18n 配置入口
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

## 📝 内嵌兼容文章规范

为了确保内嵌文章内容（HTML）能完美适配博客的 **日间/夜间 (Light/Dark)** 主题切换，文章的具体内容（HTML/CSS/JS）需要遵循以下规范。

### 核心机制

博客系统会自动为 iframe 注入基础样式和 CSS 变量。文章内容 **不需要** 包含 `<html>`, `<head>`, 或 `<body>` 标签，只需提供 body 内部的内容片段即可。主题切换是通过动态在 iframe 的 `html` 标签上切换 `.dark` 类来实现的。

### 1. 推荐使用的 CSS 变量

为了保持颜色一致性，请在自定义样式中优先使用以下 CSS 变量，而不是硬编码颜色值（如 `#000` 或 `white`）。

| 变量名 | 描述 | 日间值 (示例) | 夜间值 (示例) |
| :--- | :--- | :--- | :--- |
| `--color-main-bg` | 页面背景色 | `#FDFDFD` | `#111111` |
| `--color-primary-text` | 主要文字颜色 | `#333333` | `#CCCCCC` |
| `--color-secondary-structure` | 次要文字/边框色 | `#888888` | `#888888` |
| `--color-functional-element` | 强调色/链接色 | `#000000` | `#FFFFFF` |

### 2. HTML 结构要求

*   **纯语义化标签**: 只要使用标准的 `<p>`, `<h1>`~`<h6>`, `<ul>`, `<ol>`, `<blockquote>`, `<pre>`, `<code>` 等标签，系统会自动应用适配主题的样式。
*   **避免内联样式**: 尽量**不要**使用 `style="color: black"` 或 `style="background: white"`。这会导致夜间模式下文字看不清或背景刺眼。
*   **图片**: 图片默认 `max-width: 100%`。如果包含透明背景的 PNG/SVG，请确保它们在深色背景下也能看清（或者提供背景色）。

### 3. 自定义 CSS 规范

如果您需要在文章中嵌入 `<style>` 块来写复杂的布局（如卡片、特殊的按钮）：

#### ❌ 错误示范 (硬编码颜色)
```html
<style>
  .my-card {
    background-color: white; /* 夜间模式会非常刺眼 */
    color: black;            /* 夜间模式可能看不清 */
    border: 1px solid #ddd;
  }
</style>
<div class="my-card">内容</div>
```

#### ✅ 正确示范 (使用变量)
```html
<style>
  .my-card {
    background-color: var(--color-main-bg); /* 跟随主题 */
    color: var(--color-primary-text);       /* 跟随主题 */
    border: 1px solid var(--color-secondary-structure);
    padding: 20px;
    border-radius: 8px;
    /* 可以添加半透明背景来增加层次感 */
    background-color: rgba(125, 125, 125, 0.05); 
  }
</style>
<div class="my-card">内容</div>
```

### 4. JavaScript 规范

*   **DOM 操作**: 可以自由操作 DOM。
*   **样式修改**: 如果 JS 需要设置样式，同样建议设置 CSS 变量或添加/移除 class，而不是直接设置 `.style.color = 'black'`。
*   **主题感知**: 如果 JS 逻辑依赖当前主题（例如绘制 Canvas 图表），可以通过检测 `html` 标签是否有 `.dark` 类来判断，或者使用 `window.matchMedia('(prefers-color-scheme: dark)')` (但需注意博客的主题切换可能手动覆盖了系统偏好，最准确的是观察 `document.documentElement.classList.contains('dark')`)。

