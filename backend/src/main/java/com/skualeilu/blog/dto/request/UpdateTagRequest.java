package com.skualeilu.blog.dto.request;
import jakarta.validation.constraints.NotEmpty;
public class UpdateTagRequest {
    @NotEmpty
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}