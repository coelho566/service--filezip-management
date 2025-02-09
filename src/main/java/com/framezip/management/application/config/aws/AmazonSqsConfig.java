package com.framezip.management.application.config.aws;

import io.awspring.cloud.sqs.listener.QueueNotFoundStrategy;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

@Configuration
public class AmazonSqsConfig {

    @Bean
    public SqsClient sqsClient() {
        return SqsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }

    @Bean
    public SqsAsyncClient sqsAsyncClient() {
        return SqsAsyncClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
//                .endpointOverride(URI.create("https://sqs.us-east-1.amazonaws.com/"))
                .build();
    }

    @Bean
    public SqsTemplate sqsTemplate() {
        return SqsTemplate.builder()
                .sqsAsyncClient(sqsAsyncClient())
                .configure(o -> o.queueNotFoundStrategy(QueueNotFoundStrategy.FAIL))
                .build();
    }
}
