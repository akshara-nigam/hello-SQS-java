package com.example.hello.sqs.consumer;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Receiver {

    @Autowired
    AmazonSQS sqs;

    public void ReceiveMessage(String url) {
       Map<String, Map<String, Object>> hashMap =  this.PollMessage(url);

        System.out.println("\nMessages : ");
       for(String receipt : hashMap.keySet()) {
           Map<String, Object> val1 = hashMap.get(receipt);
           System.out.print("\n\tReceipt : " + receipt + "\n\tBody : ");

           for(String body : val1.keySet()) {
               System.out.println(body + "\n\tAttributes : " + val1.get(body));
           }
       }
    }

    private Map<String, Map<String, Object>> PollMessage(String url) {
        // The Value of the map is of type `receipt -> message body, message attributes`
        Map<String, Map<String, Object>> messagelist = new HashMap<>();

        try {
            boolean flag = true;

            while(flag) {
                System.out.println("Polling for Messages !!");

                ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(url)
                        .withMaxNumberOfMessages(10)
                        .withWaitTimeSeconds(3)
                        .withMessageAttributeNames("All");

                List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
                System.out.println("\tNo. of Messages received : " + messages.size());

                for (Message m : messages) {
                    Map<String, Object> msg = new HashMap<>();
                    msg.put(m.getBody(), m.getMessageAttributes());

                    String receipt = m.getReceiptHandle();

                    messagelist.put(receipt, msg);
                    this.DeleteMessage(receipt, url);
                }

                if(messages.size() == 0)
                    flag = false;
            }
        } catch (AmazonSQSException ase) {
            ase.printStackTrace();
        }

        return messagelist;
    }

    private void DeleteMessage(String receipt, String url) {
        sqs.deleteMessage(url, receipt);
        System.out.println("Message Deleted Successfully !!");
    }
}
