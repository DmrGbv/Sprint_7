import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.*;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.hamcrest.Matchers.notNullValue;
import static steps.OrderSteps.createOrder;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseAPITest {

    private final List<String> color;

    public CreateOrderTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
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
    public void testCreateOrderSuccess() {
        Map<String, Object> orderData = new HashMap<>();
        orderData.put("firstName", "Test");
        orderData.put("lastName", "Testov");
        orderData.put("address", "Test address");
        orderData.put("metroStation", 1);
        orderData.put("phone", 1234567890);
        orderData.put("rentTime", 1);
        orderData.put("deliveryDate", "2026-05-20");
        orderData.put("comment", "test");
        orderData.put("color", color);

        createOrder(orderData)
                .then().log().all()
                .statusCode(HTTP_CREATED)
                .body("track", notNullValue());
    }
}