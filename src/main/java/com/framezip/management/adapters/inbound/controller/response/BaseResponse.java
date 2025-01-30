package com.framezip.management.adapters.inbound.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse<T> {

    private T data;
    private Error error;

    public BaseResponse() {
    }

    public BaseResponse(T data) {
        this.data = data;
    }

    public BaseResponse(Error error) {
        this.error = error;
    }

    public BaseResponse(T data, Error error) {
        this.data = data;
        this.error = error;
    }
}
