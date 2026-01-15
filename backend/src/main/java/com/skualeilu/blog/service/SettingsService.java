package com.skualeilu.blog.service;

import com.skualeilu.blog.dto.response.SettingsResponse;
import com.skualeilu.blog.dto.response.SocialLinkResponse;
import com.skualeilu.blog.entity.Setting;
import com.skualeilu.blog.entity.SocialLink;
import com.skualeilu.blog.repository.SettingsRepository;
import com.skualeilu.blog.repository.SocialLinkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.skualeilu.blog.dto.request.UpdateSettingsRequest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.function.Consumer; // <-- 添加这个新的 import

@Service
public class SettingsService {

    private final SettingsRepository settingsRepository;
    private final SocialLinkRepository socialLinkRepository;

    public SettingsService(SettingsRepository settingsRepository, SocialLinkRepository socialLinkRepository) {
        this.settingsRepository = settingsRepository;
        this.socialLinkRepository = socialLinkRepository;
    }

    @Transactional(readOnly = true)
    public SettingsResponse getAllSettings() {
        // 1. Fetch all key-value settings and put them in a map for easy access
        List<Setting> settingsList = settingsRepository.findAll();
        Map<String, String> settingsMap = settingsList.stream()
                .collect(Collectors.toMap(Setting::getKey, Setting::getValue));

        // 2. Fetch all social links
        List<SocialLink> socialLinksList = socialLinkRepository.findAll();
        List<SocialLinkResponse> socialLinkResponses = socialLinksList.stream()
                .map(this::convertToSocialLinkResponse)
                .collect(Collectors.toList());

        // 3. Map the values to the response DTO
        SettingsResponse response = new SettingsResponse();
        response.setBlogTitle(settingsMap.get("blogTitle"));
        response.setBlogSubtitle(settingsMap.get("blogSubtitle"));
        response.setLogoUrl(settingsMap.get("logoUrl"));
        response.setFaviconUrl(settingsMap.get("faviconUrl"));
        response.setCopyrightText(settingsMap.get("copyrightText"));
        response.setAuthorName(settingsMap.get("authorName"));
        response.setAuthorBio(settingsMap.get("authorBio"));
        response.setAuthorAvatarUrl(settingsMap.get("authorAvatarUrl"));

        // Handle number conversions safely
        response.setArticlesPerPage(parseInteger(settingsMap.get("articlesPerPage")));
        response.setFeaturedArticlesCount(parseInteger(settingsMap.get("featuredArticlesCount")));
        response.setTrendingArticlesCount(parseInteger(settingsMap.get("trendingArticlesCount")));

        response.setSocialLinks(socialLinkResponses);

        return response;
    }

    private SocialLinkResponse convertToSocialLinkResponse(SocialLink socialLink) {
        SocialLinkResponse dto = new SocialLinkResponse();
        dto.setId(socialLink.getId());
        dto.setPlatform(socialLink.getPlatform());
        dto.setUrl(socialLink.getUrl());
        return dto;
    }

    private Integer parseInteger(String value) {
        if (value == null) return null;
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null; // Return null if the value is not a valid integer
        }
    }

    // 在 SettingsService 类的内部，添加这个新方法
    @Transactional
    public SettingsResponse updateAllSettings(UpdateSettingsRequest request) {
        // 1. 更新 Key-Value 形式的配置项
        updateSetting("blogTitle", request.getBlogTitle());
        updateSetting("blogSubtitle", request.getBlogSubtitle());
        updateSetting("logoUrl", request.getLogoUrl());
        updateSetting("faviconUrl", request.getFaviconUrl());
        updateSetting("copyrightText", request.getCopyrightText());
        updateSetting("authorName", request.getAuthorName());
        updateSetting("authorBio", request.getAuthorBio());
        updateSetting("authorAvatarUrl", request.getAuthorAvatarUrl());
        updateSetting("articlesPerPage", request.getArticlesPerPage());
        updateSetting("featuredArticlesCount", request.getFeaturedArticlesCount());
        updateSetting("trendingArticlesCount", request.getTrendingArticlesCount());

        // 2. 更新社交链接 (采用先删后增的简单高效策略)
        if (request.getSocialLinks() != null) {
            socialLinkRepository.deleteAll();
            request.getSocialLinks().forEach(linkDto -> {
                SocialLink newLink = new SocialLink();
                newLink.setPlatform(linkDto.getPlatform());
                newLink.setUrl(linkDto.getUrl());
                socialLinkRepository.save(newLink);
            });
        }

        // 3. 返回更新后的所有配置
        return getAllSettings();
    }

    // 辅助方法，用于更新或创建单个配置项
    private void updateSetting(String key, Object value) {
        if (value == null) return; // 如果传入的值是null，则不作任何操作

        Setting setting = settingsRepository.findByKey(key)
                .orElseGet(() -> {
                    Setting newSetting = new Setting();
                    newSetting.setKey(key);
                    return newSetting;
                });

        setting.setValue(String.valueOf(value));
        settingsRepository.save(setting);
    }
}