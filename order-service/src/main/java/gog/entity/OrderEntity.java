package gog.entity;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.experimental.Accessors;

@Entity
@Table(name = "orders")
@Data
@Accessors(chain = true)
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Long cartId;

    @OneToOne
    @MapsId
    private AddressEntity address;

    @OneToOne
    @MapsId
    private PaymentEntity payment;

    @OneToMany
    @MapsId
    private Set<OrderItemEntity> orderItems;

    private BigDecimal totalPrice;
}
