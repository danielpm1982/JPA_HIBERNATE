package jpa;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

public class DAO implements DAOAdapterInterface{
    private EntityManagerFactory factory;
    private EntityManager em;
    public DAO() {
    }
    @Override
    public void openResources(){
        factory = Persistence.createEntityManagerFactory("JPA_DAOPU");
        em = factory.createEntityManager();
    }
    @Override
    public void closeResources(){
        if(em.isOpen()){
            em.close();
        }
        if(factory.isOpen()){
            factory.close();
        }
    }
    @Override
    public Client[] searchAllClients() {
//        String query = "select e from Client e";
//        List<Client> clientList = em.createQuery(query, Client.class).getResultList();
//        List<Client> clientList = em.createNamedQuery("Client.searchAllClients", Client.class).setFirstResult(0).setMaxResults(100).getResultList();
//        return clientList.toArray(new Client[0]);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Client> cq = cb.createQuery(Client.class);
        Root<Client> root = cq.from(Client.class);
//        root.fetch("department",JoinType.LEFT); //only for lazy basic or relationship attributes
        cq.select(root).distinct(true);
//        cq.orderBy(cb.asc(root.<Long>get("id")));
        cq.orderBy(cb.asc(root.get(Client_.id))); //using metaModel for root get arguments
        return em.createQuery(cq).getResultList().toArray(new Client[0]);
    }
    @Override
    public Client[] searchClientByName(String name) {
//        String query = "select e from Client e where e.name = '"+name+"'"; //searching by exact name, not similar ones. Name is not unique, thus the search can return multiple instances with the same name.
//        List<Client> clientList = em.createQuery(query, Client.class).getResultList();
//        List<Client> clientList = em.createNamedQuery("Client.searchClientByName", Client.class).setParameter("name", name).getResultList();
//        return clientList.toArray(new Client[0]);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Client> cq = cb.createQuery(Client.class);
        Root<Client> root = cq.from(Client.class);
        cq.select(root);
//        Predicate predicate = cb.equal(root.<String>get("name"), name);
        Predicate predicate = cb.equal(root.get(Client_.name), name);
        cq.where(predicate);
        return em.createQuery(cq).getResultList().toArray(new Client[0]);
    }
    @Override
    public Client searchClientById(long id) { //it's always been supposed to be a single result. So find or getReference has been used.
//        String query = "select e from Client e where e.id = '"+id+"'";
//        Client clientTemp = em.createQuery(query, Client.class).getSingleResult();
//        Client clientTemp = em.find(Client.class, id, LockModeType.OPTIMISTIC_FORCE_INCREMENT); //for testing @Version at Client version attribute - optimistic lock type.
//        Client clientTemp = em.find(Client.class, id);
//        Client clientTemp = em.getReference(Client.class, id);
//        Client clientTemp = em.createNamedQuery("Client.searchClientById", Client.class).setParameter("id", id).getSingleResult();
//        return clientTemp;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Client> cq = cb.createQuery(Client.class);
        Root<Client> root = cq.from(Client.class);
        cq.select(root);
//        Predicate predicate = cb.equal(root.<Long>get("id"), id);
        Predicate predicate = cb.equal(root.get(Client_.id), id);
        cq.where(predicate);
        return em.createQuery(cq).getSingleResult();
    }
    @Override
    public String[] searchAllClientsNamesWithDepartmentNames() {
//        String query = "select e.name, e.department.nameDep from Client e"; 
//        List<Object[]> clientList = em.createQuery(query).getResultList(); //getting a list of arrays with multiple entity instances' attributes.
//        return clientList.stream().map(x->"Client: "+x[0]+" Department: "+x[1]).toArray(String[]::new); //has to deal with array positions (indexes) to get the attribute values.
//        String query = "select new jpa.ClientNameDepName(e.name, e.department.nameDep) from Client e"; //using JPQL new command.
//        List<ClientNameDepName> clientList = em.createQuery(query, ClientNameDepName.class).getResultList(); //getting a list of a mock object with the searched attributes defined at it.
//        List<ClientNameDepName> clientList = em.createNamedQuery("Client.searchAllClientsNamesWithDepartmentNames",ClientNameDepName.class).setFirstResult(0).setMaxResults(100).getResultList(); //getting a list of a mock object with the searched attributes defined at it, using namedQuery.
//        return clientList.stream().map(x->x.toString()).toArray(String[]::new); //avoids dealing with list of arrays, and deals with a list of the mock object instead.
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
//        Root<Client> root = cq.from(Client.class);
//        cq.multiselect(root.get("name"),root.get("department").get("nameDep"));
//        return em.createQuery(cq).getResultList().stream().map(x->"Client: "+x[0]+" Department: "+x[1]).toArray(String[]::new);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
        Root<Client> root = cq.from(Client.class);
//        cq.multiselect(root.<String>get("name").alias("Client.name"),root.<Department>get("department").<String>get("nameDep").alias("Client.deparment.nameDep"));
        cq.multiselect(root.get(Client_.name).alias("Client.name"),root.get(Client_.department).get(Department_.nameDep).alias("Client.deparment.nameDep"));
        return em.createQuery(cq).getResultList().stream().map(x->"Client: "+x.get("Client.name", String.class)+" Department: "+x.get("Client.deparment.nameDep", String.class)).toArray(String[]::new);
    }
    @Override
    public Client[] searchClientsNativeSQLQuery(String sqlQuery) {
        List<Client> clientList = em.createNativeQuery(sqlQuery, Client.class).setFirstResult(0).setMaxResults(100).getResultList();
        return clientList.toArray(new Client[0]);
    }
    @Override
    public Client searchBestPaidClient() { //selects, from the cq result, the rows - in this case unique - whose instance does not have any other greater salary at the subquery, that is, at the query of all greater results compared to each individual cq result. For each cq instance, this instance is compared to ALL results at the subquery, for possible greater values that might exist, and if no greater value is found, than that cq instance is selected. If any value at the subquery is found to be greater, though, that cq instance is not selected.
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Client> cq = cb.createQuery(Client.class);
        Root<Client> root1 = cq.from(Client.class);
        cq.select(root1);
        Subquery<Client> sq = cq.subquery(Client.class);
        Root<Client> root2 = sq.from(Client.class);
        sq.select(root2);
//        Predicate predicate2 = cb.gt(root2.<BigDecimal>get("salary"),root1.<BigDecimal>get("salary"));
        Predicate predicate2 = cb.gt(root2.get(Client_.salary),root1.get(Client_.salary));
        sq.where(predicate2);
        Predicate predicate1 = cb.not(cb.exists(sq));
        cq.where(predicate1);
        return em.createQuery(cq).getSingleResult();
    }
    @Override
    public Client searchWorstPaidClient() { //the opposite of the searchMostWellPaid() method.
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Client> cq = cb.createQuery(Client.class);
        Root<Client> root1 = cq.from(Client.class);
        cq.select(root1);
        Subquery<Client> sq = cq.subquery(Client.class);
        Root<Client> root2 = sq.from(Client.class);
        sq.select(root2);
//        Predicate predicate2 = cb.lt(root2.<BigDecimal>get("salary"),root1.<BigDecimal>get("salary"));
        Predicate predicate2 = cb.lt(root2.<BigDecimal>get(Client_.salary),root1.<BigDecimal>get(Client_.salary));
        sq.where(predicate2);
        Predicate predicate1 = cb.not(cb.exists(sq));
        cq.where(predicate1);
        return em.createQuery(cq).getSingleResult();
    }
    @Override
    public Department[] searchAllDepartments() {
//        String query = "select d from Department d";
//        List<Department> departmentList = em.createQuery(query, Department.class).getResultList();
//        List<Department> departmentList = em.createNamedQuery("Department.searchAllDepartments", Department.class).setFirstResult(0).setMaxResults(100).getResultList();
//        return departmentList.toArray(new Department[0]);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Department> cq = cb.createQuery(Department.class);
        Root<Department> root = cq.from(Department.class);
        cq.select(root);
//        cq.orderBy(cb.asc(root.get("nameDep")));
        cq.orderBy(cb.asc(root.get(Department_.nameDep)));
        return em.createQuery(cq).getResultList().toArray(new Department[0]);
    }
    
    @Override
    public Department[] searchDepartmentByName(String name) { //searching by exact name, not similar ones. Name is unique, thus can only return one single result, at most. But in the initial design it was not unique, and the code returning eventual multiple instances was maintained, in case the specs change again.
//        String query = "select d from Department d where d.nameDep= '"+name+"'";
//        List<Department> departmentList = em.createQuery(query, Department.class).getResultList();
//        List<Department> departmentList = em.createNamedQuery("Department.searchDepartmentByName", Department.class).setParameter("name", name).getResultList();
//        return departmentList.toArray(new Department[0]);
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Department> cq = cb.createQuery(Department.class);
        Root<Department> root = cq.from(Department.class);
        cq.select(root);
//        Predicate predicate = cb.equal(root.get("nameDep"), name);
        Predicate predicate = cb.equal(root.get(Department_.nameDep), name);
        cq.where(predicate);
        return em.createQuery(cq).getResultList().toArray(new Department[0]);
    }
    @Override
    public void insertClient(Client client) { //inserting incomplete Client object, without id (autogenerated by the dbms); default id=-1;
        em.getTransaction().begin();
        em.persist(client);
        em.getTransaction().commit();
    }
    @Override
    public void insertDepartment(Department department) { //inserting complete Department object, with id as departmentNo set (not autogenerated by the dbms); default id=-1;
        em.getTransaction().begin();
        em.persist(department);
        em.getTransaction().commit();
    }
    @Override
    public void insertDepartmentArray(Department[] department) { //inserting an array of Departments.
        em.getTransaction().begin();
        Arrays.asList(department).forEach(x->em.persist(x));
        em.getTransaction().commit();
    }
    @Override
    public void deleteClient(Client client) { //if argument client is a complete object (with id) remove it, if not, search for all registries that match the name of the argument passed and delete ALL of them. If an object without id were to be removed directly, the DB would not find it to remove. It should first be searched and retrived before being removed. Otherwise, if the id is defined at the object (!-1), and that entity instance is mapped at the bank, then the DB could remove it successfully using its id.
        em.getTransaction().begin();
        if(client.getId()==-1){
            Client[] clientTemp = searchClientByName(client.getName());
            Arrays.asList(clientTemp).forEach(x->em.remove(x));
        } else{
            em.remove(client);
        }
        em.getTransaction().commit();
    }
    @Override
    public void updateClient(Client oldClient, Client newClient) { //if argument oldClient is a complete object (with id) update it, if not, search for all registries that match the name of the argument passed and rename ALL of them. If an object without id were to be persisted directly (after being changed), the DB would not update any entity at the bank, it would create a new one with a new id. Otherwise, if the id is defined at the object (!-1), and that entity instance is mapped at the bank, it would be updated and the existing values overwritten, as desired. So, for entity instances without an id (mock objects), the real object should firstly be searched at and retrived from the DB before altering it and persisting it again (updating). If the object already has the id, which is expected to be immutable, it can be persisted directly right after having its state altered.
        em.getTransaction().begin();
        if(oldClient.getId()==-1){
            Client[] clientTemp = searchClientByName(oldClient.getName());
            Arrays.asList(clientTemp).forEach(x -> {
                if(x instanceof IndividualClient){
                    ((IndividualClient)x).setSSN(((IndividualClient)newClient).getSSN());
                } else{
                    ((CorporateClient)x).setFein(((CorporateClient)newClient).getFein());
                }
                x.setName(newClient.getName());
                x.setPhoneNo(newClient.getPhoneNo());
                x.setSalary(newClient.getSalary());
                x.setBirthDate(newClient.getBirthDate());
                x.setRegisteredIn(newClient.getRegisteredIn());
                Department d = newClient.getDepartment();
                d.setClient(x); //the department received must have its client attribute set to a real Client, and not to a mock one (if the setting occured before the search ou outside, at the Main).
                x.setDepartment(d);
                em.persist(x);
            });
        } else{
            if(oldClient instanceof IndividualClient){
                ((IndividualClient)oldClient).setSSN(((IndividualClient)newClient).getSSN());
            } else{
                ((CorporateClient)oldClient).setFein(((CorporateClient)newClient).getFein());
            }
            oldClient.setName(newClient.getName());
            oldClient.setPhoneNo(newClient.getPhoneNo());
            oldClient.setSalary(newClient.getSalary());
            oldClient.setBirthDate(newClient.getBirthDate());
            oldClient.setRegisteredIn(newClient.getRegisteredIn());
            oldClient.setDepartment(newClient.getDepartment()); //the department received has already set its Client attribute value a real Client object - associated to oldClient variable - outside, at the Main.
            em.persist(oldClient);
        }
        em.getTransaction().commit();
    }
    @Override
    public void updateClientUniqueThreadSafe(Client oldClient, Client newClient) { //similar to the updateClient but for known unique and complete objects - Client instances (with Id), only for testing LockModeType.OPTIMISTIC_FORCE_INCREMENT and concurrance @Version control.
        EntityManager em2 = factory.createEntityManager(); //a second EntityManager thread is created for managing the same client2 row at the table. The commit of this em2 goes on with no problems, as the Version is the same (when finding and when commiting the Client instance). But the second commit (with the main EntityManager), right after, tries to persist on the DB an Entity instance whose Version value is not the one it got when finding that instance (before the em2 commit), and, thus, accuses the error and proceedes a rollback of the later update transaction... thanks to the LockModeType.OPTIMISTIC_FORCE_INCREMENT and concurrance @Version control of the Client. Otherwise there would be inconsistencies persisted at the DB.
        em2.getTransaction().begin();
        Client lockedClient2 = em2.find(Client.class, 2L, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        long em2_initial_Client2_Version = lockedClient2.getVersion();
        System.out.println("EntityManager2 found Client2 with Version="+em2_initial_Client2_Version+". And sets a crazy Client name: Clientsdkjnvshdvbsldvsdg.");
        lockedClient2.setName("Clientsdkjnvshdvbsldvsdg");
        em2.persist(lockedClient2);
        
        em.getTransaction().begin();
        Client lockedClient=em.find(Client.class, oldClient.getId(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        long em_initial_Client2_Version = lockedClient.getVersion();
        System.out.println("EntityManager1 found Client2 with Version="+em_initial_Client2_Version+". And sets the required updating values as set on the newClient argument passed...");
        if(oldClient.getId()>-1){
            if(lockedClient instanceof IndividualClient){
                ((IndividualClient)lockedClient).setSSN(((IndividualClient)newClient).getSSN());
            } else{
                ((CorporateClient)lockedClient).setFein(((CorporateClient)newClient).getFein());
            }
            lockedClient.setName(newClient.getName());
            lockedClient.setPhoneNo(newClient.getPhoneNo());
            lockedClient.setSalary(newClient.getSalary());
            lockedClient.setBirthDate(newClient.getBirthDate());
            lockedClient.setRegisteredIn(newClient.getRegisteredIn());
            lockedClient.setDepartment(newClient.getDepartment());
            em.persist(lockedClient);
        }
        
        em2.refresh(lockedClient2);
        long em2_Commiting_Client2_Version = lockedClient2.getVersion();
        em2.getTransaction().commit();
        System.out.println("EntityManager2 committed successfully !! No Version inconsistency found when comparing the committing instance Version value to the Version value got when the Client instance was initially found !");
        System.out.println("EntityManager2 -> InitialVersion: "+em2_initial_Client2_Version+" = CommittingVersion: "+em2_Commiting_Client2_Version+" "+((em2_initial_Client2_Version==em2_Commiting_Client2_Version)?"OK!":"NOT OK!"));
        em2.close();
        
        em.refresh(lockedClient);
        long em_Commiting_Client2_Version = lockedClient.getVersion();
        System.out.println("EntityManager1, on the other hand, does not commit, and will roll back the transaction!! There will be the expected inconsistency when comparing the committing instance Version value to the Version value got when the Client instance was initially found.");
        System.out.println("EntityManager1 -> InitialVersion: "+em_initial_Client2_Version+" VS CommittingVersion: "+em_Commiting_Client2_Version+" "+((em_initial_Client2_Version==em_Commiting_Client2_Version)?"OK!":"NOT OK!"));
        em.getTransaction().commit();
    }
    @Override
    public void updateDepartment(Department oldDepartment, Department newDepartment) { //if argument oldDepartment is a complete object (with id) update it, if not, search for all registries that match the name of the argument passed and rename ALL of them.
        em.getTransaction().begin();
        if(oldDepartment.getDepartmentNo()==-1){
            Department[] departmentTemp = searchDepartmentByName(oldDepartment.getNameDep());
            Arrays.asList(departmentTemp).forEach(x -> {
                x.setNameDep(newDepartment.getNameDep());
                x.setOperationPeriod(newDepartment.getOperationPeriod());
                x.setAddress(newDepartment.getAddress());
                Client c = newDepartment.getClient();
                c.setDepartment(x);
                x.setClient(c);
                em.persist(x);
            });
        } else{
            oldDepartment.setNameDep(newDepartment.getNameDep());
            oldDepartment.setOperationPeriod(newDepartment.getOperationPeriod());
            oldDepartment.setAddress(newDepartment.getAddress());
            oldDepartment.setClient(newDepartment.getClient());
            em.persist(oldDepartment);
        }
        em.getTransaction().commit();
    }
    @Override
    public void updateDepartmentNameBulk(String nameToBeAddedToAllDepartments) { //just for showing a sample of the use of bulk update - updating all registries at once. It could have been done a selection, based on passed arguments, for updating only a group of instances (or registries), though.
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaUpdate<Department> crup = cb.createCriteriaUpdate(Department.class);
        Root<Department> root = crup.from(Department.class);
//        crup.set(root.<String>get("nameDep"), cb.concat(root.<String>get("nameDep")," "+nameToBeAddedToAllDepartments));
        crup.set(root.get(Department_.nameDep), cb.concat(root.get(Department_.nameDep)," "+nameToBeAddedToAllDepartments));
        em.createQuery(crup).executeUpdate();
        em.getTransaction().commit();
    }
    @Override
    public void deleteDepartmentBulk(long departmentNoFromWhichToStartDeleting) { //the same for bulk delete.
        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaDelete<Department> cd = cb.createCriteriaDelete(Department.class);
        Root<Department> root = cd.from(Department.class);
//        cd.where(cb.greaterThanOrEqualTo(root.<Long>get("departmentNo"), departmentNoFromWhichToStartDeleting));
        cd.where(cb.greaterThanOrEqualTo(root.<Long>get(Department_.departmentNo), departmentNoFromWhichToStartDeleting));
        em.createQuery(cd).executeUpdate();
        em.getTransaction().commit();
    }
}
