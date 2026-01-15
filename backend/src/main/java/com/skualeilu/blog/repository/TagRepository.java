package com.skualeilu.blog.repository;

import com.skualeilu.blog.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * 根据标签名称查找标签。
     * 这在创建文章并关联标签时非常有用，可以先检查标签是否已存在。
     * @param name 标签名称
     * @return 一个包含 Tag 的 Optional，如果找不到则为空
     */
    Optional<Tag> findByName(String name);

    // 新增：查找所有标签并按名称升序排序
    List<Tag> findAllByOrderByNameAsc();
}