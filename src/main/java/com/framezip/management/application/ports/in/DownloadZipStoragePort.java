package com.framezip.management.application.ports.in;

public interface DownloadZipStoragePort {

    byte[] downloadZipBucket(String fileKey);
}
