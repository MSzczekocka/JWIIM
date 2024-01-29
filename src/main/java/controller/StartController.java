package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import model.DatabaseService;

/**
 * Class which control start button
 *
 * @author Martyna Szczekocka
 * @version 1.0
 */
@WebServlet(name = "StartButtonClick", urlPatterns = {"/StartButtonClick"})
public class StartController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code>
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            DatabaseService dbUtils = DatabaseService.getInstance("jdbc:h2:file:./lab", "app", "app");
            dbUtils.createTable();
            request.getRequestDispatcher("FeaturesList.jsp").forward(request, response);
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
                out.println("<p>" + "Cannoct connect with db" + "</p>");
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
