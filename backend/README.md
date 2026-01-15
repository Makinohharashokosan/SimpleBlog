# 📝 Spring Boot Blog Project

![Java](https://img.shields.io/badge/Java-17-orange) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.2-green) ![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)

这是一个基于 **Spring Boot 3** 开发的现代化博客后端系统。它不仅仅是一个简单的 CRUD Demo，还包含了完整的用户认证流程、数据库版本管理、以及内容发现等实用功能。

## ✨ 功能特性 (Features)

### 🔐 安全与认证 (Security)
- **JWT 认证**: 无状态的 JSON Web Token 认证机制。
- **动态用户管理**: 
  - 支持 **数据库级** 用户存储 (`users` 表)。
  - 支持在线 **修改密码** 和 **修改用户名** (`/api/auth/change-*`)。
- **自动初始化**: 系统首次启动时，若检测到数据库无用户，会自动根据配置文件创建默认管理员。

### 📄 内容管理 (Content)
- **文章系统**: 文章的增删改查 (CRUD)，支持 Markdown 内容。
- **标签系统**: 灵活的标签关联，支持按标签筛选文章。
- **特色与趋势**: 支持标记“特色文章”及查询“热门/趋势”文章。

### 🔍 发现与统计 (Discovery & Stats)
- **归档**: 按年月统计文章归档。
- **相关推荐**: 基于标签算法推荐相关文章。
- **站点统计**: 全局文章数、浏览量统计。

### ⚙️ 系统设置 (System)
- **全局配置**: 动态配置站点标题、描述及社交链接 (Github/Twitter等)。
- **Flyway 迁移**: 自动化的数据库脚本版本控制，开箱即用。

---

## 🛠️ 技术栈 (Tech Stack)

- **核心框架**: Spring Boot 3.2.2
- **语言**: Java 17
- **持久层**: Spring Data JPA (Hibernate)
- **数据库**: MySQL 8.0
- **数据库迁移**: Flyway
- **对象映射**: MapStruct
- **API 文档**: SpringDoc OpenAPI (Swagger 3)
- **工具库**: Lombok, JJWT

---

## 🚀 快速开始 (Quick Start)

### 1. 环境准备
- Java 17+
- Maven 3.x
- MySQL 8.x

### 2. 配置密钥 (`secrets.json`)
本项目采用 **配置与密钥分离** 的策略。敏感信息存储在 `src/main/resources/secrets.json` 中，该文件已被 Git 忽略。

1. 在 `src/main/resources` 目录下创建 `secrets.json` 文件。
2. 填入如下内容 (请修改为你的真实密码):

```json
{
  "db.username": "root",
  "db.password": "your_db_password",
  "jwt.secret": "your_very_long_secure_random_string_key_at_least_32_bytes",
  "admin.password.hash": "$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG"
}
```

> [!NOTE]
> `admin.password.hash` 仅用于**首次启动**时创建默认管理员。
> 默认的哈希对应的密码是: `password`

### 3. 运行项目
```bash
./mvnw spring-boot:run
```
系统启动后，Flyway 会自动执行 SQL 脚本创建表结构 (`users`, `article`, `tag` 等)。

---

## 📚 API 文档

我们提供了非常详细的 API 接口说明文档，包含所有接口的请求/响应示例。

👉 [**查看完整 API 文档 (API_DOCUMENTATION.md)**](API_DOCUMENTATION.md)

或者启动项目后访问在线 Swagger UI:
- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🤝 贡献
欢迎提交 Issue 或 Pull Request 来改进这个项目！
