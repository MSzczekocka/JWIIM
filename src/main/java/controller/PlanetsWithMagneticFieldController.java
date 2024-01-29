package controller;

import jakarta.servlet.ServletException;
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
 * Class for control Planets With Magnetic Field feature
 *
 * @author Martyna Szczekocka
 * @version 1.0
 */
public class PlanetsWithMagneticFieldController extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        DatabaseService dbUtils = DatabaseService.getInstance("jdbc:h2:file:./lab", "app", "app");
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Planets with Magnetic Field</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Planets with Magnetic Field</h2>");

            List<Planet> planetsWithMagneticField = dbUtils.getPlanetsWithMagneticField();

            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Name</th><th>Mass</th><th>Rotation Period</th><th>Day Length</th><th>Distance to the Sun</th><th>Average Temperature</th><th>Has Magnetic Field</th></tr>");

            if (!planetsWithMagneticField.isEmpty()) {
                for (Planet planet : planetsWithMagneticField) {
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
        } catch (SQLException e) {
            showErrorPage(response, "Problem with adding to database");
        }

    }

    /**
     * Method for error message page
     *
     * @param response - servlet response
     * @param errorMessage - content of error message
     * @throws IOException
     */
    private void showErrorPage(HttpServletResponse response, String errorMessage) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Error</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Error</h2>");
            out.println("<p>" + errorMessage + "</p>");
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
