import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.example.Courier;
import org.example.CourierMethods;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@DisplayName("Создание курьера - негативный сценарий (409)")
public class CreateCourierNegativeTest {

    private CourierMethods courierMethods = new CourierMethods();

    @Test
    @Tag("Создание курьера")
    @Tag("Негативный сценарий")
    @DisplayName("Создание двух одинаковых курьеров (409)")
    @Description("Тест для создания курьера с уже зарегистрированным полем login")
    public void createEqualCouriers409() {
        createCourier409(createCourier("Dannatillas", "3355", "Orlando"));
    }

    private void createCourier409(Courier courier) {
        // create couriers and check correct response
        courierMethods.create(courier);
        ValidatableResponse createSecondCourierResponse = courierMethods.create(courier);
        int createdCourierStatusCode = createSecondCourierResponse.extract().statusCode();
        assertEquals(createdCourierStatusCode, HttpStatus.SC_CONFLICT);
        String errorMessage = createSecondCourierResponse.extract().path("message");
        assertEquals(errorMessage, "Этот логин уже используется");
    }

    private Courier createCourier(String login, String password, String firstName) {
        Courier courier = new Courier();
        courier.setLogin(login);
        courier.setPassword(password);
        courier.setFirstName(firstName);
        return courier;
    }
}