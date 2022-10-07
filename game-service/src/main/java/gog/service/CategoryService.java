package gog.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gog.entity.CategoryEntity;
import gog.model.Category;
import gog.repository.CategoryRepository;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class CategoryService {

    @Inject
    CategoryRepository categoryRepository;

    public Multi<Category> findAll() {
        return categoryRepository.streamAll()
        .onItem().transform(CategoryService::mapToDomain);
    }
    public Uni<Category> findById(Long id) {
        return categoryRepository.findById(id)
        .onItem().ifNotNull().transform(CategoryService::mapToDomain)
        .onItem().ifNull().failWith(() -> new WebApplicationException("Category not found", 404));
    }
    public Uni<Category> create(Category category) {
        return Panache.withTransaction(() -> categoryRepository.persistAndFlush(mapToEntity(category)))
        .onItem().transform(CategoryService::mapToDomain);
    }
    public Uni<Boolean> delete(Long id) {
        return Panache.withTransaction(() -> categoryRepository.deleteById(id));
    }
    public static CategoryEntity mapToEntity(Category category) {
        return new ObjectMapper().convertValue(category, CategoryEntity.class);
    }
    public static Category mapToDomain(CategoryEntity entity) {
        return new ObjectMapper().convertValue(entity, Category.class);
    }
}
