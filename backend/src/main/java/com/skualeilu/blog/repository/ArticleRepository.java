package com.skualeilu.blog.repository;

import com.skualeilu.blog.dto.response.ArchiveCountResponse; // <-- **补上了这个！**
import com.skualeilu.blog.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set; // <-- **补上了这个！**

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Page<Article> findByTags_Name(String tagName, Pageable pageable);

    List<Article> findByIsFeaturedTrueOrderByCreatedAtDesc();

    List<Article> findTopByOrderByViewCountDesc(Pageable pageable);

    @Query("SELECT a FROM Article a WHERE a.title LIKE %:keyword% OR a.content LIKE %:keyword%")
    Page<Article> searchByTitleOrContent(@Param("keyword") String keyword, Pageable pageable);

    // --- 新增的功能方法 ---

    @Query("SELECT new com.skualeilu.blog.dto.response.ArchiveCountResponse(YEAR(a.createdAt), MONTH(a.createdAt), COUNT(a.id)) " +
            "FROM Article a " +
            "GROUP BY YEAR(a.createdAt), MONTH(a.createdAt) " +
            "ORDER BY YEAR(a.createdAt) DESC, MONTH(a.createdAt) DESC")
    List<ArchiveCountResponse> findArchiveCounts();

    @Query("SELECT a FROM Article a JOIN a.tags t WHERE t.id IN :tagIds AND a.id != :articleId GROUP BY a.id ORDER BY COUNT(t.id) DESC")
    List<Article> findRelatedArticles(@Param("tagIds") Set<Long> tagIds, @Param("articleId") Long articleId, Pageable pageable);

    @Query("SELECT SUM(a.viewCount) FROM Article a")
    Long sumViewCount();
}