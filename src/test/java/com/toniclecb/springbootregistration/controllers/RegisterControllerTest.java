package com.toniclecb.springbootregistration.controllers;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toniclecb.springbootregistration.domain.mysql.domain.Register;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// TestInstance here permit we remove static from method setup()
@TestInstance(Lifecycle.PER_CLASS)
public class RegisterControllerTest {
	private RequestSpecification specification;
    private Register register;
    private UUID uuid;
    private Date date;

    @Value(value="${local.server.port}")
	private int port;

    @BeforeAll
	public void setup() {
        // this register will be used to insert into body of the request (.body(register)), after that will be used to make asserts
		register = new Register();
        uuid = UUID.randomUUID();
        register.setId(uuid);
        register.setDescription("some description");
        register.setName("Book");
        date = new Date();
        register.setCreateDate(date);

        specification = new RequestSpecBuilder()
				.setBasePath("/api/v1/register")
				.setPort(port)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}

    @Test
    public void testInsertRegister() throws JsonMappingException, JsonProcessingException {
        String response = given().spec(specification)
                .contentType(ContentType.JSON)
                .body(register)
                .when()
                .post()
            .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        
        System.out.println(">>> " + response);
        System.out.println("}}} " + register.toString());

        ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        Register savedRegister = objectMapper.readValue(response, Register.class);
		
        assertNotNull(response);
        assertEquals(register.getName(), savedRegister.getName());
        assertEquals(register.getDescription(), savedRegister.getDescription());
        assertEquals(register.getCreateDate().getTime(), savedRegister.getCreateDate().getTime());
    }

    // in this example we are testing everything, from controller, service, repository, query, etc.
    // not a unit test really...
    @Test
    public void testFindAll(){
        String response = given().spec(specification)
                .contentType(ContentType.JSON)
                .when()
                .get()
            .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        assertTrue(response.contains("content"));
        assertTrue(response.contains("totalElements"));
        assertTrue(response.contains("totalPages"));
    }
}
