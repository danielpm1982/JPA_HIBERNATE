package jpa;
import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="DEPARTMENT")
@Access(AccessType.PROPERTY)
public class Department implements Serializable {
    private long departmentNo;
    private String nameDep;
    private Period operationPeriod;
    private Address address;
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
    @Override
    public String toString() {
        return "departmentNo: "+departmentNo+" nameDep: "+nameDep+" operationPeriod: "+operationPeriod+" address: "+address;
    }
}
