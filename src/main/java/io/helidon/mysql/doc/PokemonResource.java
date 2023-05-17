package io.helidon.mysql.doc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import com.mysql.cj.xdevapi.Result;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/pokemons")
public class PokemonResource {
    private final PokemonService pokemonService;
    private final Integer defaultLimit = 5;

    @Inject
    public PokemonResource(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String defaultList() throws Exception{
        ArrayList<Object> pokemons = pokemonService.getPokemons(defaultLimit);
        return getResult(pokemons);
    }

    @GET
    @Path("/{limit}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listLimit(@PathParam("limit") Integer limit ) throws Exception{
        ArrayList<Object> pokemons = pokemonService
                .getPokemons(!Objects.isNull(limit) ? limit : defaultLimit );
        return getResult(pokemons);
    }

    @POST
    public long addPokemon(JsonObject jsonObject){
        return pokemonService
                .addPokemon(jsonObject.toString())
                .getAffectedItemsCount();
    }

    @DELETE
    @Path("/{id}")
    public long removePokemon(@PathParam("id") String id){
        return pokemonService
                .removePokemon(id)
                .getAffectedItemsCount();
    }

    @PUT
    @Path("/{id}/{name}")
    public long  updatePokemon(@PathParam("id") String id, @PathParam("name") String name){
        return pokemonService
                .updatePokemonName(id, name)
                .getAffectedItemsCount();
    }

    private static String getResult(List<?> pokemons){

        LinkedHashMap result = new LinkedHashMap();
        result.put("count", pokemons.size());
        result.put("pokemons", pokemons);

        return result.toString();
    }
}
