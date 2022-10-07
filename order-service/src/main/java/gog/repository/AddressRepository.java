package gog.repository;

import javax.enterprise.context.ApplicationScoped;

import gog.entity.AddressEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

@ApplicationScoped
public class AddressRepository implements PanacheRepository<AddressEntity>{
    
}
