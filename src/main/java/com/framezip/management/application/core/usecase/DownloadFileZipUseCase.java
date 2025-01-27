package com.framezip.management.application.core.usecase;

public interface DownloadFileZipUseCase {

    byte[] downloadFileZip(String userId, String fileName);
}
