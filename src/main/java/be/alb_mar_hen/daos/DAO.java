package be.alb_mar_hen.daos;

import java.net.URI;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.UriBuilder;
import com.sun.jersey.api.client.*;
import com.sun.jersey.api.client.config.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public abstract class DAO<T> {
    protected WebResource resource;
    protected Connection connection = null;

    public DAO(Connection conn) {
        this.connection = conn;
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        resource = client.resource(getBaseURI());
    }
    
    public DAO() {
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        resource = client.resource(getBaseURI());
    }
    
	public WebResource getResource() {
		return resource;
	}

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/Factory-flow-api/api").build();
    }
    
    public String sendPostRequest(String path, String input) {
        URI baseURI = getBaseURI();
        WebResource resource = Client.create().resource(baseURI);

        // Envoie la requête POST avec le corps de la requête
        ClientResponse response = resource.path(path)
                                          .type(MediaType.APPLICATION_JSON)
                                          .post(ClientResponse.class, input);

        // Vérifie le code de statut de la réponse
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            return response.getEntity(String.class); 
        } else {
            return "Error: " + response.getStatus();
        }
    }

    public abstract boolean create(T obj);
    public abstract boolean delete(int id);
    public abstract boolean update(T obj);
    public abstract T find(int id);
    public abstract List<T> findAll();
    public abstract List<T> findAll(Map<String, Object> criteria);
}
