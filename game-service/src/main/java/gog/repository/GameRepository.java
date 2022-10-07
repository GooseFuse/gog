package gog.repository;

import javax.enterprise.context.ApplicationScoped;

import gog.entity.GameEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;

@ApplicationScoped
public class GameRepository implements PanacheRepository<GameEntity>{

}
