package pharmacistPatientApp.entity;
import java.util.List;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Patient.class)
public class Patient_ {
    public static volatile SingularAttribute<Patient,Long> id;
    public static volatile SingularAttribute<Patient,String> name;
    public static volatile SingularAttribute<Patient,Address> address;
    public static volatile SingularAttribute<Patient,List<String>> contactList;
    public static volatile SingularAttribute<Patient,HealthRecord> healthRecord;
}
