package local.mocaccino.planning;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class IcyPlanningTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/icy")
          .then()
             .statusCode(200)
             .body(is("Hello from IcyPlanning"));
    }

}