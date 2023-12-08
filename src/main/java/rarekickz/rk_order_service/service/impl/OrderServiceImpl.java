package rarekickz.rk_order_service.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rarekickz.rk_order_service.domain.Order;
import rarekickz.rk_order_service.dto.CreateOrderDTO;
import rarekickz.rk_order_service.repository.OrderRepository;
import rarekickz.rk_order_service.service.OrderService;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order create(final CreateOrderDTO createOrderDTO) {
        return null;
    }
}
