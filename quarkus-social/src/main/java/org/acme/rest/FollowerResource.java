package org.acme.rest;

import java.util.stream.Collectors;

import org.acme.dto.FollowerRequest;
import org.acme.dto.FollowerResponse;
import org.acme.dto.FollowersPerUserResponse;
import org.acme.model.Follower;
import org.acme.repository.FollowerRepository;
import org.acme.repository.UserRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerResource {

	private UserRepository userRepository;
	private FollowerRepository repository;

	@Inject
	public FollowerResource(FollowerRepository repository, UserRepository userRepository) {
		this.repository = repository;
		this.userRepository = userRepository;
	}

	@PUT
	@Transactional
	public Response followerUser(@PathParam("userId") Long userId, FollowerRequest request) {

		if (userId.equals(request.getFollowerId())) {
			return Response.status(Response.Status.CONFLICT).entity("you can't follow yourself").build();
		}

		var user = userRepository.findById(userId);
		if (user == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		var follower = userRepository.findById(request.getFollowerId());

		boolean follows = repository.follows(follower, user);

		if (!follows) {

			var entity = new Follower();
			entity.setUser(user);
			entity.setFollower(follower);

			repository.persist(entity);
		}

		return Response.status(Response.Status.NO_CONTENT).build();
	}
	
	@GET
	public Response listFollowers(@PathParam("userId") Long userId) {
		var user = userRepository.findById(userId);
		if (user == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		var list = repository.findByUSer(userId);
		FollowersPerUserResponse responseObject = new FollowersPerUserResponse();
		
		var followerList = list.stream()
				.map(FollowerResponse::new)
				.collect(Collectors.toList());
		
		responseObject.setContent(followerList);
		return Response.ok(responseObject).build();
	}
	
	@DELETE
	@Transactional
	public Response unfollowUser(@PathParam("userId") Long userId,@QueryParam("followerId") Long followerId) {
		var user = userRepository.findById(userId);
		if (user == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		repository.deleteByFollowerAndUser(followerId, userId);
		
		return Response.status(Response.Status.NO_CONTENT).build();
	}

}
