package com.framezip.management.adapters.inbound.controller;


import com.framezip.management.application.core.usecase.CreateFileZipUseCase;
import com.framezip.management.application.core.usecase.DownloadFileZipUseCase;
import com.framezip.management.application.core.usecase.GetStatusVideoProcessorUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api/video")
@RequiredArgsConstructor
public class VideoManagementController {

    private final CreateFileZipUseCase createFileZipUseCase;
    private final GetStatusVideoProcessorUseCase getStatusVideoProcessorUseCase;
    private final DownloadFileZipUseCase downloadFileZipUseCase;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadVideo(JwtAuthenticationToken auth,
                                              @RequestParam("intervalFrame") Double intervalFrame,
                                              @RequestParam("files") List<MultipartFile> files) {

        var correlationId = UUID.randomUUID().toString();
        log.info("Uploading video using correlationId: {}", correlationId);

        var videoFrameRequest = VideoFrameRequest.builder()
                .userId(auth.getToken().getClaimAsString(StandardClaimNames.SUB))
                .userName(auth.getToken().getClaimAsString(StandardClaimNames.PREFERRED_USERNAME))
                .userEmail(auth.getToken().getClaimAsString(StandardClaimNames.EMAIL))
                .correlationId(correlationId)
                .intervalFrame(intervalFrame)
                .build();

        createFileZipUseCase.uploadVideo(videoFrameRequest, files);
        return ResponseEntity.ok(correlationId);
    }

    @GetMapping("/{correlationId}/status")
    public ResponseEntity<BaseResponse<List<VideoFrameResponse>>> statusVideoProcessor(JwtAuthenticationToken auth,
                                                                                       @PathVariable("correlationId") String correlationId) {

        var statusVideoFrame = getStatusVideoProcessorUseCase
                .getStatusVideoFrame(auth.getToken().getClaimAsString(StandardClaimNames.SUB), correlationId);

        return ResponseEntity.ok(new BaseResponse<>(statusVideoFrame));
    }

    @GetMapping("/download/zip/{fileName}")
    public ResponseEntity<byte[]> downloadZipFile(JwtAuthenticationToken auth,
                                                  @PathVariable("fileName") String fileName) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
        headers.add(HttpHeaders.CONTENT_TYPE, "application/zip");

        byte[] bytes = downloadFileZipUseCase.downloadFileZip(auth.getToken().getClaimAsString(StandardClaimNames.SUB), fileName);

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);

    }
}



