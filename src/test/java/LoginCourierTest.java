import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CourierModel;
import model.LoginModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static steps.CourierSteps.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.*;

public class LoginCourierTest extends BaseAPITest {
    private CourierModel courier;
    private String login;
    private String password;
    private String firstName;

    @Before
    public void generateData() {
        Faker faker = new Faker();
        login = faker.name().lastName() + faker.regexify("[0-9]{4}");
        password = faker.regexify("[0-9]{4}");
        firstName = faker.name().firstName();
        courier = new CourierModel(login, password, firstName);
        createCourier(courier);
    }

    @Test
    @DisplayName("Проверка успешной авторизации курьера при заполнении обязательных полей")
    @Description("Тест для проверки успешной авторизации курьера при заполнении обязательных полей Логин и Пароль")
    public void testLoginCourierSuccess() {
        LoginModel loginModel = new LoginModel(login, password);
        loginCourier(loginModel)
                .then().log().all()
                .statusCode(HTTP_OK)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Проверка возникновения ошибки при попытке авторизации с несуществующим Логином")
    @Description("Тест для проверки невозможности авторизации курьера при вводе НЕверных данных в поле Логин и корректными данными в поле Пароль")
    public void testLoginCourierWithWrongLoginFail() {
        LoginModel loginModel = new LoginModel(login + System.currentTimeMillis(), password);
        loginCourier(loginModel)
                .then().log().all()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Проверка возникновения ошибки при попытке авторизации с неверным Паролем")
    @Description("Тест для проверки невозможности авторизации курьера при вводе НЕверных данных в поле Пароль и корректными данными в поле Логин")
    public void testLoginCourierWithWrongPasswordFail() {
        LoginModel loginModel = new LoginModel(login, password + System.currentTimeMillis());
        loginCourier(loginModel)
                .then().log().all()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Проверка возникновения ошибки при попытке авторизации без передачи Логина")
    @Description("Тест для проверки невозможности авторизации курьера при НЕ заполнении обязательного поля Логин и заполнении поля Пароль")
    public void testLoginCourierWithoutLoginFail() {
        LoginModel loginModel = new LoginModel(null, password);
        loginCourier(loginModel)
                .then().log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверка возникновения ошибки при попытке авторизации без передачи Пароля")
    @Description("Тест для проверки невозможности авторизации курьера при НЕ заполнении обязательного поля Пароль и заполнении поля Логин")
    public void testLoginCourierWithoutPasswordFail() {
        LoginModel loginModel = new LoginModel(login, null);
        loginCourier(loginModel)
                .then().log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @After
    public void tearDown() {
        Integer id = getCourierId(login, password);
        if (id != null) {
            deleteCourier(id);
        }
    }
}