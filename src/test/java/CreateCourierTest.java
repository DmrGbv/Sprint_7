import com.github.javafaker.Faker;
import io.qameta.allure.junit4.DisplayName;
import model.CourierModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static steps.CourierSteps.*;

public class CreateCourierTest extends BaseAPITest{
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
    }

    @Test
    @DisplayName("Проверка успешного создания курьера при заполнении всех полей")
    public void testCreateCourierSuccess() {
        courier = new CourierModel(login, password, firstName);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Проверка возникновения ошибки при попытке создания двух одинаковых курьеров")
    public void testCreateDuplicateCourierFail() {
        this.courier = new CourierModel(login, password, firstName);

        createCourier(this.courier);

        createCourier(this.courier)
                .then()
                .log().all()
                .statusCode(HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Проверка возникновения ошибки при попытке создания курьера без заполнения всех обязательных полей")
    public void testCreateCourierWithoutRequiredFieldsFail() {
        CourierModel courier = new CourierModel(null, null, null);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверка возникновения ошибки при попытке создания курьера без заполнения обязательного поля Логин")
    public void testCreateCourierWithoutLoginFail() {
        CourierModel courier = new CourierModel(null, password, firstName);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверка возникновения ошибки при попытке создания курьера без заполнения обязательного поля Пароль")
    public void testCreateCourierWithoutPasswordFail() {
        CourierModel courier = new CourierModel(login, null, firstName);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Проверка успешного создания курьера при заполнении обязательных полей Логин и Пароль и пустом поле Имя")
    public void testCreateCourierWithoutFirstnameSuccess() {
        courier = new CourierModel(login, password, null);

        createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));
    }

    @After
    public void tearDown() {
        Integer id = getCourierId(login, password);
        if (id != null) {
            deleteCourier(id);
        }
    }
}
