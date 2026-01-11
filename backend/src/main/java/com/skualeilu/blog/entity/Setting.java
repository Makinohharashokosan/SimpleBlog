package com.skualeilu.blog.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "setting")
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "setting_key", nullable = false, unique = true)
    private String key;

    @Lob
    // 明确指定列定义为 'TEXT'，以匹配 V1__Initial_Schema.sql 中的定义
    @Column(name = "setting_value", columnDefinition = "TEXT")
    private String value;

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}