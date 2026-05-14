package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CourierModel;

import java.util.HashMap;
import java.util.Map;

import static data.EndpointAndUriData.*;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;

public class CourierSteps {

    @Step("Создание курьера")
    public static Response createCourier(CourierModel courier){
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(CREATE_COURIER_POST)
                .then()
                .extract().response();
    }

    @Step("Авторизация курьера с логином {login} и паролем {password}")
    public static Response loginCourier(String login, String password) {
        Map<String, String> dataLogIn = new HashMap<>();
        if (login != null) {
            dataLogIn.put("login", login);
        }
        if (password != null) {
            dataLogIn.put("password", password);
        }
        return given()
                .contentType(ContentType.JSON)
                .body(dataLogIn)
                .when()
                .post(LOGIN_COURIER_POST);
    }

    @Step("Получение ID курьера")
    public static Integer getCourierId(String login, String password) {
        Response response = loginCourier(login, password);

        if (response.statusCode() == HTTP_OK) {
            return response.path("id");
        }

        return null;
    }

    @Step("Удаление курьера с ID {courierId}")
    public static Response deleteCourier(int courierId) {
        return given()
                .log().all()
                .when()
                .delete("/api/v1/courier/" + courierId)
                .then()
                .extract().response();
    }
}