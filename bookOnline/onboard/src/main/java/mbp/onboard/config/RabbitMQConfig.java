package mbp.onboard.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${shows.seats.exchange}")
    private String exchange;

    @Value("${shows.seats.exchange.queue}")
    private String exchangeQueue;
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(exchange);
    }

    @Bean
    public Queue exchangeQueue() {
        return new Queue(exchangeQueue);
    }

    @Bean
    public Binding binding(Queue exchangeQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(exchangeQueue).to(fanoutExchange);
    }
}
