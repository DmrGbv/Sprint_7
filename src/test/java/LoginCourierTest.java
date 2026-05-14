import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import model.CourierModel;
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
    public void testLoginCourierSuccess() {
        loginCourier(login, password)
                .then().log().all()
                .statusCode(HTTP_OK)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Проверка возникновения ошибки при попытке авторизации с несуществующими Логином и Паролем")
    public void testLoginCourierWithNotExistDataFail() {
        loginCourier(login + System.currentTimeMillis(), password + System.currentTimeMillis())
                .then().log().all()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Проверка возникновения ошибки при попытке авторизации без передачи Логина")
    public void testLoginCourierWithoutLoginFail() {
        loginCourier(null, password)
                .then().log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Проверка возникновения ошибки при попытке авторизации без передачи Пароля")
    public void testLoginCourierWithoutPasswordFail() {
        loginCourier(login, null)
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