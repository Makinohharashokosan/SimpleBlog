package com.skualeilu.blog.dto.response;
public class StatsResponse {
    private long totalArticles;
    private long totalTags;
    private long totalViews;
    // Getters & Setters
    public long getTotalArticles() { return totalArticles; }
    public void setTotalArticles(long totalArticles) { this.totalArticles = totalArticles; }
    public long getTotalTags() { return totalTags; }
    public void setTotalTags(long totalTags) { this.totalTags = totalTags; }
    public long getTotalViews() { return totalViews; }
    public void setTotalViews(long totalViews) { this.totalViews = totalViews; }
}