package local.mocaccino.planning.controller.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import local.mocaccino.planning.entity.Auditorium;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuditoriumResourceTests {

    @Test
    @Order(1)
    public void resourceExistingTest() {
        List<Auditorium> auditoriums = given().when().get("/auditoriums")
                .then().statusCode(200).extract().body().jsonPath().getList(".", Auditorium.class);
        assertTrue(auditoriums.isEmpty());
    }

    @Test
    @Order(2)
    public void createAndDeleteEntityTest() {
        Auditorium auditorium = given().when().contentType(ContentType.JSON)
                .body(new Auditorium("someone"))
                .post("/auditoriums")
                .then().statusCode(201).extract().as(Auditorium.class);
        given().when().delete("/auditoriums/{id}", auditorium.getId())
                .then().statusCode(204);
    }
}
