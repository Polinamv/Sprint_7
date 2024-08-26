import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.example.Courier;
import org.example.CourierCredentials;
import org.example.CourierMethods;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

@DisplayName("Создание курьера - негативный сценарий (409)")
public class CreateCourierNegativeTest {

    private CourierMethods courierMethods;
    private Courier courier;
    private String id;

    @Before
    public void setUp() {
        courierMethods = new CourierMethods();
        courier = new Courier("Dannatillas", "3355", "Orlando");
    }

    @After
    public void clearUp() {
        // login created courier
        ValidatableResponse loginCourierResponse = courierMethods.login(CourierCredentials.from(courier));
        int loginCourierStatusCode = loginCourierResponse.extract().statusCode();
        assertEquals(loginCourierStatusCode, HttpStatus.SC_OK);
        id = loginCourierResponse.extract().path("id").toString();
        assertNotEquals(id, "0");

        // delete created courier
        ValidatableResponse deleteResponse = courierMethods.delete(id);
        int deleteResponseStatusCode = deleteResponse.extract().statusCode();
        assertEquals(deleteResponseStatusCode, HttpStatus.SC_OK);
        boolean deleted = deleteResponse.extract().path("ok");
        assertTrue(deleted);
    }

    @Test
    @Tag("Создание курьера")
    @Tag("Негативный сценарий")
    @DisplayName("Создание двух одинаковых курьеров (409)")
    @Description("Тест для создания курьера с уже зарегистрированным полем login")
    public void createEqualCouriers409() {
        // create couriers and check correct response
        courierMethods.create(courier);
        ValidatableResponse createSecondCourierResponse = courierMethods.create(courier);
        int createdCourierStatusCode = createSecondCourierResponse.extract().statusCode();
        assertEquals(createdCourierStatusCode, HttpStatus.SC_CONFLICT);
        String errorMessage = createSecondCourierResponse.extract().path("message");
        assertEquals(errorMessage, "Этот логин уже используется");
    }
}