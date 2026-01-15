package com.skualeilu.blog.service;

import com.skualeilu.blog.dto.request.CreateArticleRequest;
import com.skualeilu.blog.dto.response.ArticleResponse;
import com.skualeilu.blog.entity.Article;
import com.skualeilu.blog.entity.Tag;
import com.skualeilu.blog.repository.ArticleRepository;
import com.skualeilu.blog.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

// @SpringBootTest 会加载完整的 Spring 应用上下文，适合于测试跨越多层的场景
@SpringBootTest
@Transactional // 让每个测试方法都在事务中运行，测试结束后自动回滚，保持数据库清洁
public class ArticleServiceTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TagRepository tagRepository;

    @Test
    void getArticleById_shouldIncrementViewCount() {
        // Arrange
        Article article = new Article();
        article.setTitle("Test Title");
        article.setViewCount(5); // 初始阅读量
        Article savedArticle = articleRepository.save(article);

        // Act
        ArticleResponse response = articleService.getArticleById(savedArticle.getId());

        // Assert
        assertThat(response.getViewCount()).isEqualTo(6);

        // 再次从数据库确认
        Article articleInDb = articleRepository.findById(savedArticle.getId()).get();
        assertThat(articleInDb.getViewCount()).isEqualTo(6);
    }

    @Test
    void createArticle_withNewAndExistingTags_shouldWorkCorrectly() {
        // Arrange
        // 先创建一个已存在的标签
        Tag existingTag = tagRepository.save(new Tag("Java"));

        CreateArticleRequest request = new CreateArticleRequest();
        request.setTitle("New Article with Tags");
        request.setContent("Content here.");
        request.setTags(Set.of("Java", "Spring Boot")); // 一个已存在，一个新标签

        // Act
        ArticleResponse response = articleService.createArticle(request);

        // Assert
        assertThat(response.getId()).isNotNull();
        assertThat(response.getTitle()).isEqualTo("New Article with Tags");
        assertThat(response.getTags()).hasSize(2);
        assertThat(response.getTags().stream().map(t -> t.getName())).containsExactlyInAnyOrder("Java", "Spring Boot");

        // 确认数据库状态
        long tagCount = tagRepository.count();
        // 初始有一个 "Java"，新创建了一个 "Spring Boot"
        assertThat(tagCount).isEqualTo(2);

        Article articleInDb = articleRepository.findById(response.getId()).get();
        assertThat(articleInDb.getTags()).hasSize(2);
    }
}