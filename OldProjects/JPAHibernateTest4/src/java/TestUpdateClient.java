import com.enterprise.cliente.Client;

public class TestUpdateClient {
    public static void main (String[] args){
        Client c1 = DAOClient.findClientById(62);
        if(c1!=null){
            System.out.println(c1);
            Client c2 = c1;
            c2.getAddress().setNeighborhood("Parquelandia");
            if(DAOClient.updateClient(c1, c2)){
                System.out.println("UPDATED!");
                System.out.println(DAOClient.findClientById(c2.getId())+" "+DAOClient.findClientById(c2.getId()).getAddress());
            }
        }
        else{
            System.out.println("removed = "+false+". Client not updated !");
        }
    }
}
