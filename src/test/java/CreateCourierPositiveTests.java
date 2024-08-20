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

@DisplayName("Создание курьера - позитивный сценарий (201)")
@RunWith(Parameterized.class)
public class CreateCourierPositiveTests {

    private String login;
    private String password;
    private String firstName;
    private String id;
    private Courier courier;
    private CourierMethods courierMethods;

    public CreateCourierPositiveTests(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Before
    public void setUp() {
        courierMethods = new CourierMethods();
        courier = new Courier();
        courier.setLogin(login);
        courier.setPassword(password);
        courier.setFirstName(firstName);
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

    @Parameterized.Parameters(name = "login={0}, password={1}, firstName={2}")
    public static Object[][] testData() {
        return new Object[][]{
                {"Lianardda", "1234", "Leo"},
                {"Rafialla", "1245", null}
        };
    }

    @Test
    @Tag("Создание курьера")
    @Tag("Позитивный сценарий")
    @Description("Создание курьера с заполнением всех полей")
    public void createCourier201() {
        // create courier and check correct response
        ValidatableResponse createCourierResponse = courierMethods.create(courier);
        int createdCourierStatusCode = createCourierResponse.extract().statusCode();
        assertEquals(createdCourierStatusCode, HttpStatus.SC_CREATED);
        boolean created = createCourierResponse.extract().path("ok");
        assertTrue(created);

        // login with created credentials
        ValidatableResponse loginCourierResponse = courierMethods.login(CourierCredentials.from(courier));
        int loginCourierStatusCode = loginCourierResponse.extract().statusCode();
        assertEquals(loginCourierStatusCode, HttpStatus.SC_OK);
        id = loginCourierResponse.extract().path("id").toString();
        assertNotEquals(id, "0");
    }
}