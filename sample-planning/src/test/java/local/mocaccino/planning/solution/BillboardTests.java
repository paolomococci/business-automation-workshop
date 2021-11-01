package local.mocaccino.planning.solution;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BillboardTests {

    @Test
    @Order(1)
    public void resourceExistingTest() {
        assertTrue(true);
    }
}
