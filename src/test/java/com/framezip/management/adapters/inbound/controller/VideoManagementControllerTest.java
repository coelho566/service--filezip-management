//package com.framezip.management.adapters.inbound.controller;
//
//import com.framezip.management.adapters.inbound.controller.request.VideoFrameRequest;
//import com.framezip.management.adapters.inbound.controller.response.BaseResponse;
//import com.framezip.management.adapters.inbound.controller.response.ProcessResponse;
//import com.framezip.management.adapters.inbound.controller.response.VideoFrameResponse;
//import com.framezip.management.application.core.usecase.CreateFileZipUseCase;
//import com.framezip.management.application.core.usecase.DownloadFileZipUseCase;
//import com.framezip.management.application.core.usecase.GetStatusVideoProcessorUseCase;
//import com.framezip.management.application.utils.JwtUtils;
//import io.restassured.http.ContentType;
//import io.restassured.module.mockmvc.RestAssuredMockMvc;
//import io.restassured.response.Response;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static io.restassured.RestAssured.given;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class VideoManagementControllerTest {
//
//    @Mock
//    private CreateFileZipUseCase createFileZipUseCase;
//
//    @Mock
//    private GetStatusVideoProcessorUseCase getStatusVideoProcessorUseCase;
//
//    @Mock
//    private DownloadFileZipUseCase downloadFileZipUseCase;
//
//    @Autowired
//    private VideoManagementController videoManagementController;
//
//    @BeforeEach
//    public void setup() {
//        RestAssuredMockMvc.standaloneSetup(MockMvcBuilders.standaloneSetup(videoManagementController));
//    }
//
//    @Test
//    public void testGeneratedLinkUploadVideo() {
//        VideoFrameRequest videoFrameRequest = new VideoFrameRequest();
//        videoFrameRequest.setZipInfo(List.of());
//        ProcessResponse processResponse = new ProcessResponse();
//        JwtAuthenticationToken auth = new JwtAuthenticationToken(Jwt.withTokenValue("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c").build());
//
//        when(createFileZipUseCase.uploadVideo(videoFrameRequest, JwtUtils.getUser(auth))).thenReturn(processResponse);
//
//        given()
//
//                .contentType(ContentType.JSON)
//                .body(videoFrameRequest)
//                .when()
//                .post("/api/video/presigned-url")
//                .then()
//                .statusCode(HttpStatus.OK.value());
//    }
//
//    @Test
//    public void testStatusVideoProcessor() {
//        String correlationId = "some-correlation-id";
//        List<VideoFrameResponse> statusVideoFrame = new ArrayList<>();
//        BaseResponse<List<VideoFrameResponse>> baseResponse = new BaseResponse<>(statusVideoFrame);
//        JwtAuthenticationToken auth = new JwtAuthenticationToken(null);
//
//        when(getStatusVideoProcessorUseCase.getStatusVideoFrame(auth.getToken().getClaimAsString(StandardClaimNames.SUB), correlationId)).thenReturn(statusVideoFrame);
//
//        given()
//                .auth().oauth2(auth.getToken().getTokenValue())
//                .pathParam("correlationId", correlationId)
//                .when()
//                .get("/api/video/{correlationId}/status")
//                .then()
//                .statusCode(HttpStatus.OK.value());
//    }
//
//    @Test
//    public void testDownloadZipFile() {
//        String fileName = "test-file";
//        byte[] zipFileBytes = new byte[0];
//        JwtAuthenticationToken auth = new JwtAuthenticationToken(null);
//
//        when(downloadFileZipUseCase.downloadFileZip(auth.getToken().getClaimAsString(StandardClaimNames.SUB), fileName)).thenReturn(zipFileBytes);
//
//        Response response = given()
//                .auth().oauth2(auth.getToken().getTokenValue())
//                .pathParam("fileName", fileName)
//                .when()
//                .get("/api/video/download/zip/{fileName}");
//
//        response.then()
//                .statusCode(HttpStatus.OK.value())
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName + ".zip")
//                .header(HttpHeaders.CONTENT_TYPE, "application/zip");
//    }
//}
