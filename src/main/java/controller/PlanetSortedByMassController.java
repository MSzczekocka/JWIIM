package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.io.*;
import java.sql.SQLException;
import model.DatabaseService;
import model.Planet;

/**
 * Class for control Planet Sorted By Mass feature
 *
 * @author Martyna Szczekocka
 * @version 1.0 Planet Sorted By Mass Feature Controller
 */
@WebServlet(name = "PlanetSortedByMassButtonClick", urlPatterns = {"/PlanetSortedByMassButtonClick"})
public class PlanetSortedByMassController extends HttpServlet {

    /**
     * @param request - object HttpServletRequest.Class
     * @param response - object HttpServletResponse.class
     * @throws ServletException - general exception a servlet can throw when it
     * encounters difficulty.
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            DatabaseService dbUtils = DatabaseService.getInstance("jdbc:h2:file:./lab", "app", "app");
            List<Planet> planets = dbUtils.getPlanetsSortedByMass();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Planets Sorted By Mass</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Planets List</h2>");

            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Name</th><th>Mass</th><th>Rotation Period</th><th>Day Length</th><th>Distance to the Sun</th><th>Average Temperature</th><th>Has Magnetic Field</th></tr>");

            if (!planets.isEmpty()) {
                for (Planet planet : planets) {
                    out.println("<tr>");
                    out.println("<td>" + planet.getId() + "</td>");
                    out.println("<td>" + planet.getName() + "</td>");
                    out.println("<td>" + planet.getMass() + "</td>");
                    out.println("<td>" + planet.getRotationPeriod() + "</td>");
                    out.println("<td>" + planet.getDayLength() + "</td>");
                    out.println("<td>" + planet.getDistanceToTheSun() + "</td>");
                    out.println("<td>" + planet.getAverageTemperature() + "</td>");
                    out.println("<td>" + (planet.isHasMagneticField() ? "Yes" : "No") + "</td>");
                    out.println("</tr>");
                }
            } else {
                out.println("<tr><td colspan='8'>No planets available</td></tr>");
            }

            out.println("</table>");

            out.println("<button onclick='goBack()'>Back</button>");
            out.println("<script>");
            out.println("function goBack() {");
            out.println("window.history.back();");
            out.println("}");
            out.println("</script>");
            out.println("<p>Information from db </p>");

            out.println("</body>");
            out.println("</html>");
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
                out.println("<p>" + "SQL Exception" + "</p>");
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
}
