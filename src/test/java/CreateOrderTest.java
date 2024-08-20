import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.example.Order;
import org.example.OrderMethods;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
@DisplayName("Создание нового заказа (201)")
public class CreateOrderTest {
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryTime;
    private String comment;
    private String[] color;
    private OrderMethods orderMethods;
    private Order order;

    public CreateOrderTest(String firstName, String lastName, String address, String metroStation, String phone,
                           int rentTime, String deliveryTime, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryTime = deliveryTime;
        this.comment = comment;
        this.color = color;
    }

    @Before
    public void setUp() {
        orderMethods = new OrderMethods();
        order = new Order();
        order.setFirstName(firstName);
        order.setLastName(lastName);
        order.setAddress(address);
        order.setMetroStation(metroStation);
        order.setPhone(phone);
        order.setRentTime(rentTime);
        order.setDeliveryTime(deliveryTime);
        order.setComment(comment);
        order.setColor(color);
    }

    @Parameterized.Parameters(name = "color={8}")
    public static Object[][] testData() {
        return new Object[][]{
                {"John", "Isaak", "New st. 45", "5", "89470099004", 5, "2024-09-09", "Hello!", new String[]{"BLACK"}},
                {"Methane", "Blue", "Old street, 118", "99", "+99336611832", 2, "2025-06-10", ";)", new String[]{"GREY"}},
                {"Alen", "Align", "Gray bolivar, 9", "63", "48237642333", 10, "2023-03-05", "Hello!", new String[]{"BLACK", "GREY"}},
                {"Chris", "Mono", "Saint square, 21", "19", "88005553535", 1, "2024-12-01", "Hola", null},
        };
    }

    @Tag("Создание заказа")
    @Tag("Позитивный сценарий")
    @Description("Тест на создание заказа с выбором цветов: Black/Grey")
    @Test
    public void createOrderTest201() {
        // create new order and check response
        ValidatableResponse createOrderResponse = orderMethods.create(order);
        int createdOrderStatusCode = createOrderResponse.extract().statusCode();
        assertEquals(createdOrderStatusCode, HttpStatus.SC_CREATED);
        int orderTrack = createOrderResponse.extract().path("track");
        assertTrue(orderTrack != 0);
    }
}