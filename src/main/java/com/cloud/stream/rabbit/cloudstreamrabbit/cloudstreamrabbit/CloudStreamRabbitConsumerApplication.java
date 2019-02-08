package com.cloud.stream.rabbit.cloudstreamrabbit.cloudstreamrabbit;

import com.cloud.stream.rabbit.cloudstreamrabbit.cloudstreamrabbit.consumer.ConsumerChannels;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@SpringBootApplication
@EnableBinding(ConsumerChannels.class)
public class CloudStreamRabbitConsumerApplication {

    private static final Log log = LogFactory.getLog(CloudStreamRabbitConsumerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CloudStreamRabbitConsumerApplication.class, args);
    }

    @StreamListener(ConsumerChannels.DIRECTED)
    public void handleDirected(String msg) {
        log.info("Directed message: " + msg);
    }

    @StreamListener(ConsumerChannels.BROADCASTS)
    public void handleBroadcast(String msg) {
        log.info("Broadcast message: " + msg);
    }
}