package com.skualeilu.blog.controller;

import com.skualeilu.blog.dto.response.SettingsResponse;
import com.skualeilu.blog.service.SettingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.skualeilu.blog.dto.request.UpdateSettingsRequest; // <-- 添加这个新的 import
import jakarta.validation.Valid; // <-- 添加这个新的 import
import org.springframework.web.bind.annotation.PutMapping; // <-- 添加这个新的 import
import org.springframework.web.bind.annotation.RequestBody; // <-- 添加这个新的 import
@RestController
@RequestMapping("/api/settings")
public class SettingsController {

    private final SettingsService settingsService;

    public SettingsController(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @GetMapping
    public ResponseEntity<SettingsResponse> getAllSettings() {
        SettingsResponse settings = settingsService.getAllSettings();
        return ResponseEntity.ok(settings);
    }

    // 在 getAllSettings 方法之后，插入这个新方法
    @PutMapping
    public ResponseEntity<SettingsResponse> updateAllSettings(@Valid @RequestBody UpdateSettingsRequest request) {
        SettingsResponse updatedSettings = settingsService.updateAllSettings(request);
        return ResponseEntity.ok(updatedSettings);
    }
}