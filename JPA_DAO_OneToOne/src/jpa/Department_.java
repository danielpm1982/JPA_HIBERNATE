package jpa;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Department.class)
public class Department_ {
    public static volatile SingularAttribute<Department,Long> departmentNo;
    public static volatile SingularAttribute<Department,String> nameDep;
    public static volatile SingularAttribute<Department,Period> operationPeriod;
    public static volatile SingularAttribute<Department,Address> address;
    public static volatile SingularAttribute<Department,Client> client;
}
