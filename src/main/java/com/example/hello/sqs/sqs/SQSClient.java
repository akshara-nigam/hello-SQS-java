package com.example.hello.sqs.sqs;

import com.example.hello.sqs.constants.Config;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SQSClient {

    @Autowired
    Config config;

    @Bean
    public AmazonSQS getAmazonSQS() {
        return AmazonSQSClientBuilder.standard()
                .withRegion(config.getAWS_REGION())
                .build();
    }
}
