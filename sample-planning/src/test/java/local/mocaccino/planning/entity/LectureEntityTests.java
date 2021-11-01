package local.mocaccino.planning.entity;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

@QuarkusTest
public class LectureEntityTests {

    @Test
    public void constructorWithThreeParametersTest() {
        Lecture lecture = new Lecture("some topic", "some lecturer", "some audience");
        assertFalse(lecture.getId() != null);
    }
}
