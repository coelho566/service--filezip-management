package com.framezip.management.adapters.inbound.controller.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZipRequest {

    private String fileName;
    private Double intervalFrame;
}
