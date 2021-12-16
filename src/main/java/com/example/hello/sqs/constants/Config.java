package com.example.hello.sqs.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {
    @Value("${aws.region}")
    String AWS_REGION;

    @Value("${sqs.queue}")
    String SQS_QUEUE;

    public String getSQS_QUEUE() {
        return SQS_QUEUE;
    }

    public String getAWS_REGION() {
        return AWS_REGION;
    }
}
