package io.github.quarkussocial.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

	public Response savePost() {
		return Response.status(Response.Status.CREATED).build();
	}
	
	public Response listPost() {
		return Response.ok().build();
	}
	
}
