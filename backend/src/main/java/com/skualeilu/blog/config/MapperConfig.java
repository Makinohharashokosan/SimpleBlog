package com.skualeilu.blog.config;

import com.skualeilu.blog.mapper.ArticleMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    @ConditionalOnMissingBean
    public ArticleMapper articleMapper() {
        return Mappers.getMapper(ArticleMapper.class);
    }
}
