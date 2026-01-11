package com.skualeilu.blog.dto.request;

public class SocialLinkRequest {
    private String platform;
    private String url;

    // Getters and Setters
    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
}