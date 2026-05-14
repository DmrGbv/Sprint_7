package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static data.EndpointAndUriData.ORDERS_LIST_GET;
import static data.EndpointAndUriData.ORDERS_POST;
import static io.restassured.RestAssured.given;

public class OrderSteps {

    @Step("Создание заказа")
    public static Response createOrder(Map<String, Object> orderData) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(orderData)
                .when()
                .post(ORDERS_POST)
                .then()
                .extract().response();
    }

    @Step("Получение списка заказов")
    public static Response getOrdersList() {
        return given()
                .log().all()
                .when()
                .get(ORDERS_LIST_GET)
                .then()
                .extract().response();
    }
}
