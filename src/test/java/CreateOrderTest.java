import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.OrderModel;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import steps.OrderSteps;

import java.util.*;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.hamcrest.Matchers.notNullValue;
import static steps.OrderSteps.createOrder;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseAPITest {

    private final List<String> color;
    private Integer createdTrack;

    public CreateOrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Цвет(а): {0}")
    public static List<Object[]> orderColors() {
        return Arrays.asList(
                new Object[][]{
                        {List.of("BLACK")},
                        {List.of("GREY")},
                        {Arrays.asList("BLACK", "GREY")},
                        {null}
                }
        );
    }

    @Test
    @DisplayName("Проверка успешного создания заказа с выбором цвета и без него")
    @Description("Тест для проверки возможности создания заказа с указанием одного из цветов, сразу двух или без указания цвета, и получения track в теле ответа")
    public void testCreateOrderSuccess() {
        OrderModel orderData = new OrderModel(
                "Test",
                "Testov",
                "Test address",
                1,
                1234567890,
                1,
                "2026-05-20",
                "test",
                color
        );

        Response response = createOrder(orderData)
                .then().log().all()
                .statusCode(HTTP_CREATED)
                .body("track", notNullValue())
                .extract().response();

        createdTrack = response.path("track");

    }

    @After
    public void cancelOrder() {
        if (createdTrack != null) {
            OrderSteps.cancelOrder(createdTrack);
        }
    }
}