package jpa;
public interface DAOAdapterInterface {
    public void openResources();
    public void closeResources();
    public Client[] searchAllClients();
    public Client[] searchClientByName(String name);
    public Client searchClientById(long id);
    public Department[] searchAllDepartments();
    public Department[] searchDepartmentByName(String name);
    public void insertClient(Client client);
    public void insertDepartment(Department department);
    public void deleteClient(Client client);
    public void updateClient(Client oldClient, Client newClient);
    public void updateDepartment(Department oldDepartment, Department newDepartment);
}
