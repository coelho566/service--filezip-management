package com.framezip.management.application.config.aws;

import io.awspring.cloud.sqs.listener.QueueNotFoundStrategy;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;

@Configuration
public class AmazonSqsConfig {


    @Value("${spring.cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${spring.cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${spring.cloud.aws.region.static}")
    private String region;

    @Bean
    SqsAsyncClient sqsAsyncClient(){
        AwsSessionCredentials awsSessionCredentials = AwsSessionCredentials.create(accessKey, secretKey, "IQoJb3JpZ2luX2VjEJD//////////wEaCXVzLXdlc3QtMiJIMEYCIQDsSaViqZgVqzsM2+DrGoeRgZUhMOS2NN3rU+Utyq5btQIhAIxejMIUYBb55XxSgrGorOmOZJhPoC14hDmnCtPIvGnhKr8CCKn//////////wEQAhoMMTEwODMyNzc4NTk4IgxMDXiMhAJh9XZRPO4qkwK+cqlH2LTmQD4lp/HBQTlg3pCVojbilCQEwWjff5K3NL1/seZ5gpc4Gw3aJMKwQ0RfiyDFgqkljG9hr3IFjfhlG4zB35+38NLxuWlNT/ZPoW+X32kllfiIh4nVXr9j8k6IiTRQ86uiItNG/9InixYJ9FaZWFazMbO7L+HcqsN6E6qzYzK17W8XcrabG7uuol3jEFqMLSL4na1DiBXsnezr5sIxucYiHJc6ZiP30rJ96sGNz08Gmm1GjD3WR7Zn0BmlUZ8bfwp6fiOrSqDFlZ9XODxteSgKhB0Rm1cTMc2+pRZ1o5I6GEletwclkv9Q7qtgVTxXcI7jj696jl2WJpSnCUbMfQECpPXPN/hKF4xixYBncDDRp6O9BjqcAaaQPlAfHsFQTm+73Zb13Dty0PMuq/0pcy5NnzGYjgsnbnurna/rhPGnqxxBXqK3N8b2nwemLU3L5oJW7EFPP/RS2uOqHZcbnsTWTrCb+fjyzSy12aXuDzx3mIdanfO/LwF/FjOE1dsZkIP7GqllNiHjCyk3Abo0HfFz5XUzTC1EHDB2iUEVqh+hOp7xcyVWI65oBE8XC5RZWqE2lg==");
        return SqsAsyncClient
                .builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider
                        .create(awsSessionCredentials))
                .build();
        // add more Options
    }

    @Bean
    public SqsClient sqsClient() {
        return SqsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }

    @Bean
    public SqsTemplate sqsTemplate(SqsAsyncClient sqsAsyncClient){
        return SqsTemplate.builder().sqsAsyncClient(sqsAsyncClient).build();
    }

//    @Bean
//    public SqsAsyncClient sqsAsyncClient() {
//        return SqsAsyncClient.builder()
//                .region(Region.US_EAST_1)
//                .credentialsProvider(ProfileCredentialsProvider.create())
//                .endpointOverride(URI.create("https://sqs.us-east-1.amazonaws.com/110832778598/filezip-send_email_queue"))
//                .build();
//    }

//    @Bean
//    public SqsTemplate sqsTemplate() {
//        return SqsTemplate.builder()
//                .sqsAsyncClient(sqsAsyncClient())
//                .configure(o -> o.queueNotFoundStrategy(QueueNotFoundStrategy.FAIL))
//                .build();
//    }
}
