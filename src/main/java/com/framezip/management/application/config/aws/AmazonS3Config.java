package com.framezip.management.application.config.aws;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class AmazonS3Config {

//    @Value("${aws.access_key_id}")
//    private String accessKeyId;
//
//    @Value("${aws.secret_access_key}")
//    private String secretAccessKey;

//    @Value("${aws.region}")
//    private String region;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .region(Region.US_EAST_1) // Região do S3
                .credentialsProvider(ProfileCredentialsProvider.create()) // Usa o perfil padrão
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        return S3Presigner.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }
}
