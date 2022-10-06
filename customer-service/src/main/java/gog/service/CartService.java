package gog.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import gog.entity.CartEntity;
import gog.entity.CartStatus;
import gog.entity.CustomerEntity;
import gog.model.Cart;
import gog.repository.CartRepository;
import gog.repository.CustomerRepository;

@ApplicationScoped
public class CartService {
    @Inject
    CartRepository cartRepository;

    @Inject
    CustomerRepository customerRepository;

    public Multi<Cart> findAll() {
        return cartRepository.streamAll()
            .onItem().transform(CartService::mapToDomain);
    }
    public Uni<Cart> findById(Long id) {
        return cartRepository.findById(id)
            .onItem().ifNotNull().transform(CartService::mapToDomain)
            .onItem().ifNull().failWith(() -> new WebApplicationException("Failed to find cart", 404));
    }
    public Uni<Cart> findByCustomerId(Long customerId) {
        return cartRepository.find("customer_id", customerId).firstResult()
            .onItem().ifNotNull().transform(CartService::mapToDomain)
            .onItem().ifNull().failWith(() -> new WebApplicationException("Cart not found for customer id: "+customerId, 404));
    }
    public void create(Long customerId) {
        CustomerEntity customerEntity = customerRepository.findById(customerId).await().indefinitely();
        CartEntity entity = new CartEntity()
            .setCustomer(customerEntity)
            .setStatus(CartStatus.NEW);

        Panache.withTransaction(
            () -> cartRepository.persist(entity)
        ).await().indefinitely();
    }
    public Uni<Boolean> delete(Long id) {
        return Panache.withTransaction(() -> cartRepository.deleteById(id));
    }

    public static CartEntity mapToEntitry(Cart cart) {
        return new ObjectMapper().convertValue(cart, CartEntity.class);
    }
    public static Cart mapToDomain(CartEntity entity) {
        return new ObjectMapper().convertValue(entity, Cart.class);
    }
}
