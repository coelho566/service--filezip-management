package com.framezip.management.adapters.inbound.controller;


import com.framezip.management.adapters.inbound.controller.request.VideoFrameRequest;
import com.framezip.management.adapters.inbound.controller.response.BaseResponse;
import com.framezip.management.adapters.inbound.controller.response.ProcessResponse;
import com.framezip.management.adapters.inbound.controller.response.VideoFrameResponse;
import com.framezip.management.application.core.usecase.CreateFileZipUseCase;
import com.framezip.management.application.core.usecase.DownloadFileZipUseCase;
import com.framezip.management.application.core.usecase.GetStatusVideoProcessorUseCase;
import com.framezip.management.application.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/video")
@RequiredArgsConstructor
public class VideoManagementController {

    private final CreateFileZipUseCase createFileZipUseCase;
    private final GetStatusVideoProcessorUseCase getStatusVideoProcessorUseCase;
    private final DownloadFileZipUseCase downloadFileZipUseCase;

    @PostMapping("/presigned-url")
    public ResponseEntity<ProcessResponse> generatedLinkUploadVideo(JwtAuthenticationToken auth,
                                                                    @RequestBody VideoFrameRequest videoFrameRequest) {

        var user = JwtUtils.getUser(auth);
        var processResponse = createFileZipUseCase.uploadVideo(videoFrameRequest, user);
        return ResponseEntity.ok(processResponse);
    }

    @GetMapping("/{correlationId}/status")
    public ResponseEntity<BaseResponse<List<VideoFrameResponse>>> statusVideoProcessor(JwtAuthenticationToken auth,
                                                                                       @PathVariable("correlationId") String correlationId) {

        log.info("Getting status video processor correlation id: {}", correlationId);
        var statusVideoFrame = getStatusVideoProcessorUseCase
                .getStatusVideoFrame(auth.getToken().getClaimAsString(StandardClaimNames.SUB), correlationId);

        return ResponseEntity.ok(new BaseResponse<>(statusVideoFrame));
    }

    @GetMapping("/download/zip/{fileName}")
    public ResponseEntity<byte[]> downloadZipFile(JwtAuthenticationToken auth,
                                                  @PathVariable("fileName") String fileName) {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + ".zip");
        headers.add(HttpHeaders.CONTENT_TYPE, "application/zip");

        log.info("Init downloading zip file");
        byte[] bytes = downloadFileZipUseCase.downloadFileZip(auth.getToken().getClaimAsString(StandardClaimNames.SUB), fileName);

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}



