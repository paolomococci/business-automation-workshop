package local.mocaccino.planning;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class AntiHiccupTest {

    @Test
    public void mainViewEndpointTest() {
        given()
          .when().get("/")
          .then()
             .statusCode(200);
    }
}
