package pharmacistPatientApp.entity;
import java.util.List;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pharmacistPatientApp.helper.SpecialtyType;

@StaticMetamodel(Pharmacist.class)
public class Pharmacist_ {
    public static volatile SingularAttribute<Pharmacist,Long> id;
    public static volatile SingularAttribute<Pharmacist,String> name;
    public static volatile SingularAttribute<Pharmacist,Address> address;
    public static volatile SingularAttribute<Pharmacist,List<String>> contactList;
    public static volatile SingularAttribute<Pharmacist,SpecialtyType> specialty;
    public static volatile SingularAttribute<Pharmacist,List<Consultation>> consultationList;
}
