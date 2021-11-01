package local.mocaccino.planning.entity;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class AuditoriumEntityTests {

    @Test
    public void constructorWithOnlyTheLabelParameterTest() {
        Auditorium auditorium = new Auditorium("someone");
        assertFalse(auditorium.getId() != null && auditorium.getLabel() != null);
    }

    @Test
    public void constructorWithBothParametersTest() {
        Auditorium auditorium = new Auditorium(1L, "someone");
        assertTrue(auditorium.getId() != null && auditorium.getLabel() != null);
    }
}
