package com.toniclecb.springbootregistration.controllers;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.toniclecb.springbootregistration.domain.mysql.domain.Register;

import io.restassured.specification.RequestSpecification;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RegisterControllerTest {
    
	private static RequestSpecification specification;
    private static Register register;
    private static UUID uuid;
    private static Date date;

    @BeforeAll
	public static void setup() {
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
				.setPort(8081)
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
