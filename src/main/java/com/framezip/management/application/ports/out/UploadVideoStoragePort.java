package com.framezip.management.application.ports.out;

import org.springframework.web.multipart.MultipartFile;

public interface UploadVideoStoragePort {

    void uploadVideoBucket(String key, MultipartFile file);
}
