package com.example.hello.sqs.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Queue {

    @Autowired
    AmazonSQS sqs;

    public void CreateQueue(String name) {
        CreateQueueRequest create_request = new CreateQueueRequest(name)
                .addAttributesEntry("MessageRetentionPeriod", "86400");

        try {
            CreateQueueResult result = sqs.createQueue(create_request);
            System.out.println("Queue Created with URL : " + result.getQueueUrl());

        } catch (AmazonSQSException e) {
            if (!e.getErrorCode().equals("QueueAlreadyExists")) {
                System.out.println("Queue Already Exists !!");
            }
        }
    }

    public void ListQueues() {
        ListQueuesResult queues = sqs.listQueues();
        List<String> result = queues.getQueueUrls();

        for (int i=0;i<result.size(); i++){
             System.out.println(i+1 + " : " + result.get(i));
        }
    }

    public void DeleteQueue(String name) {
        sqs.deleteQueue(name);
        System.out.println("Queue Deleted Successfully !!");
    }

    public String GetQueueURL(String name) {
        GetQueueUrlResult result = sqs.getQueueUrl(name);
        return result.getQueueUrl();
    }
}
