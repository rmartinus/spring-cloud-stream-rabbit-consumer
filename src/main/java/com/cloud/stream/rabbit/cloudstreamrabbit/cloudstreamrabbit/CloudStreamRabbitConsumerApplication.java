package com.cloud.stream.rabbit.cloudstreamrabbit.cloudstreamrabbit;

import com.cloud.stream.rabbit.cloudstreamrabbit.cloudstreamrabbit.consumer.ConsumerChannels;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.messaging.SubscribableChannel;

@SpringBootApplication
@EnableBinding(ConsumerChannels.class)
public class CloudStreamRabbitConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudStreamRabbitConsumerApplication.class, args);
    }

    private IntegrationFlow incomingMessageFlow(SubscribableChannel incoming, String prefix) {
        Log log = LogFactory.getLog(getClass());
        return IntegrationFlows
                .from(incoming)
                .transform(String.class, String::toUpperCase)
                .handle(String.class,
                        (greeting, headers) -> {
                            log.info("greeting received in IntegrationFlow (" + prefix + "): " + greeting);
                            return null;
                        }).get();
    }

    @Bean
    @Primary
    IntegrationFlow direct(ConsumerChannels channels) {
        return incomingMessageFlow(channels.directed(), "directed");
    }

    @Bean
    IntegrationFlow broadcast(ConsumerChannels channels) {
        return incomingMessageFlow(channels.broadcasts(), "broadcasts");
    }
}

