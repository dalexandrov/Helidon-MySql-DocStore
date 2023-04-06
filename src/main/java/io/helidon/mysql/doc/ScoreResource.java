package io.helidon.mysql.doc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.validation.constraints.Null;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/score")
public class ScoreResource {
    private final ScoreService scoreService;
    private final Integer defaultLimit = 50;

    @Inject
    public ScoreResource(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String defaultList() throws Exception{
        ArrayList<Object> scores = scoreService.getScores(defaultLimit);
        return getResult(scores);
    }

    @GET
    @Path("/{limit}")
    @Produces(MediaType.APPLICATION_JSON)
    public String listLimit(@PathParam("limit") Integer limit ) throws Exception{
        ArrayList<Object> scores = scoreService.getScores(!Objects.isNull(limit) ? limit : defaultLimit );
        return getResult(scores);
    }

    private static String getResult(List<?> scores){

        LinkedHashMap result = new LinkedHashMap();
        result.put("count", scores.size());
        result.put("scores", scores);

        return result.toString();
    }
}
