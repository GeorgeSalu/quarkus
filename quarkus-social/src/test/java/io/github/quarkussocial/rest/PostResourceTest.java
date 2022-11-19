package io.github.quarkussocial.rest;

import static io.restassured.RestAssured.given;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.github.quarkussocial.domain.model.User;
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
	Long userId;
	
	@BeforeEach
	@Transactional
	public void setup() {
		var user = new User();
		user.setAge(30);
		user.setName("fulano");
		
		userRepository.persist(user);
		userId = user.getId();
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
	
}
