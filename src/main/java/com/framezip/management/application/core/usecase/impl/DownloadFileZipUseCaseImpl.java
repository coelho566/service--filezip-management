package com.framezip.management.application.core.usecase.impl;

import com.framezip.management.application.core.usecase.DownloadFileZipUseCase;
import com.framezip.management.application.exception.ResourceNotFoundException;
import com.framezip.management.application.ports.in.DownloadZipStoragePort;
import com.framezip.management.application.ports.out.VideoFrameRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DownloadFileZipUseCaseImpl implements DownloadFileZipUseCase {

    private final DownloadZipStoragePort downloadZipStoragePort;
    private final VideoFrameRepositoryPort videoFrameRepositoryPort;

    @Override
    public byte[] downloadFileZip(String userId, String fileName) {

        var video = videoFrameRepositoryPort.getVideoByUserIdAndFileId(userId, fileName);

        return video.map(v -> downloadZipStoragePort.downloadZipBucket(v.getId()))
                .orElseThrow(() -> new ResourceNotFoundException("File not found for userId: " + userId));

    }
}
