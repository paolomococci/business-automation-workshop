package local.mocaccino.planning;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/icy")
public class IcyPlanning {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String icy() {
        return "Hello from IcyPlanning";
    }
}