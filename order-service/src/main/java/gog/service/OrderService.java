package gog.service;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.WebApplicationException;

import gog.entity.OrderEntity;
import gog.model.Order;
import gog.repository.AddressRepository;
import gog.repository.OrderItemRepository;
import gog.repository.PaymentRepository;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import gog.repository.OrderRepository;

public class OrderService {
    @Inject
    OrderRepository orderRepository;
    @Inject
    AddressRepository addressRepository;
    @Inject
    OrderItemRepository orderItemRepository;
    @Inject
    PaymentRepository paymentRepository;

    public Multi<Order> findAll() {
        return orderRepository.streamAll()
            .onItem().transform(OrderService::mapToDomain);
    }
    public Uni<Order> findById(Long id) {
        return orderRepository.findById(id)
        .onItem().ifNotNull().transform(OrderService::mapToDomain)
        .onItem().ifNull().failWith(() -> new WebApplicationException("Order not found", 404));
    }
    public void create(Order order) {

    }
    public Uni<Boolean> delete(Long id) {
        return Panache.withTransaction(() -> orderRepository.deleteById(id));
    }

    public static OrderEntity mapToEntity(Order order) {
        return new ObjectMapper().convertValue(order, OrderEntity.class);
    }
    public static Order mapToDomain(OrderEntity entity) {
        return new ObjectMapper().convertValue(entity, Order.class);
    }
}
