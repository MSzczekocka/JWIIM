package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import model.DatabaseService;
import model.Planet;

/**
 * Class for control Correlation Coefficient feature
 *
 * @author Martyna Szczekocka
 * @version 1.0
 */
@WebServlet(name = "CorrelationCoefficientController", urlPatterns = {"/CorrelationCoefficientController"})
public class CorrelationCoefficientController extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            List<Planet> planets = dbUtils.getAllPlanets();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Planet Correlation Coefficients</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Planets Correlation Coefficients</h2>");

            out.println("<table border='1'>");
            out.println("<tr><th>Name</th><th>Correlation Coefficient</th></tr>");

            for (Planet planet : planets) {
                double correlationCoefficient = planet.coutPearsonCorrelationCoefficient();
                out.println("<tr>");
                out.println("<td>" + planet.getName() + "</td>");
                out.println("<td>" + correlationCoefficient + "</td>");
                out.println("</tr>");
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
