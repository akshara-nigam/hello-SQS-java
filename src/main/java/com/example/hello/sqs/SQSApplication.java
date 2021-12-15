package com.example.hello.sqs;

import com.example.hello.sqs.sqs.Queue;
import com.example.hello.sqs.consumer.Receiver;
import com.example.hello.sqs.producer.Sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication(scanBasePackages = {"com.example.hello.sqs"})
public class SQSApplication implements CommandLineRunner {

    @Autowired
    Queue q;

    @Autowired
    Receiver r;

    @Autowired
    Sender s;

    public static void main(String[] args) {
        new SpringApplication(SQSApplication.class).run(args);
    }

    @Override
    public void run(String... args) {
        String queueName = "Test-Queue-Akshara-Java";
        String msgBody = "Information about current NY Times fiction bestseller for week of 12/11/2016.";
        Map<String,String> msg = new HashMap<>();
        msg.put("Author", "John Grishma");
        msg.put("Title", "The Whistler");
        msg.put("WeeksOn", "6");

        q.ListQueues();
        q.CreateQueue(queueName);
        String url = q.GetQueueURL(queueName);

        s.SendMessage(msgBody, url, msg);

        r.ReceiveMessage(url);

        q.DeleteQueue(queueName);

        q.ListQueues();
    }
}
