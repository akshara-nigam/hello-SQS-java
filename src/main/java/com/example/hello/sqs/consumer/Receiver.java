package com.example.hello.sqs.consumer;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Receiver {

    @Autowired
    AmazonSQS sqs;

    public void ReceiveMessage(String url) {
        List<Message> messages = sqs.receiveMessage(url).getMessages();

        System.out.println("Messages Received : ");
        for(Message m : messages) {
            System.out.println("Message Body : " + m.getBody());

            Map<String, MessageAttributeValue> attributes = m.getMessageAttributes();
            System.out.println("Message Attributes : ");
            for(String key : attributes.keySet()) {
                System.out.println(key + " : " + attributes.get(key));
            }

            String receipt = m.getReceiptHandle();
            this.DeleteMessage(receipt, url);
        }
    }

    private void DeleteMessage(String receipt, String url) {
        sqs.deleteMessage(url, receipt);
        System.out.println("Message Deleted Successfully !!");
    }
}
