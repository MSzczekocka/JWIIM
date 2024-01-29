package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.DatabaseService;
import model.Planet;

/**
 * Controller Class For Temperature And Distance Correlation Feature
 *
 * @author Martyna Szczekocka
 * @version 1.0
 */
@WebServlet(name = "TemperatureAndDistanceCorrelationController", urlPatterns = {"/TemperatureAndDistanceCorrelationController"})
public class TemperatureAndDistanceCorrelationController extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DatabaseService dbUtils = DatabaseService.getInstance("jdbc:h2:file:./lab", "app", "app");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            List<Planet> planets = dbUtils.getAllPlanets();

            double minTemperature = planets.stream()
                    .mapToDouble(Planet::getAverageTemperature)
                    .min()
                    .orElse(1);
            double maxTempeature = calculateMaxTeperature(planets);
            Map<Double, Double> result = new HashMap<>();
            for (Planet planet : planets) {
                double tempInPercent = (planet.getAverageTemperature() + Math.abs(minTemperature)) * 100 / maxTempeature;
                result.put(planet.getDistanceToTheSun(), tempInPercent);
            }

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Temperature and Distance Correlation</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Temperature and Distance Correlation</h2>");

            out.println("<table border='1'>");
            out.println("<tr><th>Distance to the Sun</th><th>Temperature (Percentage)</th></tr>");

            for (Map.Entry<Double, Double> entry : result.entrySet()) {
                out.println("<tr>");
                out.println("<td>" + entry.getKey() + "</td>");
                out.println("<td>" + entry.getValue() + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body>");
            out.println("</html>");

            out.println("<button onclick='goBack()'>Back</button>");
            out.println("<script>");
            out.println("function goBack() {");
            out.println("window.history.back();");
            out.println("}");
            out.println("</script>");
                            out.println("<p>Information from db </p>");

            
        } catch (SQLException ex) {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Error</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h2>Error</h2>");
                out.println("<p>SQL Exception</p>");
                out.println("<button onclick='goBack()'>Back</button>");
                out.println("<script>");
                out.println("function goBack() {");
                out.println("window.history.back();");
                out.println("}");
                out.println("</script>");

                out.println("</body>");
                out.println("</html>");
            }
        }
    }

    /**
     * Method calculating temperature which is equal 100%
     *
     * @param planetList - all planets in db
     * @return - max temperature = 100%
     */
    private double calculateMaxTeperature(List<Planet> planetList) {
        double maxTemperatureSum = planetList.stream()
                .mapToDouble(Planet::getAverageTemperature)
                .max()
                .orElse(1);
        double minTemperatureSum = planetList.stream()
                .mapToDouble(Planet::getAverageTemperature)
                .min()
                .orElse(1);
        return Math.abs(maxTemperatureSum) + Math.abs(minTemperatureSum);
    }
}
