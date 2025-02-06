package com.framezip.management.adapters.inbound.controller.handle;

import com.framezip.management.adapters.inbound.controller.response.BaseResponse;
import com.framezip.management.adapters.inbound.controller.response.ErrorResponse;
import com.framezip.management.application.exception.BusinessException;
import com.framezip.management.application.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseResponse<?>> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        log.error("ResourceNotFoundException: {}", ex.getMessage());
        var response = new ErrorResponse(ex.getMessage(), request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(new BaseResponse<>(response), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BaseResponse<?>> handleBusinessException(BusinessException ex, WebRequest request) {
        log.warn("BusinessException: {}", ex.getMessage());
        var response = new ErrorResponse(ex.getMessage(), request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(new BaseResponse<>(response), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<?>> handleGlobalException(Exception ex, WebRequest request) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        var response = new ErrorResponse("An unexpected error occurred", request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(new BaseResponse<>(response), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
