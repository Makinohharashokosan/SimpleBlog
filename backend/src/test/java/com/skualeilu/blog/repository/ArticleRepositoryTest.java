package com.skualeilu.blog.repository;

import com.skualeilu.blog.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
// **↓↓↓ 这是唯一的修改点 ↓↓↓**
// 告诉 Spring Boot 在测试时不要替换我们配置的数据源，而是直接使用它
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ArticleRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ArticleRepository articleRepository;

    @Test
    public void whenSaveArticle_thenItShouldBePersisted() {
        // 1. 准备数据 (Arrange)
        Article newArticle = new Article();
        newArticle.setTitle("My First Test Article");
        newArticle.setContent("This is the content of the test article.");

        // 2. 执行操作 (Act)
        Article savedArticle = articleRepository.save(newArticle);

        // 3. 断言验证 (Assert)
        assertThat(savedArticle.getId()).isNotNull();
        assertThat(savedArticle.getId()).isGreaterThan(0L);

        Article foundArticle = entityManager.find(Article.class, savedArticle.getId());
        assertThat(foundArticle).isNotNull();
        assertThat(foundArticle.getTitle()).isEqualTo("My First Test Article");
    }
}