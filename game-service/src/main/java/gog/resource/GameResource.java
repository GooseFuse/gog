package gog.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.reactive.RestPath;

import gog.model.Game;
import gog.service.GameService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameResource {
    @Inject
    GameService gameService;

    @GET
    public Multi<Game> findAll() {
        return gameService.findAll();
    }

    @GET
    @Path("/{id}")
    public Uni<Game> findById(@RestPath Long id) {
        return gameService.findById(id);
    }

    @POST
    public Response create(Game game) {
        return gameService.create(game)? Response.status(Response.Status.CREATED).build()
        : Response.status(Response.Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(@RestPath Long id) {
        return gameService.delete(id)
        .map(deleted -> deleted ? Response.status(Response.Status.NO_CONTENT).build()
        : Response.status(Response.Status.NOT_FOUND).build());
    }
}