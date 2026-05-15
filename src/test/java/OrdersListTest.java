import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.*;
import static steps.OrderSteps.getOrdersList;

public class OrdersListTest extends BaseAPITest {

    @Test
    @DisplayName("Проверка успешного получения списка заказов")
    @Description("Тест для проверки успешного получения списка заказов в теле ответа")
    public void testGetOrdersList() {
        getOrdersList()
                .then().log().all()
                .statusCode(HTTP_OK)
                .body("orders", notNullValue());
    }
}