package rarekickz.rk_order_service.external.impl;

import com.rarekickz.proto.lib.ReserveSneakersRequest;
import com.rarekickz.proto.lib.SneakerRequest;
import com.rarekickz.proto.lib.SneakerServiceGrpc;
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
        ManagedChannel managedChannel = ManagedChannelBuilder
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
        ReserveSneakersRequest reserveSneakersRequest = ReserveSneakersRequest.newBuilder()
                .addAllSneakers(sneakerRequests)
                .build();
        sneakerServiceBlockingStub.reserve(reserveSneakersRequest);
    }
}
