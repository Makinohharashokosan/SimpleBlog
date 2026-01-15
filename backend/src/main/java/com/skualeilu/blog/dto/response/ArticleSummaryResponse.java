package com.skualeilu.blog.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

public class ArticleSummaryResponse {
    private Long id;
    private String title;
    // 注意：这里没有 content 字段
    private LocalDateTime createdAt;
    private boolean isFeatured;
    private long viewCount;
    private Set<TagResponse> tags;

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public boolean isFeatured() { return isFeatured; }
    public void setFeatured(boolean featured) { isFeatured = featured; }
    public long getViewCount() { return viewCount; }
    public void setViewCount(long viewCount) { this.viewCount = viewCount; }
    public Set<TagResponse> getTags() { return tags; }
    public void setTags(Set<TagResponse> tags) { this.tags = tags; }
}