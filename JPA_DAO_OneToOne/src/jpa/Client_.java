package jpa;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Client.class)
public class Client_ {
    public static volatile SingularAttribute<Client,Long> id;
    public static volatile SingularAttribute<Client,String> name;
    public static volatile SingularAttribute<Client,List<String>> phoneNo;
    public static volatile SingularAttribute<Client,BigDecimal> salary;
    public static volatile SingularAttribute<Client,LocalDate> birthDate;
    public static volatile SingularAttribute<Client,LocalDateTime> registeredIn;
    public static volatile SingularAttribute<Client,Department> department;
    public static volatile SingularAttribute<Client,Long> version;
}
