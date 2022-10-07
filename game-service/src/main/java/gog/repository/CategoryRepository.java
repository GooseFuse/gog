package gog.repository;

import javax.enterprise.context.ApplicationScoped;

import gog.entity.CategoryEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

@ApplicationScoped
public class CategoryRepository implements PanacheRepository<CategoryEntity>{

}
