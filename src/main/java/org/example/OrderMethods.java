package org.example;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderMethods extends RequestData implements EndPoints{

    @Step("Создание нового заказа")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getRequestSpec())
                .body(order)
                .post(POST_CREATE_ORDER)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrdersList() {
        return given()
                .spec(getRequestSpec())
                .get(GET_ORDERS_LIST)
                .then();
    }
}
