package gog.repository;

import javax.enterprise.context.ApplicationScoped;

import gog.entity.OrderEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

@ApplicationScoped
public class OrderRepository implements PanacheRepository<OrderEntity> {
    
}
