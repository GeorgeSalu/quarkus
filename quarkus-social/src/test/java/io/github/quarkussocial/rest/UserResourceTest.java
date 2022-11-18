package io.github.quarkussocial.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.github.quarkussocial.rest.dto.CreateUserRequest;
import io.github.quarkussocial.rest.dto.ResponseError;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

@QuarkusTest
public class UserResourceTest {

	@Test
	@DisplayName("should create an user successfully")
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
	}
}
