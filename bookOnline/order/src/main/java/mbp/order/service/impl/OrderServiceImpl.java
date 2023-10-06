package mbp.order.service.impl;



import jakarta.persistence.EntityNotFoundException;


import mbp.booking.util.Common;
import mbp.order.domainobject.BookShowResponse;
import mbp.order.entity.Order;
import mbp.order.entity.OrderDetail;
import mbp.order.domainobject.OrderCreateRequest;
import mbp.order.domainobject.OrderCreateResponse;
import mbp.order.pubsub.OrderEventPublisher;
import mbp.order.pubsub.event.OrderCreateEvent;
import mbp.order.repo.OrderDetailRepository;
import mbp.order.repo.OrderRepository;
import mbp.order.service.OrderService;
import mbp.order.service.client.BookingServiceApiClient;
import mbp.order.service.client.PaymentServiceApiClient;
import mbp.order.util.OrderStateMachine;

import mbp.order.util.OrderAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

@Service
@Transactional(isolation = Isolation.SERIALIZABLE)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private BookingServiceApiClient bookingServiceApiClient;

    @Value("${mbp.onboard.API_KEY}")
    public  String onboard_API_KEY;

    @Value("${mbp.booking.API_KEY}")
    public  String booking_API_KEY;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentServiceApiClient paymentServiceApiClient;
    @Autowired
    OrderEventPublisher orderEventPublisher;

    @Override
    public OrderCreateResponse createOrder(OrderCreateRequest request) throws Exception {
        BookShowResponse cartDetail = bookingServiceApiClient.getCart(booking_API_KEY,request.getCartId());
        OrderDetail orderDetail = orderDetailRepository.save(new OrderDetail());
        Order order = orderRepository.save(new Order(null, orderDetail, OrderAction.CREATED,
                Common.getTransactionId(),null, null, null, null));
        OrderCreateEvent event=new OrderCreateEvent();event.setOderId(order.getId());
        event.setCartId(cartDetail.getCartId());event.setOderCreatedTime(order.getCreated());
        orderEventPublisher.publishOrderCreateEvent(event);
        return new OrderCreateResponse(order.getId(), order.getOrderPaymentId(), OrderAction.CREATED);
    }

    @Override
    public Order updateOrderAction(BigInteger orderId, OrderAction newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        // Implement business logic for state transitions and validations
        if (OrderStateMachine.isValidTransition(order.getLastAction(), newStatus)) {
            order.setLastAction(newStatus);
            return orderRepository.save(order);
        } else {
            throw new IllegalStateException("Invalid state transition");
        }
    }
    @Override
    public Order cancelOrder(BigInteger orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        return updateOrderAction(orderId, OrderAction.CANCELLED);
    }

}
