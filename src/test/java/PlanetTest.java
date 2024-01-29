import exception.DividingByZeroException;
import model.Planet;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

/**
 * Class with test for Planet.class
 * @author Martyna Szczekocka
 * @version 1.0
 */
public class PlanetTest {
    
    /**
     * Test for constructor
     */
    @Test
    public void testPlanetConstructor() {
        Planet planet = new Planet(1, "Earth", 5.972e24, 24.0, 24.0, 149.6e6, 15.0, true);

        assertEquals("Earth", planet.getName());
        assertEquals(5.972e24, planet.getMass(), 1E-6);
        assertEquals(24.0, planet.getRotationPeriod(), 1E-6);
        assertEquals(24.0, planet.getDayLength(), 1E-6);
        assertEquals(149.6e6, planet.getDistanceToTheSun(), 1E-6);
        assertEquals(15.0, planet.getAverageTemperature(), 1E-6);
        assertTrue(planet.isHasMagneticField());
    }

    /**
     * Test for coutPearsonCorrelationCoefficient method
     */
    @Test
    public void testPearsonCorrelationCoefficient() {
        Planet planet = new Planet(1, "TestPlanet", 100.0, 0.0, 24.0, 149.6e6, 15.0, true);

        DividingByZeroException exception = assertThrows(DividingByZeroException.class, () -> {
            planet.coutPearsonCorrelationCoefficient();
        }, "Method should thows DividingByZeroException");

        assertEquals("Dividing By Zero", exception.getMessage(), "Wrong exception message");  

        planet.setRotationPeriod(10.0);
        double coefficient = planet.coutPearsonCorrelationCoefficient();

        assertEquals(2.4, coefficient, 1E-6);
    }

}
