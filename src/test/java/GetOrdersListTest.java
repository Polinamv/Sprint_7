import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.example.OrderMethods;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@DisplayName("Получение списка заказов - позитивный сценарий (200)")
public class GetOrdersListTest {

    private OrderMethods orderMethods;

    @Before
    public void setUp() {
        orderMethods = new OrderMethods();
    }

    @Tag("Получение списка заказов")
    @Tag("Позитивный сценарий")
    @DisplayName("Получение списка заказов с пустым телом")
    @Test
    public void getOrdersList200() {
        //get orders list and check response
        ValidatableResponse getOrdersListResponse = orderMethods.getOrdersList();
        int getOrdersListStatusCode = getOrdersListResponse.extract().statusCode();
        assertEquals(getOrdersListStatusCode, HttpStatus.SC_OK);
        ArrayList<Object> getOrdersListResponseBody = getOrdersListResponse.extract().path("orders");
        assertNotNull(getOrdersListResponseBody);
    }
}