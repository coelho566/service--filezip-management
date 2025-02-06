package com.framezip.management.adapters.inbound.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse<T> {

    private T data;
    private ErrorResponse error;

    public BaseResponse() {
    }

    public BaseResponse(T data) {
        this.data = data;
    }

    public BaseResponse(ErrorResponse error) {
        this.error = error;
    }

    public BaseResponse(T data, ErrorResponse error) {
        this.data = data;
        this.error = error;
    }
}
