package rarekickz.rk_order_service.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import rarekickz.rk_order_service.enums.OrderStatus;

import java.util.Set;

@Getter
@Setter
@Entity(name = "rk_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @OneToOne
    @JoinColumn(name = "delivery_info_id", referencedColumnName = "id")
    private DeliveryInfo deliveryInfo;

    @OneToMany(mappedBy = "order")
    private Set<OrderInventory> orderInventory;
}
