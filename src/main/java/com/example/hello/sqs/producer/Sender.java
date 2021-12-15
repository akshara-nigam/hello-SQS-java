package com.example.hello.sqs.producer;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Sender {

    @Autowired
    AmazonSQS sqs;

    public void SendMessage(String messageBody, String url, Map<String, String> msgAttributes) {
        Map<String, MessageAttributeValue> attributes = transformToMessageMap(msgAttributes);

        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(url)
                .withMessageBody(messageBody)
                .withMessageAttributes(attributes);

        sqs.sendMessage(send_msg_request);
        System.out.println("Message Sent !!");
    }

    private Map<String, MessageAttributeValue> transformToMessageMap(Map<String, String> attributes) {
        Map<String, MessageAttributeValue> result = new HashMap<>();

        for(String key : attributes.keySet()) {
            result.put(key, new MessageAttributeValue()
                    .withDataType("String")
                    .withStringValue(attributes.get(key))
            );
        }

        return result;
    }
}
