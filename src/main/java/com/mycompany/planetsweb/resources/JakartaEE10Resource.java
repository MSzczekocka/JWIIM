package com.mycompany.planetsweb.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

/**
 * JakartaEE10Resource class
 *
 * @author Martyna Szczekocka
 * @version 1.0
 */
@Path("jakartaee10")
public class JakartaEE10Resource {
/**
 * 
 * @return - response object
 */
    @GET
    public Response ping() {
        return Response
                .ok("ping Jakarta EE")
                .build();
    }
}
