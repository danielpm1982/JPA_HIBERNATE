package jpa;
import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="DEPARTMENT")
@Access(AccessType.PROPERTY)
@Cacheable(true)
//@EntityListeners(CallbackMethodsDepartment.class)
@NamedQueries({
    @NamedQuery(name="Department.searchAllDepartments",query="select d from Department d"),
    @NamedQuery(name="Department.searchDepartmentByName",query="select d from Department d where d.nameDep=:name")
})
public class Department implements Serializable {
    private long departmentNo;
    private String nameDep;
    private Period operationPeriod;
    private Address address;
    private Client client;
    public Department() {
        this(-1,"", Period.PARTIAL_BUSINESS_HOURS,null);
    }
    public Department(long departmentNo, String nameDep, Period operationPeriod, Address address) {
        this.departmentNo = departmentNo;
        this.nameDep = nameDep;
        this.operationPeriod=operationPeriod;
        this.address=address;
    }
    @Id
    @Column(name="DEPARTMENT_NO",nullable=false)
    public long getDepartmentNo() {
        return departmentNo;
    }
    public void setDepartmentNo(long departmentNo) {
        this.departmentNo = departmentNo;
    }
    @Column(name="NAME_DEP",unique=true,nullable=false)
//    @Basic(fetch=FetchType.LAZY) //for find() the default would be eager, and for getReference() the deafult would be lazy.
    public String getNameDep() {
        return nameDep;
    }
    public void setNameDep(String nameDep) {
        this.nameDep = nameDep;
    }
    @Column(name="OPERATION_PERIOD", nullable=true)
//    @Enumerated(EnumType.STRING)
    @Convert(converter=OperationPeriodConverter.class)
    public Period getOperationPeriod() {
        return operationPeriod;
    }
    public void setOperationPeriod(Period operationPeriod) {
        this.operationPeriod = operationPeriod;
    }
//    @Embedded
    @Convert(attributeName="address",converter=AddressConverter.class)
    @Column(name="ADDRESS",nullable=true)
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    @OneToOne(mappedBy="department", fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
    @JoinColumn(name="CLIENT_ID", nullable=true)
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    @Override
    public String toString() {
        return "departmentNo: "+departmentNo+" nameDep: "+nameDep+" operationPeriod: "+operationPeriod+" address: "+address;
    }
}
