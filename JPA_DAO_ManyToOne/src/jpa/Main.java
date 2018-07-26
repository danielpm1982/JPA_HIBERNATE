package jpa;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {
    private static DAOAdapterInterface dao;
    public static void main(String[] args){
        try{
            System.out.println("Initializing high-level DAO and opening resources...");
            dao = new DAO();
            dao.openResources();
            Thread.sleep(3000);
            System.out.println("Populating DB through DAO...");
            insertTest();
            System.out.println("Testing search on DB through DAO...");
            searchClientsTest();
            searchDepartmentsTest();
            System.out.println("Testing update on DB through DAO...");
            updateClientTest();
            searchDepartmentsTest();
            System.out.println("Testing deletion on DB through DAO...");
            deleteClientTest();
            searchDepartmentsTest();
            System.out.println("Reseting initial populated state of DB through DAO...");
            resetInitialPopulatedState();
            searchDepartmentsTest();
            System.out.println("ALL THE SCRIPT SUCCESSFULLY EXECUTED WITHOUT ERRORS !!");
        } catch (InterruptedException ex){
            ex.printStackTrace(System.out);
        } catch (Exception ex){
            ex.printStackTrace(System.out);
        } finally{
            System.out.println("Closing all resources...");
            if(dao!=null)
            dao.closeResources();
        }
    }
    private static void insertTest(){
        System.out.println("Inserting Department instances...");
        Department d1 = new Department(1, "department1", Period.FULL_24_HOURS, new Address("streetABC", 500, "NewYork", "NY", "UnitedStates"));
        Department d2 = new Department(2, "department2", Period.PARTIAL_BUSINESS_HOURS, new Address("streetDEF", 40, "SaoPaulo", "SP", "Brasil"));
        Department d3 = new Department(3, "department3", Period.PARTIAL_BUSINESS_HOURS, new Address("streetGHI", 30, "Santiago", "RM", "Chile"));
        dao.insertDepartment(d1);
        dao.insertDepartment(d2);
        dao.insertDepartment(d3);
        System.out.println("Department instances Inserted!");
        System.out.println("Inserting Client instances...");
        Client c1 = new IndividualClient(123456789, "Client1", Arrays.asList("555-5555","888-8888"), new BigDecimal("50000.5"), LocalDate.of(1982,Month.APRIL,20), LocalDateTime.of(2000, Month.JANUARY, 1, 12, 0),d3);
        dao.insertClient(c1);
        Client c2 = new CorporateClient(567890123, "Client2", Arrays.asList("9999-9999", "7777-5222"), new BigDecimal("2500.5"), LocalDate.of(1990,Month.AUGUST,5), LocalDateTime.of(2002, Month.JULY, 1, 12, 0),d2);
        dao.insertClient(c2);
        Client c3 = new IndividualClient(321654876, "Client3", Arrays.asList(""), new BigDecimal("1890.5"), LocalDate.of(1995,Month.DECEMBER,31), LocalDateTime.of(2005, Month.MAY, 5, 12, 0),d1);
        dao.insertClient(c3);
        Client c4 = new IndividualClient(746372865, "Client4", Arrays.asList(""), new BigDecimal("1500.5"), LocalDate.of(1995,Month.JANUARY,31), LocalDateTime.of(2002, Month.JANUARY, 1, 12, 0),d1);
        dao.insertClient(c4);
        System.out.println("Client instances Inserted!");
    }
    private static void searchClientsTest(){
        System.out.println("Looking for all Clients...");
        Arrays.asList(dao.searchAllClients()).forEach(System.out::println);
        System.out.println("Searching Client by name...");
        Arrays.asList(dao.searchClientByName("Client1")).forEach(System.out::println);
        System.out.println("Searching Client by id...");
        System.out.println(dao.searchClientById(1));
    }
    private static void searchDepartmentsTest(){
        System.out.println("Looking for all Departments With respective Clients...");
        System.out.println("Department1:");
        Arrays.asList(dao.searchAllClients()).stream().filter(x->x.getDepartment().getNameDep().equals("department1")).collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("Department2:");
        Arrays.asList(dao.searchAllClients()).stream().filter(x->x.getDepartment().getNameDep().equals("department2")).collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("Department3:");
        Arrays.asList(dao.searchAllClients()).stream().filter(x->x.getDepartment().getNameDep().equals("department3")).collect(Collectors.toList()).forEach(System.out::println);
    }
    private static void updateClientTest(){
        Department d3 = new Department(3, "department3", Period.PARTIAL_BUSINESS_HOURS, new Address("streetGHI", 30, "Santiago", "RM", "Chile"));
        Client c3 = new IndividualClient(321654876, "Client3", Arrays.asList(""), new BigDecimal("1890.5"), LocalDate.of(1995,Month.DECEMBER,31), LocalDateTime.of(2005, Month.MAY, 5, 12, 0),d3);
        System.out.println("Updating Client3... incomplete mock object - without id");
        dao.updateClient(c3, c3);
        System.out.println("Updated!");
        Department d1 = new Department(1, "department1", Period.FULL_24_HOURS, new Address("streetABC", 500, "NewYork", "NY", "US"));
        System.out.println("Updating client4... complete object - with id");
        Arrays.asList(dao.searchClientByName("client4")).forEach(x->dao.updateClient(x, new CorporateClient(567890123, "client40", x.getPhoneNo(), x.getSalary(), x.getBirthDate(), x.getRegisteredIn(),d1)));
        System.out.println("Updated!");
    }
    private static void deleteClientTest(){
        System.out.println("Deleting client40 complete object - with id");
        Client[] c40 = dao.searchClientByName("client40");
        Arrays.asList(c40).forEach(x->dao.deleteClient(x));
        System.out.println("Deleted!");
    }
    private static void resetInitialPopulatedState(){ //return to initial populated state without c4
        Department d1 = new Department(1, "department1", Period.FULL_24_HOURS, new Address("streetABC", 500, "NewYork", "NY", "UnitedStates"));
        Client c3 = new IndividualClient(321654876, "Client3", Arrays.asList(""), new BigDecimal("1890.5"), LocalDate.of(1995,Month.DECEMBER,31), LocalDateTime.of(2005, Month.MAY, 5, 12, 0),d1);
        System.out.println("Updating Client3... incomplete mock object - without id");
        dao.updateClient(c3, c3);
        System.out.println("Updated!");
    }
}
