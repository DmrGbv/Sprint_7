package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CancelOrderModel;
import model.OrderModel;

import static data.EndpointAndUriData.*;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;

public class OrderSteps {

    @Step("Создание заказа")
    public static Response createOrder(OrderModel order) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(ORDERS_POST)
                .then()
                .extract().response();
    }

    @Step("Получение списка заказов")
    public static Response getOrdersList() {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .when()
                .get(ORDERS_LIST_GET)
                .then()
                .extract().response();
    }

    @Step("Отмена заказа с треком {track}")
    public static void cancelOrder(int track) {
            given()
                    .log().all()
                    .queryParam("track", track)
                    .when()
                    .put(CANCEL_ORDER_PUT)
                    .then()
                    .statusCode(HTTP_OK);
    }
}
