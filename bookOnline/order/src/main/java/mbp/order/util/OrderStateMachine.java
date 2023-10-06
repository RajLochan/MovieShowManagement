package mbp.order.util;

import org.springframework.stereotype.Service;

@Service
public class OrderStateMachine {
    public static boolean isValidTransition(OrderAction currentAction, OrderAction newAction) {
        switch (currentAction) {
            case CREATED:
                return newAction == OrderAction.INVENTORY_BLOCKED;
            case INVENTORY_BLOCKED:
                return newAction == OrderAction.PAYMENT_PROCESSED || newAction == OrderAction.CANCELLED;
            case PAYMENT_PROCESSED:
                return newAction == OrderAction.INVENTORY_CONFIRMED || newAction == OrderAction.CANCELLED;
            case INVENTORY_CONFIRMED:
                return newAction == OrderAction.PROCESSED || newAction == OrderAction.CANCELLED;
            case PROCESSED:
                return newAction == OrderAction.FULFILLED || newAction == OrderAction.CANCELLED;
            case FULFILLED:
                // Once an order is fulfilled, no further state transitions are allowed
                return false;
            case CANCELLED:
                // Once an order is cancelled, no further state transitions are allowed
                return false;
            default:
                // Handle unexpected states
                return false;
        }
    }
}
