package com.skualeilu.blog.service;

import com.skualeilu.blog.dto.request.UpdateTagRequest;
import com.skualeilu.blog.entity.Tag;
import com.skualeilu.blog.exception.ResourceNotFoundException;
import com.skualeilu.blog.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import com.skualeilu.blog.dto.response.TagResponse;
import com.skualeilu.blog.mapper.ArticleMapper;
import java.util.List;
@Service
public class TagService {

    private final TagRepository tagRepository;
    private final ArticleMapper articleMapper; // 新增依赖

    // 修改构造函数
    public TagService(TagRepository tagRepository, ArticleMapper articleMapper) {
        this.tagRepository = tagRepository;
        this.articleMapper = articleMapper;
    }

    // 新增：获取所有标签的方法
    public List<TagResponse> getAllTags() {
        List<Tag> tags = tagRepository.findAllByOrderByNameAsc();
        return articleMapper.tagsToTagResponses(tags);
    }

    @Transactional
    public Set<Tag> findOrCreateTags(Set<String> tagNames) {
        if (tagNames == null || tagNames.isEmpty()) {
            return new HashSet<>();
        }

        return tagNames.stream()
                .map(name -> tagRepository.findByName(name)
                        .orElseGet(() -> tagRepository.save(new Tag(name))))
                .collect(Collectors.toSet());
    }
    // 在 TagService 类的内部，添加这些新方法
    @Transactional
    public TagResponse updateTag(Long id, UpdateTagRequest request) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + id));
        tag.setName(request.getName());
        Tag updatedTag = tagRepository.save(tag);
        return articleMapper.tagToTagResponse(updatedTag);
    }

    @Transactional
    public void deleteTag(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag not found with id: " + id));
        // 增加业务逻辑：如果标签仍被文章使用，则不允许删除
        if (!tag.getArticles().isEmpty()) {
            throw new IllegalStateException("Cannot delete tag: It is currently associated with one or more articles.");
        }
        tagRepository.deleteById(id);
    }
}