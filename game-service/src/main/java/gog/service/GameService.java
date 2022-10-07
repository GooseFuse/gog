package gog.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gog.entity.CategoryEntity;
import gog.entity.GameEntity;
import gog.model.Game;
import gog.repository.CategoryRepository;
import gog.repository.GameRepository;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class GameService {

    @Inject
    GameRepository gameRepository;

    @Inject
    CategoryRepository categoryRepository;

    public Multi<Game> findAll() {
        return gameRepository.streamAll()
        .onItem().transform(GameService::mapToDomain);
    }
    public Uni<Game> findById(Long id) {
        return gameRepository.findById(id)
        .onItem().transform(GameService::mapToDomain);
    }
    public boolean create(Game game) {
        CategoryEntity categoryEntity = categoryRepository.findById(game.getCategory().getId()).await().indefinitely();
        if(categoryEntity==null) return false;
        GameEntity entity = mapToEntity(game);
        entity.setCategory(categoryEntity);

        Panache.withTransaction(() -> gameRepository.persist(entity)).await().indefinitely();
        return true;
    }
    public Uni<Boolean> delete(Long id) {
        return Panache.withTransaction(() -> gameRepository.deleteById(id));
    }

    public static GameEntity mapToEntity(Game game) {
        return new ObjectMapper().convertValue(game, GameEntity.class);
    }

    public static Game mapToDomain(GameEntity entity) {
        return new ObjectMapper().convertValue(entity, Game.class);
    }
}
