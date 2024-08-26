package org.example;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

import static io.restassured.http.ContentType.JSON;

public class RequestData {

    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";

    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
                .setContentType(JSON)
                .setBaseUri(BASE_URI)
                .build();
    }
}