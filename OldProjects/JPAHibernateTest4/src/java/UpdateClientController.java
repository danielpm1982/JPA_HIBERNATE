import com.enterprise.cliente.Address;
import com.enterprise.cliente.Client;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UpdateClientController", urlPatterns = {"/UpdateClientController"})
public class UpdateClientController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Client client1 = null;
        try {
            if(request.getParameter("idClient").compareTo("")!=0){
                client1 = DAOClient.findClientById(Long.parseLong(request.getParameter("idClient")));
            }
            Client client2 = client1;
            if(request.getParameter("name").compareTo("")!=0){
                client2.setName(request.getParameter("name"));
            }
            Address address2 = client1.getAddress();
            if(request.getParameter("street").compareTo("")!=0){
                address2.setStreet(request.getParameter("street"));
            }
            if(request.getParameter("streetNumber").compareTo("")!=0){
                address2.setStreetNumber(request.getParameter("streetNumber"));
            }
            if(request.getParameter("neighborhood").compareTo("")!=0){
                address2.setNeighborhood(request.getParameter("neighborhood"));
            }
            if(request.getParameter("city").compareTo("")!=0){
                address2.setCity(request.getParameter("city"));
            }
            if(request.getParameter("state").compareTo("")!=0){
                address2.setState(request.getParameter("state"));
            }
            if(request.getParameter("country").compareTo("")!=0){
                address2.setCountry(request.getParameter("country"));
            }
            address2.setClient(client2);
            client2.setAddress(address2);
            request.setAttribute("updateResult", false);
            if(DAOClient.updateClient(client1, client2)){
                request.setAttribute("updateResult", true);
            }
            client2=DAOClient.findClientById(Long.parseLong(request.getParameter("idClient")));
            address2=client2.getAddress();
            request.setAttribute("client2", client2);
            request.setAttribute("address2", address2);
            RequestDispatcher rd = request.getRequestDispatcher("resultUpdateClient.jsp");
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
