package local.mocaccino.planning;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/sample")
public class SamplePlanning {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sample() {
        return "Hello from SamplePlanning";
    }
}