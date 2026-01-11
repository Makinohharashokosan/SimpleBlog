package com.skualeilu.blog.controller;

import com.skualeilu.blog.dto.response.ArchiveCountResponse;
import com.skualeilu.blog.dto.response.ArticleSummaryResponse;
import com.skualeilu.blog.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ContentDiscoveryController {

    private final ArticleService articleService;

    public ContentDiscoveryController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/archives")
    public ResponseEntity<List<ArchiveCountResponse>> getArchives() {
        return ResponseEntity.ok(articleService.getArchiveCounts());
    }

    @GetMapping("/articles/{id}/related")
    public ResponseEntity<List<ArticleSummaryResponse>> getRelatedArticles(
            @PathVariable Long id,
            @RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(articleService.getRelatedArticles(id, limit));
    }
}