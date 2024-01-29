package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class responsible for db connection
 *
 * @author Martyna Szzekocka
 * @version 1.0
 */
public class DatabaseService {

    /**
     * instance - placeholder for singleton object, url - db url, user - user
     * name, password - user password
     */
    private static DatabaseService instance;
    private final String url;
    private final String user;
    private final String password;

    /**
     * Constructor
     *
     * @param url - db url,
     * @param user - db user,
     * @param password - user password
     */
    private DatabaseService(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    /**
     * Object provider
     *
     * @param url - db url
     * @param user - db user
     * @param password - user password
     * @return - class object
     */
    public static synchronized DatabaseService getInstance(String url, String user, String password) {
        if (instance == null) {
            instance = new DatabaseService(url, user, password);
        }
        return instance;
    }

    /**
     * Creating planets tables
     *
     * @throws SQLException - during problem with connection to db
     */
    public void createTable() throws SQLException {
        String createQuery = "CREATE TABLE IF NOT EXISTS Planet (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), mass REAL, rotationPeriod REAL, dayLength REAL, distanceToTheSun REAL, averageTemperature REAL, hasMagneticField BOOLEAN);";
        try (Connection connection = this.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(createQuery);
        }
    }

    /**
     * Delete plants table
     *
     * @throws SQLException - during problem with connection to db
     */
    public void clearTable() throws SQLException {
        String createQuery = "DROP TABLE IF EXISTS Planet";
        try (Connection connection = this.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(createQuery);
        }
    }

    /**
     * Provide connection to db
     *
     * @return - connection to db
     * @throws SQLException - during problem with connection to db
     */
    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(true);
        return connection;
    }

    /**
     * Adding planet to db
     *
     * @param newPlanet - planet to add
     * @throws SQLException - during problem with connection to db
     */
    public void addPlanet(Planet newPlanet) throws SQLException {
        String insertQuery = "INSERT INTO Planet (name, mass, rotationPeriod, dayLength, distanceToTheSun, averageTemperature, hasMagneticField) VALUES (?, ?, ?, ?, ?, ?, ?);";

        try (Connection connection = this.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, newPlanet.getName());
            preparedStatement.setDouble(2, newPlanet.getMass());
            preparedStatement.setDouble(3, newPlanet.getRotationPeriod());
            preparedStatement.setDouble(4, newPlanet.getDayLength());
            preparedStatement.setDouble(5, newPlanet.getDistanceToTheSun());
            preparedStatement.setDouble(6, newPlanet.getAverageTemperature());
            preparedStatement.setBoolean(7, newPlanet.isHasMagneticField());

            preparedStatement.executeUpdate();
        }

    }

    /**
     * Return all planets sorted by mass
     *
     * @return - all planets sorted by mass
     * @throws SQLException - during problem with connection to db
     */
    public List<Planet> getPlanetsSortedByMass() throws SQLException {
        String query = "SELECT * FROM Planet ORDER BY mass;";
        List<Planet> allPlanets = new ArrayList<>();

        try (Connection connection = this.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Planet planet = new Planet();
                planet.setId(resultSet.getInt("id"));
                planet.setName(resultSet.getString("name"));
                planet.setMass(resultSet.getDouble("mass"));
                planet.setRotationPeriod(resultSet.getDouble("rotationPeriod"));
                planet.setDayLength(resultSet.getDouble("dayLength"));
                planet.setDistanceToTheSun(resultSet.getDouble("distanceToTheSun"));
                planet.setAverageTemperature(resultSet.getDouble("averageTemperature"));
                planet.setHasMagneticField(resultSet.getBoolean("hasMagneticField"));

                allPlanets.add(planet);
            }
        }

        return allPlanets;
    }

    /**
     * return all planets with magnetic field
     *
     * @return - planets with magnetic field
     * @throws SQLException - during problem with connection to db
     */
    public List<Planet> getPlanetsWithMagneticField() throws SQLException {
        String query = "SELECT * FROM Planet WHERE hasMagneticField = true;";
        List<Planet> allPlanets = new ArrayList<>();

        try (Connection connection = this.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Planet planet = new Planet();
                planet.setId(resultSet.getInt("id"));
                planet.setName(resultSet.getString("name"));
                planet.setMass(resultSet.getDouble("mass"));
                planet.setRotationPeriod(resultSet.getDouble("rotationPeriod"));
                planet.setDayLength(resultSet.getDouble("dayLength"));
                planet.setDistanceToTheSun(resultSet.getDouble("distanceToTheSun"));
                planet.setAverageTemperature(resultSet.getDouble("averageTemperature"));
                planet.setHasMagneticField(resultSet.getBoolean("hasMagneticField"));

                allPlanets.add(planet);
            }
        }

        return allPlanets;
    }

    /**
     * return all planets from db
     *
     * @return - all planets
     * @throws SQLException - during problem with connection to db
     */
    public List<Planet> getAllPlanets() throws SQLException {
        String query = "SELECT * FROM Planet;";
        List<Planet> allPlanets = new ArrayList<>();

        try (Connection connection = this.getConnection(); Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Planet planet = new Planet();
                planet.setId(resultSet.getInt("id"));
                planet.setName(resultSet.getString("name"));
                planet.setMass(resultSet.getDouble("mass"));
                planet.setRotationPeriod(resultSet.getDouble("rotationPeriod"));
                planet.setDayLength(resultSet.getDouble("dayLength"));
                planet.setDistanceToTheSun(resultSet.getDouble("distanceToTheSun"));
                planet.setAverageTemperature(resultSet.getDouble("averageTemperature"));
                planet.setHasMagneticField(resultSet.getBoolean("hasMagneticField"));

                allPlanets.add(planet);
            }
        }
        return allPlanets;
    }
}
