package de.grado.customerservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig
{

    public static final String EXCHANGE_NAME = "customer.exchange";
    public static final String QUEUE_NAME = "customer.queue";
    public static final String ROUTING_KEY = "customer.routing-key";

    @Bean
    public DirectExchange customerExchange()
    {
        return new DirectExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue customerQueue()
    {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public Binding customerBinding(Queue customerQueue, DirectExchange customerExchange)
    {
        return BindingBuilder.bind(customerQueue).to(customerExchange).with(ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter rabbitMessageConverter(ObjectMapper objectMapper)
    {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter rabbitMessageConverter)
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(rabbitMessageConverter);
        return rabbitTemplate;
    }

}
