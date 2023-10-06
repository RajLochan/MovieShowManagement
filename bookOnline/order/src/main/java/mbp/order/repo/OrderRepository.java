package mbp.order.repo;

import mbp.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, BigInteger> {
    @Query("select od from Order od where od.orderPaymentId = ?1")
    Optional<Order> findByOrderPaymentId(String orderPaymentId);
}
