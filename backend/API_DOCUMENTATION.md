# API Documentation

## Base URL
`http://localhost:8080/api`

## Authentication (`/auth`)

| Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| `POST` | `/auth/login` | User login. Returns JWT token. | No |
| `POST` | `/auth/change-password` | Change current user's password. | **Yes** |
| `POST` | `/auth/change-username` | Change current user's username. | **Yes** |

**Request Body (Login):**
```json
{
  "username": "admin",
  "password": "your_password"
}
```

**Request Body (Change Password):**
```json
{
  "oldPassword": "current_password",
  "newPassword": "new_secure_password"
}
```

**Request Body (Change Username):**
```json
{
  "newUsername": "new_username",
  "password": "current_password_for_verification"
}
```

---

## Articles (`/articles`)

| Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| `GET` | `/articles` | Get all articles (Paginated). Support sorting. | No |
| `POST` | `/articles` | Create a new article. | **Yes** |
| `GET` | `/articles/{id}` | Get specific article by ID. | No |
| `PUT` | `/articles/{id}` | Update an existing article. | **Yes** |
| `DELETE` | `/articles/{id}` | Delete an article. | **Yes** |
| `GET` | `/articles/featured` | Get distinct featured articles. | No |
| `PUT` | `/articles/{id}/feature` | Set article featured status (`?featured=true/false`). | **Yes** |
| `GET` | `/articles/trending` | Get trending articles. Query param `limit` (default 5). | No |
| `GET` | `/articles/search` | Search articles. Query param `q`. | No |

**Request Body (Create/Update Article):**
```json
{
  "title": "My Article Title",
  "content": "# Markdown Content...",
  "summary": "Short summary...",
  "coverImage": "https://example.com/image.jpg",
  "tagIds": [1, 2]
}
```

**Pagination**: Standard Spring Data pagination (e.g., `?page=0&size=10&sort=createdAt,desc`).

---

## Content Discovery (`/`)

| Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| `GET` | `/archives` | Get article counts grouped by month (YYYY-MM). | No |
| `GET` | `/articles/{id}/related` | Get related articles based on tags. Query param `limit`. | No |

---

## Tags (`/tags`)

| Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| `GET` | `/tags` | Get all available tags. | No |
| `GET` | `/tags/{tagName}/articles` | Get articles associated with a specific tag (Paginated). | No |
| `PUT` | `/tags/{id}` | Update a tag. | **Yes** |
| `DELETE` | `/tags/{id}` | Delete a tag. | **Yes** |

**Request Body (Update Tag):**
```json
{
  "name": "New Tag Name"
}
```

---

## Settings (`/settings`)

| Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| `GET` | `/settings` | Get global blog settings (title, description, social links). | No |
| `PUT` | `/settings` | Update global settings. | **Yes** |

**Request Body (Update Settings):**
```json
{
  "siteTitle": "My Awesome Blog",
  "siteDescription": "Tech and Life",
  "socialLinks": [
    { "platform": "GitHub", "url": "https://github.com/user" },
    { "platform": "Twitter", "url": "https://twitter.com/user" }
  ]
}
```

---

## Statistics (`/stats`)

| Method | Endpoint | Description | Auth Required |
| :--- | :--- | :--- | :--- |
| `GET` | `/stats` | Get site statistics (article count, view count, etc.). | No |

---

## Response Formats

### Standard Wrapper
Depending on the endpoint, data is returned directly or wrapped in a standard structure.

### Pagination Wrapper (`Page<T>`)
```json
{
  "content": [ ... ],
  "pageable": { ... },
  "totalElements": 100,
  "totalPages": 10,
  "last": false,
  ...
}
```

### Error Response
```json
{
  "timestamp": "2024-01-01T12:00:00",
  "status": 4xx/5xx,
  "error": "Error Name",
  "message": "Detailed error message",
  "path": "/api/..."
}
```
