package gog.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

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
        .onItem().transform(CategoryService::mapToDomain);
    }
    public Uni<Category> create(Category category) {
        return Panache.withTransaction(() -> categoryRepository.persistAndFlush(mapToEntity(category)))
        .onItem().transform(CategoryService::mapToDomain);
    }

    public static CategoryEntity mapToEntity(Category category) {
        return new ObjectMapper().convertValue(category, CategoryEntity.class);
    }
    public static Category mapToDomain(CategoryEntity entity) {
        return new ObjectMapper().convertValue(entity, Category.class);
    }
}
