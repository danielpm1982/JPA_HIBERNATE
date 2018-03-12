import com.enterprise.cliente.Client;
import java.util.List;

public class TestFindClient {
    public static void main(String[] args){
        
//        Client a = new Client();
//        a.setName("Daniel");
//        DAOClient.addClient(a, null);
        Client c = DAOClient.findClientById2(2);
        System.out.println(c);
//        DAOClient.removeClient(c);
        List<Client> list = DAOClient.findClientByName2("Daniel");
        System.out.println(list);
    }
}
