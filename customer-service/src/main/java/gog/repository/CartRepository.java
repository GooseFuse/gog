package gog.repository;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;

import gog.entity.CartEntity;

@ApplicationScoped
public class CartRepository implements PanacheRepository<CartEntity> {

}
