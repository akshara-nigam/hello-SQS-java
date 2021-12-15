package com.example.hello.sqs.consumer;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Receiver {

    @Autowired
    AmazonSQS sqs;

    public void ReceiveMessage(String url) {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(url)
                .withMaxNumberOfMessages(10)
                .withWaitTimeSeconds(3)
                .withMessageAttributeNames("Author", "Title", "WeeksOn");

        List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();

        if (messages.isEmpty()) {
            System.out.printf("Queue Empty !!%s\n", url);
            return;
        }

        System.out.println("Message Received : ");
        for(Message m : messages) {
            System.out.println("\tBody \t: " + m.getBody());

            Map<String, MessageAttributeValue> attributes = m.getMessageAttributes();
            for(String key : attributes.keySet()) {
                System.out.println("\t" + key + "\t: " + attributes.get(key).getStringValue());
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
