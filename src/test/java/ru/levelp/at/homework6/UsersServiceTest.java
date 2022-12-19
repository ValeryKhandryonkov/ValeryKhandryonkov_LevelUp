package ru.levelp.at.homework6;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.levelp.at.homework6.model.CreateUserDataRequest;
import ru.levelp.at.homework6.model.UserData;
import ru.levelp.at.homework6.model.UserData.Genders;
import ru.levelp.at.homework6.model.UserData.Statuses;
import ru.levelp.at.homework6.service.UsersRequest;

class UsersServiceTest {

    static Stream<Arguments> userParametersDataProvider() {

        var users = new UsersRequest(requestSpecification())
            .getUsers()
            .then()
            .spec(responseSpecificationForOkResponse())
            .extract()
            .as(UserData[].class);

        return Stream.of(
            Arguments.of(Map.of("name", users[new Random().nextInt(5)].getName())),
            Arguments.of(Map.of("email", users[new Random().nextInt(5)].getEmail())),
            Arguments.of(Map.of("gender", Genders.values()[new Random().nextInt(Genders.values().length)].toString())),
            Arguments.of(Map.of("status", Statuses.values()[new Random().nextInt(Statuses.values().length)].toString()))
        );
    }

    static Stream<Arguments> parametersForCreatingUsersDataProvider() {

        var faker = new Faker();

        return Stream.of(
            Arguments.of(faker.name().firstName() + " " + faker.name().lastName(),
                faker.internet().emailAddress(),
                Genders.values()[new Random().nextInt(Genders.values().length)].toString(),
                Statuses.values()[new Random().nextInt(Statuses.values().length)].toString()),
            Arguments.of(faker.name().firstName() + " " + faker.name().lastName(),
                faker.internet().emailAddress(),
                Genders.values()[new Random().nextInt(Genders.values().length)].toString(),
                Statuses.values()[new Random().nextInt(Statuses.values().length)].toString()),
            Arguments.of(faker.name().firstName() + " " + faker.name().lastName(),
                faker.internet().emailAddress(),
                Genders.values()[new Random().nextInt(Genders.values().length)].toString(),
                Statuses.values()[new Random().nextInt(Statuses.values().length)].toString()),
            Arguments.of(faker.name().firstName() + " " + faker.name().lastName(),
                faker.internet().emailAddress(),
                Genders.values()[new Random().nextInt(Genders.values().length)].toString(),
                Statuses.values()[new Random().nextInt(Statuses.values().length)].toString())
        );
    }

    private UserData[] getUsersByParameters(Map<String, String> params) {

        return new UsersRequest(requestSpecification())
            .getUsersByParameters(params)
            .then()
            .spec(responseSpecificationForOkResponse())
            .statusCode(200)
            .extract()
            .as(UserData[].class);
    }

    static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
            .setBaseUri("https://gorest.co.in")
            .setBasePath("public/v2")
            .setContentType(ContentType.JSON)
            .setAuth(RestAssured.oauth2("b99f5e25d5a3372d6c14b66e43a95a6e0b242fd7ba14173943ce9b517a0413f4"))
            .log(LogDetail.ALL)
            .build();
    }

    static ResponseSpecification responseSpecificationForOkResponse() {
        return new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .build();
    }

    @Test
    void getUsersTest() {
        var rsBody = new UsersRequest(requestSpecification())
            .getUsers()
            .then()
            .spec(responseSpecificationForOkResponse())
            .statusCode(200)
            .extract()
            .as(UserData[].class);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(rsBody).extracting(UserData::getId).isNotNull();
            softly.assertThat(rsBody).extracting(UserData::getName).isNotNull();
            softly.assertThat(rsBody).extracting(UserData::getEmail).isNotNull();
            softly.assertThat(rsBody).extracting(UserData::getGender).isNotNull();
            softly.assertThat(rsBody).extracting(UserData::getStatus).isNotNull();
        });
    }

    @ParameterizedTest
    @MethodSource("userParametersDataProvider")
    void getUsersByParameterTest(Map<String, String> parameters) {

        if (parameters.containsKey("name")) {
            var rsBody = getUsersByParameters(parameters);
            assertThat(rsBody[0].getName()).isEqualTo(parameters.get("name"));

        } else if (parameters.containsKey("email")) {
            var rsBody = getUsersByParameters(parameters);
            assertThat(rsBody[0].getEmail()).isEqualTo(parameters.get("email"));

        } else if (parameters.containsKey("gender")) {
            var rsBody = getUsersByParameters(parameters);
            assertThat(rsBody).extracting(UserData::getGender).containsOnly(parameters.get("gender"));

        } else if (parameters.containsKey("status")) {
            var rsBody = getUsersByParameters(parameters);
            assertThat(rsBody).extracting(UserData::getStatus).containsOnly(parameters.get("status"));
        }
    }

    @ParameterizedTest
    @MethodSource("parametersForCreatingUsersDataProvider")
    void createUserTest(String name, String email, String gender, String status) {
        var rqBody = CreateUserDataRequest.builder()
            .name(name)
            .email(email)
            .gender(gender)
            .status(status)
            .build();

        UserData rsBody = new UsersRequest(requestSpecification())
            .createUser(rqBody)
            .then()
            .spec(responseSpecificationForOkResponse())
            .statusCode(201)
            .extract()
            .as(UserData.class);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(rsBody.getId()).isNotNull();
            softly.assertThat(rsBody.getName()).isEqualTo(rqBody.getName());
            softly.assertThat(rsBody.getEmail()).isEqualTo(rqBody.getEmail());
            softly.assertThat(rsBody.getGender()).isEqualTo(rqBody.getGender());
            softly.assertThat(rsBody.getStatus()).isEqualTo(rqBody.getStatus());
        });
    }
}
