package rarekickz.rk_order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rarekickz.rk_order_service.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
