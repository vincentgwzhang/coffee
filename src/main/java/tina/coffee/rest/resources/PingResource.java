package tina.coffee.rest.resources;

import org.springframework.http.MediaType;
import tina.coffee.rest.dto.PingDto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/ping")
public class PingResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON_VALUE)//if no such thing, then it will turn back xml format
    public Response ping(){
        PingDto dto = new PingDto();
        dto.setKey("key");
        dto.setValue("value");

        return Response.ok(dto).build();
    }
}
