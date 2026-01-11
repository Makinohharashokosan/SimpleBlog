package com.skualeilu.blog.controller;

import com.skualeilu.blog.dto.response.ArticleSummaryResponse;
import com.skualeilu.blog.dto.response.TagResponse;
import com.skualeilu.blog.service.ArticleService;
import com.skualeilu.blog.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.skualeilu.blog.dto.request.UpdateTagRequest; // 新增
import jakarta.validation.Valid; // 新增
import org.springframework.web.bind.annotation.*; // 修改

import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {
    private final TagService tagService;
    private final ArticleService articleService;

    public TagController(TagService tagService, ArticleService articleService) {
        this.tagService = tagService;
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags() {
        return ResponseEntity.ok(tagService.getAllTags());
    }

    @GetMapping("/{tagName}/articles")
    public ResponseEntity<Page<ArticleSummaryResponse>> getArticlesByTag(
            @PathVariable String tagName,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(articleService.getArticlesByTagName(tagName, pageable));
    }
    // 在 TagController 类的内部，添加这些新方法
    @PutMapping("/{id}")
    public ResponseEntity<TagResponse> updateTag(@PathVariable Long id, @Valid @RequestBody UpdateTagRequest request) {
        return ResponseEntity.ok(tagService.updateTag(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}