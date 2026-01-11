package com.skualeilu.blog.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tag")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    // mappedBy 指示 'tags' 字段是关系的反向端（由 Article 实体维护）
    @ManyToMany(mappedBy = "tags")
    private Set<Article> articles = new HashSet<>();


    // --- Constructors ---

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }
}