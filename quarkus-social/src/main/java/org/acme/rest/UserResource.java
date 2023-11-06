package org.acme.rest;

import org.acme.dto.CreateUserRequest;
import org.acme.model.User;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	@POST
	@Transactional
	public Response createUser( CreateUserRequest userRequest ) {
		User user = new User();
		user.setAge(userRequest.getAge());
		user.setName(userRequest.getName());
		
		user.persist();
		
		return Response.ok(userRequest).build();
	}
	
	@GET
	public Response listAllUsers() {
		PanacheQuery<User> query = User.findAll();
		return Response.ok(query.list()).build();
	}
	
}
