package com.skualeilu.blog.controller;

import com.skualeilu.blog.dto.request.CreateArticleRequest;
import com.skualeilu.blog.dto.response.ArticleResponse;
import com.skualeilu.blog.dto.response.ArticleSummaryResponse;
import com.skualeilu.blog.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.skualeilu.blog.dto.request.UpdateArticleRequest;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // --- Existing Endpoints ---

    @PostMapping
    public ResponseEntity<ArticleResponse> createArticle(@Valid @RequestBody CreateArticleRequest request) {
        ArticleResponse article = articleService.createArticle(request);
        return new ResponseEntity<>(article, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getArticleById(@PathVariable Long id) {
        ArticleResponse article = articleService.getArticleById(id);
        return ResponseEntity.ok(article);
    }

    @GetMapping
    public ResponseEntity<Page<ArticleSummaryResponse>> getAllArticles(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ArticleSummaryResponse> articles = articleService.getAllArticles(pageable);
        return ResponseEntity.ok(articles);
    }
    // 在 createArticle 方法之后，getArticleById 方法之前，插入这个新方法
    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponse> updateArticle(
            @PathVariable Long id,
            @Valid @RequestBody UpdateArticleRequest request) {

        ArticleResponse updatedArticle = articleService.updateArticle(id, request);
        return ResponseEntity.ok(updatedArticle);
    }
    // --- New Endpoints for Content Discovery ---

    @GetMapping("/featured")
    public ResponseEntity<List<ArticleSummaryResponse>> getFeaturedArticles() {
        List<ArticleSummaryResponse> articles = articleService.getFeaturedArticles();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/trending")
    public ResponseEntity<List<ArticleSummaryResponse>> getTrendingArticles(@RequestParam(defaultValue = "5") int limit) {
        List<ArticleSummaryResponse> articles = articleService.getTrendingArticles(limit);
        return ResponseEntity.ok(articles);
    }

    // 在 ArticleController 类的内部，添加这个新方法
    @PutMapping("/{id}/feature")
    public ResponseEntity<ArticleResponse> setArticleFeatured(
            @PathVariable Long id,
            @RequestParam boolean featured) {

        ArticleResponse updatedArticle = articleService.setFeaturedStatus(id, featured);
        return ResponseEntity.ok(updatedArticle);
    }
    @GetMapping("/search")
    public ResponseEntity<Page<ArticleSummaryResponse>> searchArticles(
            @RequestParam String q,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ArticleSummaryResponse> articles = articleService.searchArticles(q, pageable);
        return ResponseEntity.ok(articles);
    }

    // 在 ArticleController 类的内部，添加这个新方法
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        // 对于DELETE操作，通常返回 204 No Content 表示成功，且响应体为空
        return ResponseEntity.noContent().build();
    }
}