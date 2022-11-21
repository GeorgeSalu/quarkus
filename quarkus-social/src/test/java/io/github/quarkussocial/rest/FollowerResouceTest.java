package io.github.quarkussocial.rest;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.github.quarkussocial.domain.model.Follower;
import io.github.quarkussocial.domain.model.User;
import io.github.quarkussocial.domain.repository.UserRepository;
import io.github.quarkussocial.rest.dto.FollowerRequest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(FollowerResource.class)
public class FollowerResouceTest {

	@Inject
	UserRepository userRepository;
	Long userId;
	Long followerId;
	
	@BeforeEach
	@Transactional
	void setup() {
		// usuario padrão dos testes
		var user = new User();
		user.setAge(30);
		user.setName("fulano");
		userRepository.persist(user);
		userId = user.getId();
		
		// o seguidor
		var follower = new User();
		follower.setAge(31);
		follower.setName("cicrano");
		userRepository.persist(follower);
		followerId = follower.getId();
	}
	
	@Test
	@DisplayName("should return 409 when Follower Id is equal to User id")
	public void sameUserAsFollowerTest() {
		var body = new FollowerRequest();
		body.setFollowerId(userId);
		
		given()
			.contentType(ContentType.JSON)
			.body(body)
			.pathParam("userId", userId)
		.when()
			.put()
		.then()
			.statusCode(Response.Status.CONFLICT.getStatusCode());
	}
	
	@Test
	@DisplayName("should return 404 when user id dosen't exists")
	public void sameNotFountTest() {
		var body = new FollowerRequest();
		body.setFollowerId(userId);
		
		var inexistenUserId = 999;
		
		given()
			.contentType(ContentType.JSON)
			.body(body)
			.pathParam("userId", inexistenUserId)
		.when()
			.put()
		.then()
			.statusCode(Response.Status.NOT_FOUND.getStatusCode());
	}
	
	@Test
	@DisplayName("should return 404 when user id dosen't exists")
	public void followerUserTese() {
		var body = new FollowerRequest();
		body.setFollowerId(followerId);
		
		
		given()
			.contentType(ContentType.JSON)
			.body(body)
			.pathParam("userId", userId)
		.when()
			.put()
		.then()
			.statusCode(Response.Status.NO_CONTENT.getStatusCode());
	}
	
}
