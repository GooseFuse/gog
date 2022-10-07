package gog.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;

import gog.entity.AddressEntity;
import gog.model.Address;
import gog.repository.AddressRepository;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class AddressService {
    @Inject
    AddressRepository addressRepository;

    public Multi<Address> findAll() {
        return addressRepository.streamAll().onItem().transform(AddressService::mapToDomain);
    }
    public Uni<Address> findById(Long id) {
        return addressRepository.findById(id)
        .onItem().transform(AddressService::mapToDomain);
    }
    public Uni<Address> create(Address address) {
        return Panache.withTransaction(() -> addressRepository.persistAndFlush(mapToEntity(address)))
        .onItem().transform(AddressService::mapToDomain);
    }
    public Uni<Boolean> delete(Long id) {
        return Panache.withTransaction(() -> addressRepository.deleteById(id));
    }


    public static AddressEntity mapToEntity(Address address) {
        return new ObjectMapper().convertValue(address, AddressEntity.class);
    }
    public static Address mapToDomain(AddressEntity entity) {
        return new ObjectMapper().convertValue(entity, Address.class);
    }
}
