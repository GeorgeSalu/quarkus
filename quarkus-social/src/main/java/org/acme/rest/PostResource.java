package org.acme.rest;

import org.acme.dto.CreatePostRequest;
import org.acme.model.Post;
import org.acme.model.User;
import org.acme.repository.PostRepository;
import org.acme.repository.UserRepository;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users/{userId}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {
	
	private UserRepository userRepository;
	private PostRepository postRepository;

	@Inject
	public PostResource(UserRepository userRepository,PostRepository postRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}

	@POST
	@Transactional
	public Response savePost(@PathParam("userId") Long userId, CreatePostRequest request) {
		User user = userRepository.findById(userId);
		if(user == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		Post post = new Post();
		post.setText(request.getText());
		post.setUser(user);
		
		postRepository.persist(post);
		
		return Response.status(Response.Status.CREATED).build();
	}
	
	@GET
	public Response listPosts(@PathParam("userId") Long userId) {
		User user = userRepository.findById(userId);
		if(user == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		return Response.ok().build();
	}
	
}
