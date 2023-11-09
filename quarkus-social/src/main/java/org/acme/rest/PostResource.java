package org.acme.rest;

import java.util.stream.Collectors;

import org.acme.dto.CreatePostRequest;
import org.acme.dto.PostResponse;
import org.acme.model.Post;
import org.acme.model.User;
import org.acme.repository.FollowerRepository;
import org.acme.repository.PostRepository;
import org.acme.repository.UserRepository;

import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
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
	private FollowerRepository followerRepository;

	@Inject
	public PostResource(UserRepository userRepository,PostRepository postRepository,FollowerRepository followerRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
		this.followerRepository = followerRepository;
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
	public Response listPosts(@PathParam("userId") Long userId,@HeaderParam("followerId") Long followerId) {
		User user = userRepository.findById(userId);
		if(user == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		
		if(followerId == null) {
			return Response
						.status(Response.Status.BAD_REQUEST)
						.entity("you forgot the header")
						.build();
		}
		
		User follower = userRepository.findById(followerId);
		
		if(follower == null) {
			return Response
						.status(Response.Status.BAD_REQUEST)
						.build();
		}
		
		boolean follows = followerRepository.follows(follower, user);
		if(!follows) {
			return Response.status(Response.Status.FORBIDDEN).entity("you cant see these post").build();
		}
		
		var query = postRepository.find("user",Sort.by("dataTime", Sort.Direction.Descending) , user);
		var list = query.list();
		
		var postResponse = list.stream()
			.map(post -> PostResponse.fromEntity(post))
			.collect(Collectors.toList());
		
		return Response.ok(postResponse).build();
	}
	
}
