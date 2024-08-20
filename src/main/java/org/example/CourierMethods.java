package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CourierMethods extends RequestData implements EndPoints {

    @Step("Создание нового курьера")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getRequestSpec())
                .body(courier)
                .post(POST_CREATE_COURIER)
                .then();
    }

    @Step("Логин курьера в системе")
    public ValidatableResponse login(CourierCredentials credentials) {
        return given()
                .spec(getRequestSpec())
                .body(credentials)
                .post(POST_LOGIN)
                .then();
    }

    @Step("Удаление курьера по полю id")
    public ValidatableResponse delete(String id) {
        Map<String, Object> courierIdMap = new HashMap<>();
        courierIdMap.put("id", id);
        return given()
                .spec(getRequestSpec())
                .body(courierIdMap)
                .when()
                .delete(DELETE_COURIER + id)
                .then();
    }
}