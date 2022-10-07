package gog.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderItem {
    private Long id;
    private Long productId;

}
