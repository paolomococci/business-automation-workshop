package local.mocaccino.planning.entity;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class TimeslotEntityTests {

    @Test
    public void constructorWithThreeParametersTest() {
        Timeslot timeslot = new Timeslot(DayOfWeek.MONDAY, LocalTime.now(), LocalTime.now().plusMinutes(40));
        assertFalse(timeslot.getId() != null);
    }

    @Test
    public void constructorWithFourParametersTest() {
        Timeslot timeslot = new Timeslot(1L, DayOfWeek.MONDAY, LocalTime.now(), null);
        assertTrue(timeslot.getId() != null);
    }
}
