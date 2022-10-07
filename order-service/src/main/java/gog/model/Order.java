package gog.model;

import java.math.BigDecimal;
import java.util.Set;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Order {
    private Long id;
    private String status;
    private Address shipmentAddress;
    private Payment payment;
    private Set<OrderItem> orderItems;
    private BigDecimal totalPrice;
}
