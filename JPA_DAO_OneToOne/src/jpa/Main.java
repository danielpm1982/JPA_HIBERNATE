package jpa;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
            searchClients2Test("select * from CLIENT c where c.ID>0 order by c.NAME desc");
            searchDepartmentsTest();
            System.out.println("Testing update on DB through DAO...");
            updateClientTest();
            searchDepartmentsTest2();
//            System.out.println("Testing update on DB through DAO using LockModeType Optimistic @Version attribute - thread safe...");
//            updateClientTest2();
            System.out.println("Testing deletion on DB through DAO...");
            deleteClientTest();
            searchDepartmentsTest3();
            System.out.println("Reseting initial populated state of DB through DAO...");
            resetInitialPopulatedState();
            searchDepartmentsTest2();
            System.out.println("Testing search of multiple entity attributes on DB through DAO...");
            searchMultipleEntityAttributesTest();
            System.out.println("Testing bulk update...");
            updateAllDepartmentNameWith("Bulked Updated at: "+LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
            searchMultipleEntityAttributesTest();
//            System.out.println("Testing bulk delete...");
//            deleteAllDepartmentStartingFrom(1);
            searchMultipleEntityAttributesTest();
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
    private static void insertTest(){ //Differently from the update method problem, here, the mock Clients will be the ones persisted (and not other instances searched from the mock Client's names), therefore the Departments can have their client attribute value set to the mock Clients already, and both persisted inside the DAO method. Or the DAO insert method could be changed to deal with that bidirectional settings internally, as well as it's been done with the update method right below.
//        System.out.println("Inserting Department instances...");
        Department d1 = new Department(1, "department1", Period.FULL_24_HOURS, new Address("streetABC", 500, "NewYork", "NY", "UnitedStates"));
        Department d2 = new Department(2, "department2", Period.PARTIAL_BUSINESS_HOURS, new Address("streetDEF", 40, "SaoPaulo", "SP", "Brasil"));
        Department d3 = new Department(3, "department3", Period.PARTIAL_BUSINESS_HOURS, new Address("streetGHI", 30, "Santiago", "RM", "Chile"));
        Department d4 = new Department(4, "department4", Period.FULL_24_HOURS, new Address("streetJKL", 20, "Madrid", "Madrid", "Spain"));
//        dao.insertDepartment(d1); //Cascaded persisted from Client.
//        dao.insertDepartment(d2); //Cascaded persisted from Client.
//        dao.insertDepartment(d3); //Cascaded persisted from Client.
//        dao.insertDepartment(d4); //Cascaded persisted from Client.
//        System.out.println("Department instances Inserted!");
        System.out.println("Inserting Client instances... and cascaded Deparment instances associated...");
        Client c1 = new IndividualClient(123456789, "Client1", Arrays.asList("555-5555","888-8888"), new BigDecimal("50000.5"), LocalDate.of(1982,Month.APRIL,20), LocalDateTime.of(2000, Month.JANUARY, 1, 12, 0),d3);
        d3.setClient(c1);
        Client c2 = new CorporateClient(567890123, "Client2", Arrays.asList("9999-9999", "7777-5222"), new BigDecimal("2500.5"), LocalDate.of(1990,Month.AUGUST,5), LocalDateTime.of(2002, Month.JULY, 1, 12, 0),d2);
        d2.setClient(c2);
        Client c3 = new IndividualClient(321654876, "Client3", Arrays.asList(""), new BigDecimal("1890.5"), LocalDate.of(1995,Month.DECEMBER,31), LocalDateTime.of(2005, Month.MAY, 5, 12, 0),d1);
        d1.setClient(c3);
        dao.insertClient(c1);
        dao.insertClient(c2);
        dao.insertClient(c3);
        System.out.println("Client instances with respective Department instances Inserted!");
    }
    private static void searchClientsTest(){
        System.out.println("Looking for all Clients...");
        Arrays.asList(dao.searchAllClients()).forEach(System.out::println);
        System.out.println("Searching Client by name...");
        Arrays.asList(dao.searchClientByName("Client1")).forEach(System.out::println);
        System.out.println("Searching Client by id...");
        System.out.println(dao.searchClientById(1));
        System.out.println("Searching best paid Client at the DataBase...");
        Client bestPaidClient = dao.searchBestPaidClient();
        System.out.println("Best Salary: "+bestPaidClient.getSalary()+" Client: "+bestPaidClient);
        System.out.println("Searching worst paid Client at the DataBase...");
        Client worstPaidClient = dao.searchWorstPaidClient();
        System.out.println("Worst Salary: "+worstPaidClient.getSalary()+" Client: "+worstPaidClient);
    }
    private static void searchClients2Test(String sqlQuery){
        System.out.println("Searching for Clients through native custom SQL query: "+sqlQuery);
        Arrays.asList(dao.searchClientsNativeSQLQuery(sqlQuery)).forEach(System.out::println);
    }
    private static void searchDepartmentsTest(){ //getting clients and then the respective department attribute value through the toString method, obtained through the table column ´DEPARTMENT_NO´ FK that relates to the respective and correct tuple at the department table: CLIENT-->DEPARTMENT;
        System.out.println("Looking for all Departments With respective Clients...");
        System.out.println("Department1:");
        Arrays.asList(dao.searchAllClients()).stream().filter(x->x.getDepartment().getNameDep().equals("department1")).collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("Department2:");
        Arrays.asList(dao.searchAllClients()).stream().filter(x->x.getDepartment().getNameDep().equals("department2")).collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("Department3:");
        Arrays.asList(dao.searchAllClients()).stream().filter(x->x.getDepartment().getNameDep().equals("department3")).collect(Collectors.toList()).forEach(System.out::println);
        System.out.println("Department4:");
        Arrays.asList(dao.searchAllClients()).stream().filter(x->x.getDepartment().getNameDep().equals("department4")).collect(Collectors.toList()).forEach(System.out::println);
    }
    private static void searchDepartmentsTest2(){ //getting departments and then the respective Client attribute value through the getClient(). The values are obtained from the table Client through the PK departmentNo, which Department entity instances have the client attribute mappedBy from;
        System.out.println("Looking for all Departments With respective Clients...");
        System.out.println("Department1:");
        Arrays.asList(dao.searchDepartmentByName("department1")).forEach(x->System.out.println(x.getClient()+" "+x));
        System.out.println("Department2:");
        Arrays.asList(dao.searchDepartmentByName("department2")).forEach(x->System.out.println(x.getClient()+" "+x));
        System.out.println("Department3:");
        Arrays.asList(dao.searchDepartmentByName("department3")).forEach(x->System.out.println(x.getClient()+" "+x));
        System.out.println("Department4:");
        Arrays.asList(dao.searchDepartmentByName("department4")).forEach(x->System.out.println(x.getClient()+" "+x));
    }
    private static void searchDepartmentsTest3(){ //getting all departments
        System.out.println("Looking for all Departments...");
        Arrays.asList(dao.searchAllDepartments()).forEach(System.out::println);
    }
    private static void searchMultipleEntityAttributesTest(){
        System.out.println("Looking for all Clients' names with Departments' names...");
        Arrays.asList(dao.searchAllClientsNamesWithDepartmentNames()).forEach(System.out::println);
    }
    private static void updateClientTest(){ 
        Department d4 = new Department(4, "department4", Period.FULL_24_HOURS, new Address("streetJKL", 20, "Madrid", "Madrid", "Spain"));
        Client c3 = new IndividualClient(321654876, "Client3", Arrays.asList(""), new BigDecimal("1890.5"), LocalDate.of(1995,Month.DECEMBER,31), LocalDateTime.of(2005, Month.MAY, 5, 12, 0),d4);
        System.out.println("Updating Client3... incomplete mock object - without id"); //in the case of the first update example here (with a mock oldClient being passed), instead of setting the department client value here, to an unmanaged mock client, it should be done inside the DAO method, so that each department has its client value set with each real and managed client found for that mock Client's name. Otherwise, the persistence of the client would cascade the persistence of the department but the department would still be set to the outsider mock client which is not managed at the EntityManager or mapped at the DB tables. Differently from the insert method, it is NOT the mock client that will be persisted.
        dao.updateClient(c3, c3);
        System.out.println("Updated!");
        Department d1 = new Department(1, "department1", Period.FULL_24_HOURS, new Address("streetABC", 500, "NewYork", "NY", "UnitedStates"));
        System.out.println("Updating client2... complete object - with id"); //in the case of the second update example here, with a complete (and not mock) object passed as oldClient, the department could already be set to each real oldClient found, and passed to the DAO method through the newClient, in order to be set to the oldClient, inside the DAO, as the other Client attribute values, without the DAO method having to set the departments to each Client anymore. The setting of the Department client value would be external, beacause the real Client instance which the department is set to is already available from the external search, differently from the first update example (with a mock oldClient, right above).
        Arrays.asList(dao.searchClientByName("client2")).forEach(x->{d1.setClient(x);dao.updateClient(x, new CorporateClient(567890123, "client40", x.getPhoneNo(), x.getSalary(), x.getBirthDate(), x.getRegisteredIn(),d1));});
        System.out.println("Updated!");
    }
    private static void updateClientTest2(){
        Department d1 = dao.searchDepartmentByName("department1")[0];
        System.out.println("Updating client40 - current Version: "+dao.searchClientByName("client40")[0].getVersion()+" a first time... complete object - with id");
        Arrays.asList(dao.searchClientByName("client40")).forEach(x->{d1.setClient(x);dao.updateClientUniqueThreadSafe(x, new CorporateClient(567890123, "client40", x.getPhoneNo(), x.getSalary(), x.getBirthDate(), x.getRegisteredIn(),d1));});
        System.out.println("Updated!");
        System.out.println("Checking Version:");
        Arrays.asList(dao.searchClientByName("client40")).forEach(x->System.out.println("Version of this Client instance: "+x.getVersion()+" "+x));
        System.out.println("Updating client40 a second time... complete object - with id");
        Arrays.asList(dao.searchClientByName("client40")).forEach(x->{d1.setClient(x);dao.updateClientUniqueThreadSafe(x, new CorporateClient(567890123, "client40", x.getPhoneNo(), x.getSalary(), x.getBirthDate(), x.getRegisteredIn(),d1));});
        System.out.println("Updated!");
        System.out.println("Checking Version:");
        Arrays.asList(dao.searchClientByName("client40")).forEach(x->System.out.println("Version of this Client instance: "+x.getVersion()+" "+x));
        System.out.println("Updating client40 a third time... complete object - with id");
        Arrays.asList(dao.searchClientByName("client40")).forEach(x->{d1.setClient(x);dao.updateClientUniqueThreadSafe(x, new CorporateClient(567890123, "client40", x.getPhoneNo(), x.getSalary(), x.getBirthDate(), x.getRegisteredIn(),d1));});
        System.out.println("Updated!");
        System.out.println("Checking Version:");
        Arrays.asList(dao.searchClientByName("client40")).forEach(x->System.out.println("Version of this Client instance: "+x.getVersion()+" "+x));
    }
    private static void deleteClientTest(){
        Department d2 = new Department(2, "department2", Period.PARTIAL_BUSINESS_HOURS, new Address("streetDEF", 40, "SaoPaulo", "SP", "Brasil"));
        Client c50 = new CorporateClient(890567098, "Client50", Arrays.asList(""), new BigDecimal("1890.5"), LocalDate.of(1995,Month.DECEMBER,31), LocalDateTime.of(2005, Month.MAY, 5, 12, 0),d2);
        d2.setClient(c50);
        dao.insertClient(c50);
        System.out.println("Deleting client50 complete object - with id");
        Client[] c50Array = dao.searchClientByName("client50");
        Arrays.asList(c50Array).forEach(x->dao.deleteClient(x));
        System.out.println("Deleted!");
    }
    private static void resetInitialPopulatedState(){ //see observations for update method above. On both cases (mock and real passed objects as the old instance to be updated).
        Department d2 = new Department(2, "department2", Period.PARTIAL_BUSINESS_HOURS, new Address("streetDEF", 40, "SaoPaulo", "SP", "Brasil"));
        Client c2 = new CorporateClient(567890123, "Client2", Arrays.asList("9999-9999", "7777-5222"), new BigDecimal("2500.5"), LocalDate.of(1990,Month.AUGUST,5), LocalDateTime.of(2002, Month.JULY, 1, 12, 0),d2);
        System.out.println("Updating Client2... complete object - with id");
        Arrays.asList(dao.searchClientByName("client40")).forEach(x->{d2.setClient(x);dao.updateClient(x, c2);});
        System.out.println("Checking Version:");
        Arrays.asList(dao.searchClientByName("client2")).forEach(x->System.out.println("Version of this Client instance: "+x.getVersion()+" "+x));
        Department d1 = new Department(1, "department1", Period.FULL_24_HOURS, new Address("streetABC", 500, "NewYork", "NY", "UnitedStates"));
        Client c3 = new IndividualClient(321654876, "Client3", Arrays.asList(""), new BigDecimal("1890.5"), LocalDate.of(1995,Month.DECEMBER,31), LocalDateTime.of(2005, Month.MAY, 5, 12, 0),d1);
        System.out.println("Updating Client3... incomplete mock object - without id");
        dao.updateClient(c3, c3);
        System.out.println("Checking Version:");
        Arrays.asList(dao.searchClientByName("client3")).forEach(x->System.out.println("Version of this Client instance: "+x.getVersion()+" "+x));
        System.out.println("Updated!");
    }
    private static void updateAllDepartmentNameWith(String nameToBeAddedToAllDepartments){
        System.out.println("Updating (concatenating) all entity names with \'"+nameToBeAddedToAllDepartments+"\'");
        dao.updateDepartmentNameBulk(nameToBeAddedToAllDepartments);
        System.out.println("Updated!");
    }
    private static void deleteAllDepartmentStartingFrom(long departmentNoFromWhichToStartDeleting){
        System.out.println("Deleting all entities starting from departmentNo: "+departmentNoFromWhichToStartDeleting);
        dao.deleteDepartmentBulk(departmentNoFromWhichToStartDeleting);
        System.out.println("Deleted!");
    }
}

/*
Some trouble has been found:

1.
All attempts to reproduce a lazy behavior have failed. Either with @Basic annotation on Client or Department basic attributes, or with relationship
attributes (FK attributes) between two entities - with the cardinality annotations. Even after closing the EntityManager, the lazy attribute still
returns a value, which it should not happen if it were lazily fetched. The same for FK attributes returning the other entity instance even after
the EntityMananged is closed and the fetch type at the cardinality annotation has been set to FetchType.LAZY. It seems that the LAZY set has not
taken any effect and every attribute value is fetched in the EAGER mode. No idea why.

2. 
The xml value for the shared cache implementation suggested was not recognized. So, 2nd cache was not set or tested.
*/
