package org.acme.rest;

import org.acme.repository.FollowerRepository;
import org.acme.repository.UserRepository;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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
	
}
