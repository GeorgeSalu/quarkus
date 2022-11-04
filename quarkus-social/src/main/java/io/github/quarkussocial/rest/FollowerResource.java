package io.github.quarkussocial.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.github.quarkussocial.domain.repository.FollowerRepository;
import io.github.quarkussocial.domain.repository.UserRepository;

@Path("/users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FollowerResource {

	private UserRepository userRepository;
	private FollowerRepository followerRepository;

	public FollowerResource(FollowerRepository followerRepository,UserRepository userRepository) {
		this.followerRepository = followerRepository;
		this.userRepository = userRepository;
	}
	
}
