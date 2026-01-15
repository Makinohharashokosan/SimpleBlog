package com.skualeilu.blog.service;

import com.skualeilu.blog.dto.response.StatsResponse;
import com.skualeilu.blog.repository.ArticleRepository;
import com.skualeilu.blog.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StatsService {

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;

    public StatsService(ArticleRepository articleRepository, TagRepository tagRepository) {
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
    }

    @Transactional(readOnly = true)
    public StatsResponse getSiteStats() {
        StatsResponse stats = new StatsResponse();
        stats.setTotalArticles(articleRepository.count());
        stats.setTotalTags(tagRepository.count());
        // 计算总浏览量需要自定义查询
        Long totalViews = articleRepository.sumViewCount();
        stats.setTotalViews(totalViews != null ? totalViews : 0L);
        return stats;
    }
}