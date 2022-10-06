package gog.repository;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;

import gog.entity.CustomerEntity;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<CustomerEntity> {

}
