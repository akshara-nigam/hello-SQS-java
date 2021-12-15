package com.example.hello.sqs.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SQSClient {

    @Bean
    public AmazonSQS getAmazonSQS() {
        AmazonSQS amazonSQS = AmazonSQSClientBuilder.standard()
                .withRegion("us-east-2")
                .build();
        return amazonSQS;
    }
}
