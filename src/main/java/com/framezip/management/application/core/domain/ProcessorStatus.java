package com.framezip.management.application.core.domain;

import lombok.Getter;

@Getter
public enum ProcessorStatus {

    RECEIVED,
    STARTING_PROCESS,
    PROCESSING,
    PROCESSED
}
