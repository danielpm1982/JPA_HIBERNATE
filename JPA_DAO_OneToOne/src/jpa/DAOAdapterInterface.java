package jpa;

public interface DAOAdapterInterface {
    public void openResources();
    public void closeResources();
    public Client[] searchAllClients();
    public Client[] searchClientByName(String name);
    public Client searchClientById(long id);
    public Client[] searchClientsNativeSQLQuery(String sqlQuery);
    public Client searchBestPaidClient();
    public Client searchWorstPaidClient();
    public Department[] searchAllDepartments();
    public Department[] searchDepartmentByName(String name);
    public void insertClient(Client client);
    public void insertDepartment(Department department);
    public void insertDepartmentArray(Department[] department);
    public void deleteClient(Client client);
    public void updateClient(Client oldClient, Client newClient);
    public void updateClientUniqueThreadSafe(Client oldClient, Client newClient);
    public void updateDepartment(Department oldDepartment, Department newDepartment);
    public void updateDepartmentNameBulk(String nameToBeAddedToAllDepartments);
    public void deleteDepartmentBulk(long departmentNoFromWhichToStartDeleting);
    public String[] searchAllClientsNamesWithDepartmentNames();
}
