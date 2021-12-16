package com.example.hello.sqs.sqs;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SQSClient {

    @Bean
    public AmazonSQS getAmazonSQS() {
        return AmazonSQSClientBuilder.standard()
                .withRegion(Regions.US_EAST_2)
                .build();
    }
}
