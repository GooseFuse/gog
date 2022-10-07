package gog.repository;

import javax.enterprise.context.ApplicationScoped;

import gog.entity.PaymentEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

@ApplicationScoped
public class PaymentRepository implements PanacheRepository<PaymentEntity> {
    
}
