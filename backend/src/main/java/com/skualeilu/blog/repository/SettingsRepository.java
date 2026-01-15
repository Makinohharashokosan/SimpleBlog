package com.skualeilu.blog.repository;

import com.skualeilu.blog.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SettingsRepository extends JpaRepository<Setting, Long> {
    // 根据 key 查找配置项
    Optional<Setting> findByKey(String key);
}