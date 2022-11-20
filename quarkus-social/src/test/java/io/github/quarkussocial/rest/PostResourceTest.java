package io.github.quarkussocial.rest;

import static io.restassured.RestAssured.given;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.github.quarkussocial.domain.model.Follower;
import io.github.quarkussocial.domain.model.User;
import io.github.quarkussocial.domain.repository.FollowerRepository;
import io.github.quarkussocial.domain.repository.UserRepository;
import io.github.quarkussocial.rest.dto.CreatePostRequest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(PostResource.class)
public class PostResourceTest {

	@Inject
	UserRepository userRepository;
	@Inject
	FollowerRepository followerRepository;
	
	Long userId;
	Long userNotFollowerId;
	Long userFollowerId;
	
	@BeforeEach
	@Transactional
	public void setup() {
		// usuario padrao testes
		var user = new User();
		user.setAge(30);
		user.setName("fulano");
		
		userRepository.persist(user);
		userId = user.getId();
		
		// usuario que não segue ninguem
		var userNotFollower = new User();
		userNotFollower.setAge(33);
		userNotFollower.setName("cicrano");
		userRepository.persist(userNotFollower);
		userNotFollowerId = userNotFollower.getId();
		
		// usuario seguidor
		var userFollower = new User();
		userFollower.setAge(33);
		userFollower.setName("cicrano");
		userRepository.persist(userFollower);
		userFollowerId = userFollower.getId();
		
		Follower follower = new Follower();
		follower.setUser(user);
		follower.setFollower(userFollower);
		followerRepository.persist(follower);
	}
	
	@Test
	@DisplayName("should create a post for a user")
	public void createPostTest() {
		var postRequest = new CreatePostRequest();
		postRequest.setText("Some text");
		
		var userID = 1;
		
		given()
			.contentType(ContentType.JSON)
			.body(postRequest)
			.pathParam("userId", userID)
		.when()
			.post()
		.then()
			.statusCode(201);
		
	}
	
	@Test
	@DisplayName("should return 404 when trying to make a post for an inexistent user")
	public void postForAnInexistentUserTest() {
		var postRequest = new CreatePostRequest();
		postRequest.setText("Some text");
		
		var inexistentUserId = 999;
		
		given()
			.contentType(ContentType.JSON)
			.body(postRequest)
			.pathParam("userId", inexistentUserId)
		.when()
			.post()
		.then()
			.statusCode(404);
		
	}
	
	@Test
	@DisplayName("should  return 404 when user doesn't exist")
	public void listPostUserNotFoundTest() {
		var inexistentUserId = 999;
		
		given()
			.pathParam("userId", inexistentUserId)
		.when()
			.get()
		.then()
			.statusCode(404);
	}
	
	@Test
	@DisplayName("should  return 400 when followerId header is not present")
	public void listPostFollowerHeaderNotSendTest() {

		given()
			.pathParam("userId", userId)
		.when()
			.get()
		.then()
			.statusCode(400)
			.body(Matchers.is("You forgot the header followerId"));
	}
	
	@Test
	@DisplayName("should  return 400 when follower doesn't exist")
	public void listPostFollowerNotFoundTest() {

		var inexistenFollowerId = 999;
		
		given()
			.pathParam("userId", userId)
			.header("followerId", inexistenFollowerId)
		.when()
			.get()
		.then()
			.statusCode(400)
			.body(Matchers.is("Inexistent followerId"));
		
	}
	
	@Test
	@DisplayName("should  return 403 when follower ins't a follower")
	public void listPostNotFollower() {
		given()
			.pathParam("userId", userId)
			.header("followerId", userNotFollowerId)
		.when()
			.get()
		.then()
			.statusCode(403)
			.body(Matchers.is("You can't see tree posts"));
	}
	
	@Test
	@DisplayName("should return 200 posts")
	public void listPostsTest() {
		given()
			.pathParam("userId", userId)
			.header("followerId", userFollowerId)
		.when()
			.get()
		.then()
			.statusCode(200);
	}
	
}
