package gog.service;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;

import gog.entity.GameEntity;
import gog.model.Game;
import gog.repository.GameRepository;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class GameService {

    @Inject
    GameRepository gameRepository;

    public Multi<Game> findAll() {
        return gameRepository.streamAll()
        .onItem().transform(GameService::mapToDomain);
    }
    public Uni<Game> findById(Long id) {
        return gameRepository.findById(id)
        .onItem().transform(GameService::mapToDomain);
    }
    public Uni<Game> create(Game game) {
        return Panache.withTransaction(() -> gameRepository.persistAndFlush(mapToEntity(game)))
        .onItem().transform(GameService::mapToDomain);
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
