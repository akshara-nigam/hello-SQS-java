package com.example.hello.sqs.sqs;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.AfterMethod;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Queue.class})
public class QueueTest {

    @Mock
    Queue q;

    @MockBean
    private AmazonSQS sqs;

    private final String queueName = "Test-dummy";
    private final String expectedURL = "https://aws.mock-region/Test-dummy";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(QueueTest.class);
    }

    @AfterMethod
    public void afterEachMethod() {
        Mockito.reset(q);
    }

    @Test
    public void testCreateQueue() {
        CreateQueueResult expectedResult = new CreateQueueResult();
        expectedResult.setQueueUrl(this.expectedURL);

        Mockito.when(sqs.createQueue(this.queueName)).thenReturn(expectedResult);
        Mockito.doNothing().when(q).CreateQueue(this.queueName);

        q.CreateQueue(this.queueName);

        Mockito.verify(q, Mockito.times(1)).CreateQueue(this.queueName);
    }

    @Test
    public void testGetQueueURL() {
        GetQueueUrlResult expectedResult = new GetQueueUrlResult();
        expectedResult.setQueueUrl(this.expectedURL);

        // Mocking the Queue method
        Mockito.when(sqs.getQueueUrl(this.queueName)).thenReturn(expectedResult);
        Mockito.when(q.GetQueueURL(this.queueName)).thenReturn(this.expectedURL);

        String actual = q.GetQueueURL(this.queueName);
        assertEquals(this.expectedURL, actual);
    }

    @Test
    public void testListQueues() {
        ListQueuesResult expectedResult = new ListQueuesResult();
        List<String> urls = new ArrayList<>();
        urls.add(this.expectedURL);
        expectedResult.setQueueUrls(urls);

        Mockito.when(sqs.listQueues()).thenReturn(expectedResult);
        Mockito.doNothing().when(q).ListQueues();

        q.ListQueues();

        Mockito.verify(q, Mockito.times(1)).ListQueues();
    }

    @Test
    public void testDeleteQueue() {
        Mockito.when(sqs.deleteQueue(this.queueName)).thenReturn(new DeleteQueueResult());
        Mockito.doNothing().when(q).DeleteQueue(this.queueName);

        q.DeleteQueue(this.queueName);

        Mockito.verify(q, Mockito.times(1)).DeleteQueue(this.queueName);
    }

}
