package com.skualeilu.blog.service;

import com.skualeilu.blog.dto.request.UpdateArticleRequest;
import com.skualeilu.blog.dto.request.CreateArticleRequest;
import com.skualeilu.blog.dto.response.ArchiveCountResponse;
import com.skualeilu.blog.dto.response.ArticleResponse;
import com.skualeilu.blog.entity.Article;
import com.skualeilu.blog.entity.Tag;
import com.skualeilu.blog.exception.ResourceNotFoundException;
import com.skualeilu.blog.mapper.ArticleMapper;
import com.skualeilu.blog.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.skualeilu.blog.dto.response.ArticleSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.util.Set;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final TagService tagService;
    private final ArticleMapper articleMapper;

    public ArticleService(ArticleRepository articleRepository, TagService tagService, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.tagService = tagService;
        this.articleMapper = articleMapper;
    }

    // 新增：获取分页的文章列表
    @Transactional(readOnly = true) // readOnly = true 优化只读事务
    public Page<ArticleSummaryResponse> getAllArticles(Pageable pageable) {
        Page<Article> articles = articleRepository.findAll(pageable);
        return articles.map(articleMapper::articleToArticleSummaryResponse);
    }

    // 新增：根据标签名获取文章列表
    @Transactional(readOnly = true)
    public Page<ArticleSummaryResponse> getArticlesByTagName(String tagName, Pageable pageable) {
        Page<Article> articles = articleRepository.findByTags_Name(tagName, pageable);
        return articles.map(articleMapper::articleToArticleSummaryResponse);
    }

    // 在 ArticleService 类的内部，添加这个新方法
    @Transactional
    public ArticleResponse updateArticle(Long id, UpdateArticleRequest request) {
        // 1. 查找文章，找不到就抛出标准异常
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id: " + id));

        // 2. 更新文章的标题和内容
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());

        // 3. 复用我们强大的标签服务，处理标签的更新
        Set<Tag> tags = tagService.findOrCreateTags(request.getTags());
        article.setTags(tags);

        // 4. 保存被修改的文章，JPA会自动处理更新操作
        Article updatedArticle = articleRepository.save(article);

        // 5. 转换并返回结果
        return articleMapper.articleToArticleResponse(updatedArticle);
    }

    @Transactional
    public ArticleResponse createArticle(CreateArticleRequest request) {
        Article article = new Article();
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());

        Set<Tag> tags = tagService.findOrCreateTags(request.getTags());
        article.setTags(tags);

        Article savedArticle = articleRepository.save(article);
        return articleMapper.articleToArticleResponse(savedArticle);
    }

    @Transactional
    public ArticleResponse getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id: " + id));

        // 核心业务逻辑：阅读量 +1
        article.setViewCount(article.getViewCount() + 1);
        Article updatedArticle = articleRepository.save(article);

        return articleMapper.articleToArticleResponse(updatedArticle);
    }

    // --- New Methods for Content Discovery ---

    @Transactional(readOnly = true)
    public List<ArticleSummaryResponse> getFeaturedArticles() {
        List<Article> articles = articleRepository.findByIsFeaturedTrueOrderByCreatedAtDesc();
        return articles.stream()
                .map(articleMapper::articleToArticleSummaryResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ArticleSummaryResponse> getTrendingArticles(int limit) {
        // We use PageRequest to ask for the "first page" of size "limit"
        Pageable pageable = PageRequest.of(0, limit);
        List<Article> articles = articleRepository.findTopByOrderByViewCountDesc(pageable);
        return articles.stream()
                .map(articleMapper::articleToArticleSummaryResponse)
                .collect(Collectors.toList());
    }

    // 在 ArticleService 类的内部，添加这个新方法
    @Transactional
    public ArticleResponse setFeaturedStatus(Long id, boolean isFeatured) {
        // 1. 查找文章，如果找不到则抛出标准异常
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id: " + id));

        // 2. 更新 isFeatured 状态
        article.setFeatured(isFeatured);

        // 3. 保存更新后的文章
        Article updatedArticle = articleRepository.save(article);

        // 4. 将更新后的实体转换为 DTO 并返回
        return articleMapper.articleToArticleResponse(updatedArticle);
    }

    @Transactional(readOnly = true)
    public Page<ArticleSummaryResponse> searchArticles(String keyword, Pageable pageable) {
        Page<Article> articles = articleRepository.searchByTitleOrContent(keyword, pageable);
        return articles.map(articleMapper::articleToArticleSummaryResponse);
    }

    // 在 ArticleService 类的内部，添加这个新方法
    @Transactional
    public void deleteArticle(Long id) {
        // 检查文章是否存在，不存在则抛出异常，这也能防止无效的删除操作
        if (!articleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Article not found with id: " + id);
        }
        articleRepository.deleteById(id);
    }

    // 在 ArticleService 类的内部，添加这些新方法
    @Transactional(readOnly = true)
    public List<ArchiveCountResponse> getArchiveCounts() {
        return articleRepository.findArchiveCounts();
    }

    @Transactional(readOnly = true)
    public List<ArticleSummaryResponse> getRelatedArticles(Long articleId, int limit) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id: " + articleId));

        if (article.getTags().isEmpty()) {
            return new ArrayList<>();
        }

        Set<Long> tagIds = article.getTags().stream().map(Tag::getId).collect(Collectors.toSet());
        Pageable pageable = PageRequest.of(0, limit);

        List<Article> relatedArticles = articleRepository.findRelatedArticles(tagIds, articleId, pageable);
        return relatedArticles.stream()
                .map(articleMapper::articleToArticleSummaryResponse)
                .collect(Collectors.toList());
    }
}