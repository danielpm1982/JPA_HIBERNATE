import com.enterprise.cliente.Address;
import com.enterprise.cliente.Client;

public class TestAddClient {
    public static void main (String[] args){

        Client c1 = new Client();
        c1.setName("Daniel");
        
        Address a1 = new Address();
        a1.setStreet("Dom Manuel de Medeiros");
        a1.setStreetNumber("2785");
        a1.setNeighborhood("Parquelândia");
        a1.setCity("Fortaleza");
        a1.setState("Ceara");
        a1.setCountry("Brasil");
        a1.setClient(c1);
        
        c1.setAddress(a1);
        
        DAOClient.addClient(c1, a1);
        
    }
}
