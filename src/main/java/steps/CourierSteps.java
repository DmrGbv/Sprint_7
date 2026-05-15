package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CourierModel;
import model.LoginModel;

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
    public static Response loginCourier(LoginModel loginModel) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(loginModel)
                .when()
                .post(LOGIN_COURIER_POST)
                .then()
                .extract().response();
    }

    @Step("Получение ID курьера")
    public static Integer getCourierId(String login, String password) {
        LoginModel loginModel = new LoginModel(login, password);
        Response response = loginCourier(loginModel);

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