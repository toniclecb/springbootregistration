package com.toniclecb.springbootregistration.controllers;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ActiveProfiles;

import com.toniclecb.springbootregistration.domain.mysql.domain.Register;
import com.toniclecb.springbootregistration.services.RegisterService;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class RegisterControllerUnitTest {
    
    @MockBean
    private RegisterService registerService;

	private RequestSpecification specification;

    @Value(value="${local.server.port}")
	private int port;

    @BeforeAll
	public void setup() {
        specification = new RequestSpecBuilder()
				.setBasePath("/api/v1/register")
				.setPort(port)
					.addFilter(new RequestLoggingFilter(LogDetail.ALL))
					.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
				.build();
	}

    @Test
    public void testUnitServiceFindAll2(){
        Page<Register> pagedRegs = new PageImpl<Register>(
            Arrays.asList(getRegister(), getRegister(), getRegister(), getRegister(), getRegister(), getRegister())); // 6
        Mockito.when(registerService.findAll(org.mockito.ArgumentMatchers.any())).thenReturn(pagedRegs);

        JsonPath jsonPath = given().spec(specification)
                .contentType(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .extract().body().jsonPath();

        List<Register> registers =  jsonPath.getList("content", Register.class);
        Integer totalElements = jsonPath.get("totalElements");

        // testing the size returned in the content and in the pagination information
        assertEquals(pagedRegs.getNumberOfElements(), registers.size());
        assertEquals(totalElements, registers.size());
        // a new way of test: verify if findAll is called 1 time!
        verify(registerService, times(1)).findAll(Mockito.any());
    }

    private Register getRegister() {
        Register r = new Register();
        r.setCreateDate(new Date());
        r.setDescription("Desc");
        r.setId(UUID.randomUUID());
        r.setName("Desc");

        return r;
    }
}
