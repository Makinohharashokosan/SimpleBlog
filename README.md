# 🎨 ArtBlog - 现代化的前后端分离博客系统

![Java](https://img.shields.io/badge/Java-17-orange) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-green) ![Vue](https://img.shields.io/badge/vue-3.x-brightgreen) ![License](https://img.shields.io/badge/license-MIT-blue.svg)

**ArtBlog** 是一个兼具强大功能与前卫设计美学的现代化博客系统。它由一个基于 **Spring Boot 3** 的稳健后端和一个基于 **Vue 3** 的艺术感前端构成，旨在为开发者和写作者提供极致的创作与阅读体验。

该项目不仅是一个功能完整的博客平台，也是一个优秀的全栈开发实践案例，涵盖了从用户认证、内容管理到数据库迁移的完整流程。

---

## ✨ 核心特性

**后端 (Spring Boot)**
*   **🔐 安全与认证**: 基于 JWT 的无状态认证机制，支持在线修改密码和用户名，并能在系统首次启动时自动创建默认管理员。
*   **📄 强大的内容管理**: 提供文章、标签的增删改查，支持 Markdown，并能标记“特色文章”及查询“热门”文章。
*   **🔍 内容发现与统计**: 支持按年月归档、基于标签的相关文章推荐，以及全站数据统计。
*   **⚙️ 灵活的系统设置**: 可动态配置站点信息，并通过 Flyway 自动化管理数据库版本。

**前端 (Vue)**
*   🎨 **前卫美学设计**: 融合玻璃拟态 (Glassmorphism)、平滑动画和艺术字体，提供高标准的视觉体验。
*   🌓 **完美深色模式**: 支持 Light/Dark 主题一键切换，并能自动适配系统设置。
*   🌏 **国际化 (i18n)**: 内置中英双语支持，易于扩展。
*   🧘 **禅模式编辑器**: 为创作者设计的极简 Markdown 编辑器，提供沉浸式的写作体验。
*   📱 **完全响应式**: 适配从移动端到桌面端的各类设备。

---

## 🛠️ 技术栈概览

| 类别 | 技术 |
| :--- | :--- |
| **后端** | Spring Boot 3, Java 17, Spring Data JPA, MySQL 8, Flyway, MapStruct, SpringDoc |
| **前端** | Vue 3, Pinia, Vue Router 4, Axios, Vue I18n, 原生 CSS 变量 |
| **通用** | JWT, Markdown, RESTful API |

---

## 🚀 快速开始

本项目为前后端分离架构，需要分别启动后端和前端服务。

### 1. 启动后端服务
1.  **环境准备**: 确保已安装 Java 17+, Maven 3.x, MySQL 8.x。
2.  **克隆后端仓库**:
    ```bash
    # 替换为您的后端仓库地址
    git clone https://github.com/your-username/spring-boot-blog-backend.git
    cd spring-boot-blog-backend
    ```
3.  **配置与运行**:
    详细的数据库和 JWT 密钥配置，请参考 `backend/README.md`。配置完成后，使用以下命令启动：
    ```bash
    ./mvnw spring-boot:run
    ```
    服务将在 `http://localhost:8080` 启动。

### 2. 启动前端服务
1.  **环境准备**: 确保已安装 Node.js (LTS 版本)。
2.  **克隆前端仓库**:
    ```bash
    # 替换为您的前端仓库地址
    git clone https://github.com/your-username/vue-art-blog-template.git
    cd vue-art-blog-template
    ```
3.  **安装与运行**:
    ```bash
    # 安装依赖
    npm install
    
    # 启动开发服务器
    npm run serve
    ```
    前端应用将在 `http://localhost:8081` 启动，并自动代理 API 请求到后端。

### 3. 访问您的博客
*   **博客前台**: [http://localhost:8081](http://localhost:8081)
*   **API 文档 (Swagger)**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

> [!NOTE]
> 详细的数据库初始化、环境变量配置、API 文档以及文章内嵌规范，请分别查阅前端和后端的 `README.md` 文件。

---

## 🤝 贡献

欢迎对本项目提交 Issue 或 Pull Request，我们期待您的宝贵意见和贡献！

1.  Fork 本仓库
2.  创建您的特性分支 (`git checkout -b feature/AmazingFeature`)
3.  提交您的修改 (`git commit -m 'Add some AmazingFeature'`)
4.  推送到分支 (`git push origin feature/AmazingFeature`)
5.  开启 Pull Request

---

## 📄 开源协议

本项目基于 [MIT](LICENSE) 协议开源。
