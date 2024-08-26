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

@DisplayName("Логин курьера - позитивный сценарий (200)")
public class LoginCourierPositiveTest {

    private String id;
    private CourierMethods courierMethods;
    private Courier courier;

    @Before
    public void setUp() {
        // create courier and check response
        courierMethods = new CourierMethods();
        courier = createCourier("Kotiruer", "12334", "pok");
        ValidatableResponse createCourierResponse = courierMethods.create(courier);
        int createdCourierStatusCode = createCourierResponse.extract().statusCode();
        assertEquals(createdCourierStatusCode, HttpStatus.SC_CREATED);
        boolean created = createCourierResponse.extract().path("ok");
        assertTrue(created);
    }

    @After
    public void clearUp() {
        // delete created courier
        ValidatableResponse deleteResponse = courierMethods.delete(id);
        int deleteResponseStatusCode = deleteResponse.extract().statusCode();
        assertEquals(deleteResponseStatusCode, HttpStatus.SC_OK);
        boolean deleted = deleteResponse.extract().path("ok");
        assertTrue(deleted);
    }

    @Test
    @Tag("Логин курьера")
    @Tag("Позитивный сценарий")
    @Description("Тест для проверки корректности логина курьера в системе")
    public void loginCourier200() {
        // login courier with correct credentials
        ValidatableResponse loginCourierResponse = courierMethods.login(CourierCredentials.from(courier));
        int loginCourierStatusCode = loginCourierResponse.extract().statusCode();
        assertEquals(loginCourierStatusCode, HttpStatus.SC_OK);
        id = loginCourierResponse.extract().path("id").toString();
        assertNotEquals(id, "0");
    }

    private Courier createCourier(String login, String password, String firstName) {
        Courier courier = new Courier();
        courier.setLogin(login);
        courier.setPassword(password);
        courier.setFirstName(firstName);
        return courier;
    }
}