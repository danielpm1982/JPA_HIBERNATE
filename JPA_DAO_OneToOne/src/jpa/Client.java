package jpa;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import javax.persistence.*;
import org.hibernate.envers.Audited;

@Entity
@Table(name="CLIENT")
@Access(AccessType.FIELD)
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
//@Inheritance(strategy=InheritanceType.JOINED)
//@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@Cacheable(false)
//@EntityListeners(CallbackMethodsClient.class)
@NamedQueries({
    @NamedQuery(name="Client.searchAllClients",query="select e from Client e"),
    @NamedQuery(name="Client.searchClientByName",query="select e from Client e where e.name =:name"),
    @NamedQuery(name="Client.searchClientById",query="select e from Client e where e.id =:id"),
    @NamedQuery(name="Client.searchAllClientsNamesWithDepartmentNames",query="select new jpa.ClientNameDepName(e.name, e.department.nameDep) from Client e"),
})
public abstract class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private long id;
    @Column(name="NAME", length=60, unique=false, nullable=false)
//    @Basic(fetch=FetchType.LAZY) //for find() the default would be eager, and for getReference() the deafult would be lazy.
    private String name;
    @ElementCollection
    @CollectionTable(name="CLIENT_PHONE_NUMBER", joinColumns=@JoinColumn(name="CLIENT_ID"))
    @Column(name="PHONE_NO", nullable=false)
    private List<String> phoneNo;
    @Column(name="SALARY", precision=11, scale=2, nullable=false)
    private BigDecimal salary;
    @Column(name="BIRTH_DATE", nullable=false)
//    @Temporal(TemporalType.DATE) Does not work on the new java.time api, only with the old date/time/Calendar api.
    private LocalDate birthDate;
    @Column(name="REGISTERED_IN", nullable=false)
//    @Temporal(TemporalType.TIMESTAMP) Does not work on the new java.time api, only with the old date/time/Calendar api.
    private LocalDateTime registeredIn;
    @OneToOne(optional=false, fetch=FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
    @JoinColumn(name="DEPARTMENT_NO", nullable=false)
    private Department department;
    @Transient
    private String unmappedFakeAttribute; //just for testing transient annotation
    @Version
    @Column(name="VERSION")
    private long version;
    public Client(String name, List<String> phoneNo, BigDecimal salary, LocalDate birthDate, LocalDateTime registeredIn, Department department) {
        this.id=-1; //real id defined at the dbms, not here.
        this.name = name;
        this.phoneNo=phoneNo;
        this.salary = salary;
        this.birthDate = birthDate;
        this.registeredIn = registeredIn;
        this.department=department;
    }
    public Client() {
        this(null,null,null,null,null,null);
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<String> getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(List<String> phoneNo) {
        this.phoneNo = phoneNo;
    }
    public BigDecimal getSalary() {
        return salary;
    }
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public LocalDateTime getRegisteredIn() {
        return registeredIn;
    }
    public void setRegisteredIn(LocalDateTime registeredIn) {
        this.registeredIn = registeredIn;
    }
    public Department getDepartment() {
        return department;
    }
    public void setDepartment(Department department) {
        this.department = department;
    }
    public long getVersion() {
        return version;
    }
    public String getClientWithoutId() {
        return "name: "+name+" salary: "+salary+" birthDate: "+birthDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))+" registeredIn: "+registeredIn.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))+" department: "+department;
    }
    @Override
    public String toString() {
        return "id: "+id+" name: "+name+" phoneNo: "+phoneNo.stream().reduce("",(x,y)->x+" "+y)+" salary: "+salary+" birthDate: "+birthDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))+" registeredIn: "+registeredIn.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))+" department: "+department;
    }
}
