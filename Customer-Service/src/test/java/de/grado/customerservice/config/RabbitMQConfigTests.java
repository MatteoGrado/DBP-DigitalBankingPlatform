package de.grado.customerservice.config;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RabbitMQConfigTests
{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange customerExchange;

    @Autowired
    private Queue customerQueue;

    @Autowired
    private Binding customerBinding;

    @Test
    void rabbitTopologyBeansAreAvailable()
    {
        assertThat(customerExchange.getName()).isEqualTo(RabbitMQConfig.EXCHANGE_NAME);
        assertThat(customerQueue.getName()).isEqualTo(RabbitMQConfig.QUEUE_NAME);
        assertThat(customerBinding.getRoutingKey()).isEqualTo(RabbitMQConfig.ROUTING_KEY);
        assertThat(rabbitTemplate.getMessageConverter()).isInstanceOf(Jackson2JsonMessageConverter.class);
    }

}
