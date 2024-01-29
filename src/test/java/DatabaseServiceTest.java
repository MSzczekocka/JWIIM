
import java.sql.Connection;
import java.sql.ResultSet;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;
import model.DatabaseService;
import model.Planet;

/**
 * Class with test for DatabaseService.class
 *
 * @author Martyna Szczekocka
 * @version 1.0
 */
public class DatabaseServiceTest {

    /**
     * DB_URL - test db url DB_USER - db user DB_PASSWORD - db passowrd
     * databaseService - object DatabaseService.class
     */

    private static DatabaseService databaseService;

    /**
     * Method creating table before tests - also testing createTable() method
     *
     * @throws SQLException - during problem with db connection
     */
    @BeforeEach
    public void setUp() throws SQLException {
        databaseService = DatabaseService.getInstance("jdbc:h2:file:./db", "", "");
        databaseService.createTable();
    }

    /**
     * Method droping table after tests - testing also clearTable() method
     *
     * @throws SQLException - during problem with db connection
     */
    @AfterEach
    public void clearTable() throws SQLException {
        databaseService.clearTable();
    }

    /**
     * Test for getConnection() method
     *
     * @throws SQLException - during problem with db connection
     */
    @Test
    public void testGetConnection() throws SQLException {
        // Ensure that a valid database connection is obtained
        Connection connection = databaseService.getConnection();

        assertNotNull(connection);
        assertFalse(connection.isClosed());
        connection.close();
    }

    /**
     *Method checking if db exist
     * @param tableName - name of created table
     * @return - true or false 
     * @throws SQLException - during problem with connection to db
     */
    private boolean doesTableExist(String tableName) throws SQLException {
        try (Connection connection = databaseService.getConnection(); ResultSet resultSet = connection.getMetaData().getTables(null, null, tableName, null)) {
            return resultSet.next();
        }
    }

    /**
     * Test for getAllPlanets() and addPlanet() methods
     * @throws SQLException - during problem with connection to db
     */
    @Test
    public void testAddPlanetAndGetAllPlanets() throws SQLException {
        Planet planet = new Planet();
        planet.setName("Earth");
        planet.setMass(5.972e24);
        planet.setRotationPeriod(24.0);
        planet.setDayLength(24.0);
        planet.setDistanceToTheSun(149.6e6);
        planet.setAverageTemperature(15.0);
        planet.setHasMagneticField(true);
        databaseService.addPlanet(planet);

        List<Planet> allPlanets = databaseService.getAllPlanets();

        assertTrue(allPlanets.stream().anyMatch(p
                -> p.getName().equals(planet.getName())
                && Math.abs(p.getMass() - planet.getMass()) < 1E23
                && Math.abs(p.getRotationPeriod() - planet.getRotationPeriod()) < 1E-6
                && Math.abs(p.getDayLength() - planet.getDayLength()) < 1E-6
                && Math.abs(p.getDistanceToTheSun() - planet.getDistanceToTheSun()) < 1E23
                && Math.abs(p.getAverageTemperature() - planet.getAverageTemperature()) < 1E-6
                && p.isHasMagneticField() == planet.isHasMagneticField()
        ));
    }

    /**
     * Test for getPlanetsWithMagneticField() method
     * @throws SQLException - during problem with connection to db
     */
    @Test
    public void testGetPlanetsWithMagneticField() throws SQLException {
        Planet expectedPlanet = new Planet();
        expectedPlanet.setName("Magnetar");
        expectedPlanet.setMass(2.0E29);
        expectedPlanet.setRotationPeriod(2.0);
        expectedPlanet.setDayLength(2.0);
        expectedPlanet.setDistanceToTheSun(0.0);
        expectedPlanet.setAverageTemperature(2000.0);
        expectedPlanet.setHasMagneticField(true);

        databaseService.addPlanet(expectedPlanet);

        List<Planet> planetsWithMagneticField = databaseService.getPlanetsWithMagneticField();

        assertFalse(planetsWithMagneticField.isEmpty());

        Planet retrievedPlanet = planetsWithMagneticField.get(0);

        
        assertEquals(expectedPlanet.getName(), retrievedPlanet.getName());
        assertEquals(expectedPlanet.getRotationPeriod(), retrievedPlanet.getRotationPeriod(), 1E-6);
        assertEquals(expectedPlanet.getDayLength(), retrievedPlanet.getDayLength(), 1E-6);
        assertEquals(expectedPlanet.getDistanceToTheSun(), retrievedPlanet.getDistanceToTheSun(), 1E-6);
        assertEquals(expectedPlanet.getAverageTemperature(), retrievedPlanet.getAverageTemperature(), 1E-6);
        assertEquals(expectedPlanet.isHasMagneticField(), retrievedPlanet.isHasMagneticField());

        double expectedMass = expectedPlanet.getMass();
        double retrievedMass = retrievedPlanet.getMass();
        assertEquals(expectedMass, retrievedMass, 1E23);
    }

    /**
     * Test for getPlanetsSortedByMass() method
     * @throws SQLException - during problem with db connection
     */
    @Test
    public void testGetPlanetsSortedByMass() throws SQLException {
        Planet planet1 = new Planet();
        planet1.setName("Earth");
        planet1.setMass(5.972e24);
        planet1.setRotationPeriod(24.0);
        planet1.setDayLength(24.0);
        planet1.setDistanceToTheSun(149.6e6);
        planet1.setAverageTemperature(15.0);
        planet1.setHasMagneticField(true);

        Planet planet2 = new Planet();
        planet2.setName("Mars");
        planet2.setMass(6.4171e23);
        planet2.setRotationPeriod(24.6);
        planet2.setDayLength(24.7);
        planet2.setDistanceToTheSun(227.9e6);
        planet2.setAverageTemperature(-80.0);
        planet2.setHasMagneticField(false);

        databaseService.addPlanet(planet1);
        databaseService.addPlanet(planet2);

        List<Planet> sortedPlanets = databaseService.getPlanetsSortedByMass();

        assertFalse(sortedPlanets.isEmpty());

        double previousMass = 0.0;
        for (Planet planet : sortedPlanets) {
            assertTrue(planet.getMass() >= previousMass);
            previousMass = planet.getMass();
        }
    }
}
