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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Stream;
import org.assertj.core.api.SoftAssertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.levelp.at.homework6.model.CreateUserDataRequest;
import ru.levelp.at.homework6.model.UserData;
import ru.levelp.at.homework6.model.UserData.Genders;
import ru.levelp.at.homework6.model.UserData.Statuses;
import ru.levelp.at.homework6.service.UsersRequest;

class UsersServiceTest {

    static Stream<Arguments> userParametersDataProvider() {

        var users = getUsers();

        return Stream.of(
            Arguments.of(Map.of("name", users[new Random().nextInt(5)].getName())),
            Arguments.of(Map.of("email", users[new Random().nextInt(5)].getEmail())),
            Arguments.of(Map.of("gender", Genders.values()[new Random().nextInt(Genders.values().length)].toString())),
            Arguments.of(Map.of("status", Statuses.values()[new Random().nextInt(Statuses.values().length)].toString()))
        );
    }

    static Stream<Integer> userIdDataProvider() {
        var users = getUsers();
        List<Integer> userIds = new ArrayList<>();
        for (UserData user : users) {
            userIds.add(user.getId());
        }

        return Stream.of(userIds.get(0), userIds.get(1), userIds.get(2), userIds.get(3));
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

    private static UserData[] getUsers() {
        return new UsersRequest(requestSpecification())
            .getUsers()
            .then()
            .spec(responseSpecWithCode200())
            .extract()
            .as(UserData[].class);
    }

    private UserData[] getUsersByParameters(Map<String, String> params) {
        return new UsersRequest(requestSpecification())
            .getUsersByParameters(params)
            .then()
            .spec(responseSpecWithCode200())
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

    static ResponseSpecification responseSpecWithCode200() {
        return new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .expectStatusCode(200)
            .build();
    }

    static ResponseSpecification responseSpecWithCode201() {
        return new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .expectStatusCode(201)
            .build();
    }

    static ResponseSpecification responseSpecWithCode204() {
        return new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .expectStatusCode(204)
            .build();
    }

    static ResponseSpecification responseSpecWithCode404() {
        return new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .expectStatusCode(404)
            .expectBody("message", Matchers.equalTo("Resource not found"))
            .build();
    }

    @Test
    void getUsersTest() {
        var rsBody = new UsersRequest(requestSpecification())
            .getUsers()
            .then()
            .spec(responseSpecWithCode200())
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
    @MethodSource("userIdDataProvider")
    void getUserById(int id) {
        var rsBody = new UsersRequest(requestSpecification())
            .getUserById(id)
            .then()
            .spec(responseSpecWithCode200())
            .extract()
            .as(UserData.class);

        assertThat(rsBody.getId()).isEqualTo(id);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 22, 55555, 6000006})
    void getNonexistentUserByIdTest(int id) {
        new UsersRequest(requestSpecification())
            .getUserById(id)
            .then()
            .spec(responseSpecWithCode404());
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
            .spec(responseSpecWithCode201())
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

    @ParameterizedTest
    @MethodSource({"parametersForCreatingUsersDataProvider"})
    void updateUserTest(String name, String email, String gender, String status) {
        var users = getUsers();
        var userId = users[new Random().nextInt(5)].getId();

        var rqBody = CreateUserDataRequest.builder()
                                          .name(name)
                                          .email(email)
                                          .gender(gender)
                                          .status(status)
                                          .build();

        UserData rsBody = new UsersRequest(requestSpecification())
            .updateUserById(rqBody, userId)
            .then()
            .spec(responseSpecWithCode200())
            .extract()
            .as(UserData.class);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(rsBody.getId()).isEqualTo(userId);
            softly.assertThat(rsBody.getName()).isEqualTo(rqBody.getName());
            softly.assertThat(rsBody.getEmail()).isEqualTo(rqBody.getEmail());
            softly.assertThat(rsBody.getGender()).isEqualTo(rqBody.getGender());
            softly.assertThat(rsBody.getStatus()).isEqualTo(rqBody.getStatus());
        });
    }

    @ParameterizedTest
    @MethodSource({"parametersForCreatingUsersDataProvider"})
    void updateNonexistentUserTest(String name, String email, String gender, String status) {
        var userId = new Random().nextInt(888888 + 111111);

        var rqBody = CreateUserDataRequest.builder()
                                          .name(name)
                                          .email(email)
                                          .gender(gender)
                                          .status(status)
                                          .build();

        new UsersRequest(requestSpecification())
            .updateUserById(rqBody, userId)
            .then()
            .spec(responseSpecWithCode404());
    }

    @ParameterizedTest
    @MethodSource("userIdDataProvider")
    void deleteUserTest(int id) {
        new UsersRequest(requestSpecification())
            .deleteUserById(id)
            .then()
            .spec(responseSpecWithCode204());
    }
}
