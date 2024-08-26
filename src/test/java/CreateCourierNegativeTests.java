import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.example.Courier;
import org.example.CourierMethods;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
@DisplayName("Создание курьера - негативный сценарий (400)")
public class CreateCourierNegativeTests {

    private String login;
    private String password;
    private String firstName;
    private Courier courier;
    private CourierMethods courierMethods;

    public CreateCourierNegativeTests(String login, String password, String firstName) {
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

    @Parameterized.Parameters(name = "login={0}, password={1}, firstName={2}")
    public static Object[][] testData() {
        return new Object[][]{
                {null, "1234", "Leo"},
                {"Rafialla", null, "Okko"},
                {"", "1245", null},
                {"Danallater", "", null}
        };
    }

    @Test
    @Tag("Создание курьера")
    @Tag("Негативный сценарий")
    @Description("Создание курьера без добавления/заполнения обязательных полей")
    public void createCourier400() {
        // create courier and check correct response
        ValidatableResponse createCourierResponse = courierMethods.create(courier);
        int createdCourierStatusCode = createCourierResponse.extract().statusCode();
        assertEquals(createdCourierStatusCode, HttpStatus.SC_BAD_REQUEST);
        String errorMessage = createCourierResponse.extract().path("message");
        assertEquals(errorMessage, "Недостаточно данных для создания учетной записи");
    }
}