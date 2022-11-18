package io.github.quarkussocial.rest;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.github.quarkussocial.rest.dto.CreateUserRequest;
import io.github.quarkussocial.rest.dto.ResponseError;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URL;
import java.util.List;
import java.util.Map;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserResourceTest {
	
	@TestHTTPResource("/users")
	URL apiUrl;

	@Test
	@DisplayName("should create an user successfully")
	@Order(1)
	public void createUserTest() {
		var user = new CreateUserRequest();
		user.setName("Fulano");
		user.setAge(30);
		
		var response = 
				given()
					.contentType(ContentType.JSON)
					.body(user)
				.when()
					.post("/users")
				.then()
					.extract().response();
		
		assertEquals(201, response.statusCode());
		assertNotNull(response.jsonPath().getString("id"));
	}
	
	@Test
	@DisplayName("should return error whene json is not valid")
	@Order(2)
	public void createUserValidationErroTest() {
		var user = new CreateUserRequest();
		user.setAge(null);
		user.setName(null);
		
		var response = 
				given()
					.contentType(ContentType.JSON)
					.body(user)
				.when()
					.post("/users")
				.then()
					.extract().response();
				
		
		assertEquals(ResponseError.UNPROCESSABLE_ENTITY_STATUS, response.statusCode());
		assertEquals("Validation Error", response.jsonPath().getString("message"));
		
		List<Map<String, String>> errors = response.jsonPath().getList("errors");
		
		assertNotNull(errors.get(0).get("message"));
		assertNotNull(errors.get(1).get("message"));
	}
	
	@Test
	@DisplayName("should list all users")
	@Order(3)
	public void listAllUsersTest() {
		given()
			.contentType(ContentType.JSON)
		.when()
			.get(apiUrl)
		.then()
			.statusCode(200)
			.body("size()", Matchers.is(1));
			
	}
}
