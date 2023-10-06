package mbp.booking.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Value("${shows.seats.publisher.exchange}")
    private String exchange;

    @Value("${order.seat.lock.queue}")
    private String orderSeatQueue;

    @Value("${onboard.seat.lock.queue}")
    private String onboardSeatQueue;

    @Value("${order.create.queue}")
    private String orderCreateQueue;


    @Bean
    public Queue orderCreateQueue() {
        return new Queue(orderCreateQueue);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(exchange);
    }

    @Bean
    public Queue orderSeatQueue() {
        return new Queue(orderSeatQueue);
    }
    @Bean
    public Queue onboardSeatQueue() {
        return new Queue(onboardSeatQueue);
    }

    @Bean
    public Binding binding1(Queue orderSeatQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(orderSeatQueue).to(fanoutExchange);
    }

    @Bean
    public Binding binding2(Queue onboardSeatQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(onboardSeatQueue).to(fanoutExchange);
    }
}
