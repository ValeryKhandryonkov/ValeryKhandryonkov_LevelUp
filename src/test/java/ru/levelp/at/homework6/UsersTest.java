package ru.levelp.at.homework6;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Test;

public class UsersTest {

    RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
            .setBaseUri("https://gorest.co.in")
            .setBasePath("public/v2")
            .setAuth(RestAssured.oauth2("b99f5e25d5a3372d6c14b66e43a95a6e0b242fd7ba14173943ce9b517a0413f4"))
            .log(LogDetail.ALL)
            .build();
    }

    ResponseSpecification responseSpecificationForOkResponse() {
        return new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .expectStatusCode(200)
            .build();
    }

    @Test
    void getUsers() {
        RestAssured.given()
            .spec(requestSpecification())
            .when()
            .get("/users")
            .then()
            .spec(responseSpecificationForOkResponse());
    }
}
