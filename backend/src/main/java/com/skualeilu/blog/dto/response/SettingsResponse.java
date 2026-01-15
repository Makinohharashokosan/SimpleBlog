package com.skualeilu.blog.dto.response;

import java.util.List;

// This DTO will aggregate all settings into one object for the API response.
public class SettingsResponse {

    // Core Identity
    private String blogTitle;
    private String blogSubtitle;
    private String logoUrl;
    private String faviconUrl;
    private String copyrightText;

    // Author Profile
    private String authorName;
    private String authorBio;
    private String authorAvatarUrl;

    // Social Links
    private List<SocialLinkResponse> socialLinks;

    // Behavior Control
    private Integer articlesPerPage;
    private Integer featuredArticlesCount;
    private Integer trendingArticlesCount;

    // --- Getters and Setters ---
    public String getBlogTitle() { return blogTitle; }
    public void setBlogTitle(String blogTitle) { this.blogTitle = blogTitle; }
    public String getBlogSubtitle() { return blogSubtitle; }
    public void setBlogSubtitle(String blogSubtitle) { this.blogSubtitle = blogSubtitle; }
    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }
    public String getFaviconUrl() { return faviconUrl; }
    public void setFaviconUrl(String faviconUrl) { this.faviconUrl = faviconUrl; }
    public String getCopyrightText() { return copyrightText; }
    public void setCopyrightText(String copyrightText) { this.copyrightText = copyrightText; }
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
    public String getAuthorBio() { return authorBio; }
    public void setAuthorBio(String authorBio) { this.authorBio = authorBio; }
    public String getAuthorAvatarUrl() { return authorAvatarUrl; }
    public void setAuthorAvatarUrl(String authorAvatarUrl) { this.authorAvatarUrl = authorAvatarUrl; }
    public List<SocialLinkResponse> getSocialLinks() { return socialLinks; }
    public void setSocialLinks(List<SocialLinkResponse> socialLinks) { this.socialLinks = socialLinks; }
    public Integer getArticlesPerPage() { return articlesPerPage; }
    public void setArticlesPerPage(Integer articlesPerPage) { this.articlesPerPage = articlesPerPage; }
    public Integer getFeaturedArticlesCount() { return featuredArticlesCount; }
    public void setFeaturedArticlesCount(Integer featuredArticlesCount) { this.featuredArticlesCount = featuredArticlesCount; }
    public Integer getTrendingArticlesCount() { return trendingArticlesCount; }
    public void setTrendingArticlesCount(Integer trendingArticlesCount) { this.trendingArticlesCount = trendingArticlesCount; }
}