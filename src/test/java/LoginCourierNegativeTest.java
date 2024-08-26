import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.example.CourierCredentials;
import org.example.CourierMethods;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@DisplayName("Логин курьера - негативный сценарий (404)")
public class LoginCourierNegativeTest {
    private CourierMethods courierMethods;

    @Before
    public void setUp() {
        courierMethods = new CourierMethods();
    }

    @Test
    @Tag("Логин курьера")
    @Tag("Негативный сценарий")
    @DisplayName("Логин курьера с несуществующими логин + пароль")
    public void loginCourier404() {
        String login = "Loakoa";
        String password = "1234";
        // login non-existent courier and check response
        CourierCredentials credentials = new CourierCredentials();
        credentials.setLogin(login);
        credentials.setPassword(password);
        ValidatableResponse loginCourierResponse = courierMethods.login(credentials);
        int loginCourierStatusCode = loginCourierResponse.extract().statusCode();
        assertEquals(loginCourierStatusCode, HttpStatus.SC_NOT_FOUND);
        String errorMessage = loginCourierResponse.extract().path("message");
        assertEquals(errorMessage, "Учетная запись не найдена");
    }
}