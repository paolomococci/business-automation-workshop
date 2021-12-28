package local.mocaccino.planning.controller.resource;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

import local.mocaccino.planning.entity.Lecture;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertFalse;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LectureResourceTests {

    @Test
    @Order(1)
    public void resourceExistingTest() {
        List<Lecture> lectures = given().when().get("/lectures")
                .then().statusCode(200).extract().body().jsonPath().getList(".", Lecture.class);
        assertFalse(lectures.isEmpty());
    }

    @Test
    @Order(2)
    public void createAndDeleteEntityTest() {
         Lecture lecture = given().when().contentType(ContentType.JSON)
                .body(new Lecture("some topic", "someone lecturer", "some audience"))
                .post("/lectures")
                .then().statusCode(201).extract().as(Lecture.class);
        given().when().delete("/lectures/{id}", lecture.getId())
                .then().statusCode(204);
    }
}
