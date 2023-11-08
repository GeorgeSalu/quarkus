package org.acme.rest;

import org.acme.dto.FollloweRequest;
import org.acme.model.Follower;
import org.acme.repository.FollowerRepository;
import org.acme.repository.UserRepository;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerResource {

	private UserRepository userRepository;
	private FollowerRepository repository;

	@Inject
	public FollowerResource(FollowerRepository repository,UserRepository userRepository) {
		this.repository = repository;
		this.userRepository = userRepository;
	}
	
	@PUT
	public Response followerUser(@PathParam("userId") Long userId,FollloweRequest request) {
		var user = userRepository.findById(userId);
		if(user == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		var follower = userRepository.findById(request.getFollowerId());
		
		var entity = new Follower();
		entity.setUser(user);
		entity.setFollower(follower);
		
		repository.persist(entity);
		
		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
}
