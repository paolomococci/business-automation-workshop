package local.mocaccino.planning.controller.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import local.mocaccino.planning.entity.Lecture;
import local.mocaccino.planning.entity.Timeslot;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TimeslotResourceTests {

    @Test
    @Order(1)
    public void resourceExistingTest() {
        List<Timeslot> timeslots = given().when().get("/timeslots")
                .then().statusCode(200).extract().body().jsonPath().getList(".", Timeslot.class);
        assertTrue(timeslots.isEmpty());
    }

    @Test
    @Order(2)
    public void createAndDeleteEntityTest() {
        Timeslot timeslot = given().when().contentType(ContentType.JSON)
                .body(new Timeslot())
                .post("/timeslots")
                .then().statusCode(201).extract().as(Timeslot.class);
        given().when().delete("/timeslots/{id}", timeslot.getId())
                .then().statusCode(204);
    }
}
