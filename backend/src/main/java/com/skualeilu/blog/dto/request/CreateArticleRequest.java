package com.skualeilu.blog.dto.request;

import jakarta.validation.constraints.NotEmpty;
import java.util.Set;

public class CreateArticleRequest {

    @NotEmpty(message = "Title cannot be empty")
    private String title;

    private String content;

    private Set<String> tags;

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public Set<String> getTags() { return tags; }
    public void setTags(Set<String> tags) { this.tags = tags; }
}