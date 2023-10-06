package mbp.order.repo;

import mbp.order.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, BigInteger> {
}
