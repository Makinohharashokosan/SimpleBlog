---

# 现代博客系统 (Full-Stack Blog Platform)

![Java](https://img.shields.io/badge/Java-17-blue.svg) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green.svg) ![Vue](https://img.shields.io/badge/vue-3.x-brightgreen.svg) ![License](https://img.shields.io/badge/license-MIT-blue.svg)

一个融合了强大后端与前卫前端的全栈博客项目。后端采用 **Spring Boot** 构建，提供安全、稳定的 API 服务；前端则基于 **Vue 3** 精心打造，追求极致的艺术感与用户体验。

## ✨ 项目亮点 (Highlights)

- **全栈架构**: 前后端分离设计，后端负责数据与逻辑，前端专注美学与交互，分工明确，易于维护。
- **安全可靠**: 后端集成 **JWT (JSON Web Tokens)** 进行身份认证，保障用户数据安全。
- **前卫设计**: 前端采用 **玻璃拟态 (Glassmorphism)** 风格，配合平滑动画与艺术字体，带来独特的视觉享受。
- **沉浸式写作**: 提供专为创作者设计的 **禅模式 (Zen Mode)** 编辑器，支持 Markdown 实时预览，让写作回归纯粹。
- **现代化技术栈**: 选用业界主流且先进的技术栈，如 Spring Boot 3, JPA, Vue 3, Pinia 等，确保项目的高性能与可扩展性。
- **开发者友好**: 后端自动生成 OpenAPI (Swagger) 文档，前端提供清晰的模块划分与详尽注释，方便快速上手与二次开发。

## 🛠️ 技术栈 (Tech Stack)

| 领域       | 技术                                        |
| ---------- | ------------------------------------------- |
| **后端**   | Java 17+, Spring Boot 3, Spring Data JPA, MySQL 8, Maven, Flyway, JWT |
| **前端**   | Vue 3 (Composition API), Pinia, Vue Router 4, Axios, Vue I18n, CSS Variables |
| **开发工具** | Git, IntelliJ IDEA / VS Code, npm          |

## 🚀 快速开始 (Getting Started)

本项目包含两个独立的部分：`backend` (后端服务) 和 `frontend` (前端应用)。请分别按照以下步骤启动。

### 1. 启动后端服务

1.  **环境准备**: 确保已安装 Java 17+, Maven 3.x, 和 MySQL 8.x。
2.  **配置**:
    *   进入 `backend/src/main/resources` 目录。
    *   复制 `secrets.json.example` 并重命名为 `secrets.json`。
    *   在 `secrets.json` 中填入你的数据库用户名、密码以及一个自定义的 JWT 密钥。
3.  **运行**: 在 `backend` 项目根目录下执行：
    ```bash
    ./mvnw spring-boot:run
    ```
4.  **API 文档**: 服务启动后，可在 `http://localhost:8080/swagger-ui.html` 查看所有 API。

### 2. 启动前端应用

1.  **环境准备**: 确保已安装 Node.js (LTS 版本)。
2.  **安装依赖**: 进入 `frontend` 目录并执行：
    ```bash
    npm install
    ```
3.  **运行**: 继续在 `frontend` 目录下执行：
    ```bash
    npm run serve
    ```
4.  **访问**: 打开浏览器并访问 `http://localhost:8081` (或其他终端提示的端口) 查看博客。

## 📂 项目结构 (Project Structure)

```
.
├── backend/      # Spring Boot 后端项目
│   ├── src/
│   └── pom.xml
└── frontend/     # Vue 3 前端项目
    ├── src/
    └── package.json
```

> 每个子目录中都包含了更详细的 README 文件，介绍了其内部实现、配置和脚本等细节。

## 🤝 贡献 (Contributing)

我们非常欢迎任何形式的贡献，无论是报告问题 (Issue)还是提交功能增强 (Pull Request)。

1.  Fork 本仓库。
2.  创建你的特性分支 (`git checkout -b feature/YourAmazingFeature`)。
3.  提交你的修改 (`git commit -m 'Add some AmazingFeature'`)。
4.  推送到分支 (`git push origin feature/YourAmazingFeature`)。
5.  开启一个 Pull Request。

## 📄 开源协议 (License)

本项目基于 [MIT](LICENSE) 协议开源。
