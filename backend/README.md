# Spring Boot Blog Project

这是一个基于 Spring Boot 开发的简单博客系统，支持 JWT 认证、文章管理等功能。

## 功能特性

- **用户认证**: 使用 JWT (JSON Web Tokens) 进行安全认证。
- **数据持久化**: 使用 Spring Data JPA 和 MySQL。
- **数据库迁移**: 集成 Flyway 进行数据库版本控制。
- **API 文档**: 自动生成 OpenAPI (Swagger) 文档。

## 快速开始

### 1. 环境准备

- Java 17+
- Maven 3.x
- MySQL 8.x

### 2. 配置与密钥

为了安全起见，本项目的敏感配置（如数据库密码、JWT 密钥）不包含在代码库中。你需要创建一个本地配置文件。

1. 进入 `src/main/resources` 目录。
2. 复制 `secrets.json.example` 并重命名为 `secrets.json`。
3. 编辑 `secrets.json`，填入你的真实配置：

```json
{
  "db.username": "root",
  "db.password": "你的数据库密码",
  "jwt.secret": "请使用一个足够长且随机的字符串作为你的JWT密钥"
}
```

> [!IMPORTANT]
> `secrets.json` 已被配置为忽略提交到 Git，请确保不要将含有真实密钥的此文件意外提交。

### 3. 运行项目

在项目根目录下，使用 Maven 运行：

```bash
# Windows
./mvnw spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

或者使用你的 IDE (IntelliJ IDEA / Eclipse) 导入项目并直接运行 `BlogApplication` 类。

### 4. 访问

- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **API Docs**: [http://localhost:8080/api-docs](http://localhost:8080/api-docs)

## 贡献

欢迎提交 Issue 和 Pull Request！
