package rarekickz.rk_order_service.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rarekickz.rk_order_service.domain.Order;
import rarekickz.rk_order_service.dto.CreateOrderDTO;
import rarekickz.rk_order_service.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Long> createOrder(@RequestBody CreateOrderDTO createOrderDTO) {
        final Order order = orderService.create(createOrderDTO);
        return new ResponseEntity<>(order.getId(), HttpStatus.OK);
    }
}
