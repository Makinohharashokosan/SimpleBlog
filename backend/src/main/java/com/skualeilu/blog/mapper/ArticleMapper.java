package com.skualeilu.blog.mapper;

import com.skualeilu.blog.dto.response.ArticleResponse;
import com.skualeilu.blog.dto.response.ArticleSummaryResponse;
import com.skualeilu.blog.dto.response.TagResponse;
import com.skualeilu.blog.entity.Article;
import com.skualeilu.blog.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

    // 单个实体到 DTO 的转换
    ArticleResponse articleToArticleResponse(Article article);
    ArticleSummaryResponse articleToArticleSummaryResponse(Article article);
    TagResponse tagToTagResponse(Tag tag);

    // 列表转换
    List<TagResponse> tagsToTagResponses(List<Tag> tags);
}