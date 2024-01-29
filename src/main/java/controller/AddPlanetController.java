package controller;

import exception.DividingByZeroException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import model.DatabaseService;
import model.Planet;

/**
 * Class for control add planet feature
 * @author Martyna Szczekocka
 * @version 1.0 Controller Class For Add Planet Feature
 */
@WebServlet(name = "AddPlanetController", urlPatterns = {"/AddPlanetController"})
public class AddPlanetController extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Add New Planet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Add New Planet</h2>");
            out.println("<form action='AddPlanetController' method='POST'>");
            out.println("Name: <input type='text' name='name' required><br><br>");
            out.println("Mass (in kg): <input type='number' name='mass' step='any' required><br><br>");
            out.println("Rotation Period (in hours): <input type='number' name='rotationPeriod' step='any' required><br><br>");
            out.println("Day Length (in hours): <input type='number' name='dayLength' step='any' required><br><br>");
            out.println("Distance to the Sun (in km): <input type='number' name='distanceToTheSun' step='any' required><br><br>");
            out.println("Average Temperature (in Celsius): <input type='number' name='averageTemperature' step='any' required><br><br>");
            out.println("Has Magnetic Field: <input type='checkbox' name='hasMagneticField'><br><br>");
            out.println("<input type='submit' value='Add Planet'>");
            out.println("</form>");

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

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            double mass = Double.parseDouble(request.getParameter("mass"));
            double rotationPeriod = Double.parseDouble(request.getParameter("rotationPeriod"));
            double dayLength = Double.parseDouble(request.getParameter("dayLength"));
            double distanceToTheSun = Double.parseDouble(request.getParameter("distanceToTheSun"));
            double averageTemperature = Double.parseDouble(request.getParameter("averageTemperature"));
            String hasMagneticFieldParam = request.getParameter("hasMagneticField");
            boolean hasMagneticField = hasMagneticFieldParam != null && hasMagneticFieldParam.equals("on");

            if (rotationPeriod == 0) {
                throw new DividingByZeroException();
            }

            Planet newPlanet = new Planet();
            newPlanet.setName(name);
            newPlanet.setMass(mass);
            newPlanet.setRotationPeriod(rotationPeriod);
            newPlanet.setDayLength(dayLength);
            newPlanet.setDistanceToTheSun(distanceToTheSun);
            newPlanet.setAverageTemperature(averageTemperature);
            newPlanet.setHasMagneticField(hasMagneticField);

            DatabaseService dbService = DatabaseService.getInstance("jdbc:h2:file:./lab", "app", "app");
            dbService.addPlanet(newPlanet);

            response.sendRedirect("planetAdded.html"); // or some confirmation message/page

            response.setContentType("text/html;charset=UTF-8");
        } catch (NumberFormatException e) {
            showErrorPage(response, "Invalid input format.");
        } catch (DividingByZeroException e) {
            showErrorPage(response, "Value of Rotation Period cannot be 0");
        } catch (SQLException e) {
            showErrorPage(response, "Problem with adding to database");
        }
    }

    /**
     * Method for error message page
     * @param response - servlet response
     * @param errorMessage - content of error message 
     * @throws IOException 
     */
    private void showErrorPage(HttpServletResponse response, String errorMessage) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
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
        out.close();
    }
}
