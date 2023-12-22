package rarekickz.rk_order_service.service.impl;

import com.rarekickz.proto.lib.ReserveSneakersRequest;
import com.rarekickz.proto.lib.SneakerRequest;
import com.rarekickz.proto.lib.SneakerServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import rarekickz.rk_order_service.domain.Order;
import rarekickz.rk_order_service.dto.CreateOrderDTO;
import rarekickz.rk_order_service.dto.SneakerDTO;
import rarekickz.rk_order_service.enums.OrderStatus;
import rarekickz.rk_order_service.external.ExternalSneakerService;
import rarekickz.rk_order_service.repository.OrderRepository;
import rarekickz.rk_order_service.service.OrderService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ExternalSneakerService externalSneakerService;

    @Override
    public Order create(final CreateOrderDTO createOrderDTO) {
        externalSneakerService.reserve(createOrderDTO.getSneakers());
        Order order = createOrder(createOrderDTO);
        order = orderRepository.save(order);
        return order;
    }

    private Order createOrder(final CreateOrderDTO createOrderDTO) {
        final List<Long> sneakerIds = createOrderDTO.getSneakers().stream()
                .map(SneakerDTO::getId)
                .toList();
        final Double totalPrice = externalSneakerService.getTotalPrice(sneakerIds);
        return Order.builder()
                .totalPrice(totalPrice)
                .orderStatus(OrderStatus.ORDER_STOCK_RESERVED)
                .build();
    }
}
