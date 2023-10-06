package mbp.order.service;


import mbp.order.domainobject.OrderCreateRequest;
import mbp.order.domainobject.OrderCreateResponse;
import mbp.order.entity.Order;
import mbp.order.util.OrderAction;

import java.math.BigInteger;

public interface OrderService {
    OrderCreateResponse createOrder(OrderCreateRequest request) throws Exception;

    Order cancelOrder(BigInteger orderId) throws IllegalStateException;

    Order updateOrderAction(BigInteger orderId, OrderAction newStatus) throws IllegalStateException;

}
