package rarekickz.rk_order_service.external.impl;

import com.rarekickz.proto.lib.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import rarekickz.rk_order_service.dto.SneakerDTO;
import rarekickz.rk_order_service.external.ExternalSneakerService;

import java.util.List;

@Service
public class ExternalSneakerServiceImpl implements ExternalSneakerService {

    @GrpcClient("sneakerService")
    private final SneakerServiceGrpc.SneakerServiceBlockingStub sneakerServiceBlockingStub;

    public ExternalSneakerServiceImpl() {
        final ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost", 9090)
                .usePlaintext()
                .build();
        sneakerServiceBlockingStub = SneakerServiceGrpc.newBlockingStub(managedChannel);
    }


    @Override
    public void reserve(final List<SneakerDTO> sneakers) {
        final List<SneakerRequest> sneakerRequests = sneakers.stream()
                .map(sneakerDTO -> SneakerRequest.newBuilder()
                        .setSneakerId(sneakerDTO.getId())
                        .setSneakerSize(sneakerDTO.getSize())
                        .build())
                .toList();
        final ReserveSneakersRequest reserveSneakersRequest = ReserveSneakersRequest.newBuilder()
                .addAllSneakers(sneakerRequests)
                .build();
        sneakerServiceBlockingStub.reserve(reserveSneakersRequest);
    }

    @Override
    public Double getTotalPrice(List<Long> sneakerIds) {
        final SneakerIdsRequest sneakerIdsRequest = SneakerIdsRequest.newBuilder()
                .addAllSneakerId(sneakerIds)
                .build();
        final OrderTotalPriceResponse orderTotalPrice = sneakerServiceBlockingStub.getSneakerPrice(sneakerIdsRequest);
        return orderTotalPrice.getPrice();
    }
}
