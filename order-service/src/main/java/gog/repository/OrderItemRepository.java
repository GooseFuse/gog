package gog.repository;

import javax.enterprise.context.ApplicationScoped;

import gog.entity.OrderItemEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

@ApplicationScoped
public class OrderItemRepository implements PanacheRepository<OrderItemEntity> {
    
}
