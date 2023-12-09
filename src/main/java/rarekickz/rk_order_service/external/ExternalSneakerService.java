package rarekickz.rk_order_service.external;

import rarekickz.rk_order_service.dto.SneakerDTO;

import java.util.List;

public interface ExternalSneakerService {

    void reserve(List<SneakerDTO> sneakers);
}
