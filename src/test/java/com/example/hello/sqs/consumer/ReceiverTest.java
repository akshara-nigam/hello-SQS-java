package com.example.hello.sqs.consumer;

import com.amazonaws.services.dynamodbv2.xspec.M;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.DeleteMessageResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.AfterMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest(classes = {Receiver.class})
@RunWith(SpringRunner.class)
@PrepareForTest(Receiver.class)
public class ReceiverTest {

    @Mock
    Receiver r;

    @MockBean
    AmazonSQS sqs;

    private final String url = "https://aws.mock-region/Test-dummy";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(Receiver.class);
    }

    @Test
    public void testReceiveMessage() {

//        Receiver mock = PowerMockito.spy(new Receiver());

//        PowerMockito.doReturn(pollResult).when(mock, "PollMessage", url);

//        Mockito.when(mock.sqs.receiveMessage(expectedRequest).getMessages()).thenReturn(expectedMessageResult);
//        Mockito.doNothing().when(mock).ReceiveMessage(url);
//
//        mock.ReceiveMessage(url);
//
//        Mockito.verify(mock, Mockito.times(1)).ReceiveMessage(url);
//        PowerMockito.verifyPrivate(mock, Mockito.times(1)).invoke("PollMessage", url);


        Mockito.doNothing().when(r).ReceiveMessage(url);

        r.ReceiveMessage(url);

        Mockito.verify(r, Mockito.times(1)).ReceiveMessage(url);
    }

    @Test
    public void testPollMessage() throws Exception {

        Map<String, Map<String, Object>> pollResult = new HashMap<>();
        ReceiveMessageRequest expectedRequest = new ReceiveMessageRequest()
                .withQueueUrl(url)
                .withMessageAttributeNames("All");
        List<Message> expectedMessageResult = new ArrayList<>();


        Receiver mock = PowerMockito.spy(new Receiver());
        PowerMockito.when(mock, "PollMessage", url).thenReturn(pollResult);
//        PowerMockito.doNothing().when(r);

        r.ReceiveMessage(url);

        PowerMockito.verifyPrivate(mock, Mockito.times(1)).invoke("PollMessage", url);
//        PowerMockito.verifyNew(Receiver.class).withArguments(url);

    }

}
