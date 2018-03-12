import com.enterprise.cliente.Client;

public class TestRemoveClient {
    public static void main (String[] args){
        long idClient = 1L;
        Client c = DAOClient.findClientById(idClient);
        if(c!=null){
            System.out.println(c);
            System.out.println(c.getAddress());
            System.out.println("removed = "+DAOClient.removeClient(c));
        }
        else{
            System.out.println("removed = "+false+". Client does not exist !");
        }
    }
}
