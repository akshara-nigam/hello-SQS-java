package com.example.hello.sqs;

import com.example.hello.sqs.sqs.Queue;
import com.example.hello.sqs.consumer.Receiver;
import com.example.hello.sqs.producer.Sender;
import com.example.hello.sqs.constants.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

//import static com.example.hello.sqs.constants.ConfigurationProperties.SQS_QUEUE;

@SpringBootApplication(scanBasePackages = {"com.example.hello.sqs"})
public class SQSApplication implements CommandLineRunner {

    @Autowired
    Queue q;

    @Autowired
    Receiver r;

    @Autowired
    Sender s;

    @Autowired
    Config config;

    public static void main(String[] args) {
        new SpringApplication(SQSApplication.class).run(args);
    }

    @Override
    public void run(String... args) {
        String msgBody = "Information about current NY Times fiction bestseller for week of 12/11/2016.";
        Map<String,String> msg = new HashMap<>();
        msg.put("Author", "John Grishma");
        msg.put("Title", "The Whistler");
        msg.put("WeeksOn", "6");
        String queueName = config.getSQS_QUEUE();

        q.ListQueues();
        q.CreateQueue(queueName);
        String url = q.GetQueueURL(queueName);

        s.SendMessage(msgBody, url, msg);
        r.ReceiveMessage(url);

        q.DeleteQueue(queueName);
    }
}
