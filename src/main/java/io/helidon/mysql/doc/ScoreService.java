package io.helidon.mysql.doc;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.mysql.cj.xdevapi.Session;
import com.mysql.cj.xdevapi.Client;
import com.mysql.cj.xdevapi.ClientFactory;
import com.mysql.cj.xdevapi.Collection;
import com.mysql.cj.xdevapi.DbDoc;
import com.mysql.cj.xdevapi.DocResult;
import com.mysql.cj.xdevapi.Schema;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class ScoreService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ClientFactory clientFactory = new ClientFactory();
    private String url;
    private String schema;
    private String collection;
    private Client cli;

    @Inject
    public ScoreService(@ConfigProperty(name = "demo.user") String user,
                        @ConfigProperty(name = "demo.password") String password,
                        @ConfigProperty(name = "demo.host") String host,
                        @ConfigProperty(name = "demo.port") String port,
                        @ConfigProperty(name = "demo.schema") String schema,
                        @ConfigProperty(name = "demo.collection") String collection) {
        this.url = "mysqlx://" + user + ":" + password + "@" + host + ":" + port + "/";
        this.schema = schema;
        this.collection = collection;
        this.cli = clientFactory.getClient(this.url, "{\"pooling\":{\"enabled\":true, \"maxSize\":8,\"maxIdleTime\":30000, \"queueTimeout\":10000} }");
    }

    public ArrayList<Object> getScores(Integer limit) throws Exception {
        Session session = getSession();
        Schema schema = session.getSchema(this.schema);
        Collection col = schema.getCollection(this.collection);
        DocResult result = col.find().limit(limit).execute();
        ArrayList<Object> docs = cleanResults(result.fetchAll());
        session.close();
        return docs;
    }

    private Session getSession(){
        return cli.getSession();
    }

    private ArrayList<Object> cleanResults(List<DbDoc> docs) throws JsonProcessingException {
        ArrayList<Object> cleaned = new ArrayList<>();
        for( DbDoc doc : docs){
            cleaned.add( objectMapper.readTree(doc.toString()));
        }
        return cleaned;
    }

}
