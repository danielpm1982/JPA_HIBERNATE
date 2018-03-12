import com.enterprise.cliente.Address;
import com.enterprise.cliente.Client;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AddClientController", urlPatterns = {"/AddClientController"})
public class AddClientController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            Client c = new Client();
            c.setName(request.getParameter("name"));
            Address a = new Address();
            a.setStreet(request.getParameter("street"));
            a.setStreetNumber(request.getParameter("streetNumber"));
            a.setNeighborhood(request.getParameter("neighborhood"));
            a.setCity(request.getParameter("city"));
            a.setState(request.getParameter("state"));
            a.setCountry(request.getParameter("country"));
            a.setClient(c);
            c.setAddress(a);
            request.setAttribute("insertResult", false);
            if(DAOClient.addClient(c, a)){
                request.setAttribute("insertResult", true);
            }
            request.setAttribute("client", c);
            request.setAttribute("address", a);
            RequestDispatcher rd = request.getRequestDispatcher("resultAdd.jsp");
            rd.forward(request, response);
        } finally {            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
