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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
@DisplayName("Логин курьера - негативный сценарий (400)")
public class LoginCourierNegativeTests {

    private String login;
    private String password;
    private String id;
    private Courier courier;
    private CourierMethods courierMethods;

    public LoginCourierNegativeTests(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Before
    public void setUp() {
        courierMethods = new CourierMethods();
        courier = new Courier("Liloki", "12334", "pok");
        ValidatableResponse createCourierResponse = courierMethods.create(courier);
        int createdCourierStatusCode = createCourierResponse.extract().statusCode();
        assertEquals(createdCourierStatusCode, HttpStatus.SC_CREATED);
        boolean created = createCourierResponse.extract().path("ok");
        assertTrue(created);
    }

    @After
    public void clearUp() {
        // login courier to get id and delete courier
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

    @Parameterized.Parameters(name = "login={0}, password={1}")
    public static Object[][] testData() {
        return new Object[][]{
                {null, "12334"},
                {"Liloki", null},
                {"", "12334"},
                {"Liloki", ""}
        };
    }

    @Test(timeout = 5000)
    @Tag("Логин курьера")
    @Tag("Негативный сценарий")
    @Description("Логин курьера в системе с неверными/отсутствующими данными")
    public void loginCourier400() {
        try {
            CourierCredentials credentials = new CourierCredentials(login, password);
            ValidatableResponse loginCourierResponse = courierMethods.login(credentials);
            int loginCourierStatusCode = loginCourierResponse.extract().statusCode();
            assertEquals(loginCourierStatusCode, HttpStatus.SC_BAD_REQUEST);
            String errorMessage = loginCourierResponse.extract().path("message");
            assertEquals(errorMessage, "Недостаточно данных для входа");
        } catch (Exception e) {
            fail("Время выполнения теста (5 секунд) истекло." + e.getMessage());
        }
    }
}