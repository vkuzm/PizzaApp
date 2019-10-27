package com.vkuzmenko.pizza.admin.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    void upload(MultipartFile file);
}
