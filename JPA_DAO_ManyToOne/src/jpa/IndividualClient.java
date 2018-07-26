package jpa;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="INDIVIDUAL_CLIENT")
public class IndividualClient extends Client{
    @Column(name="SSN", nullable=true)
    private long SSN; // Social Security number.
    public IndividualClient(long SSN, String name, List<String> phoneNo, BigDecimal salary, LocalDate birthDate, LocalDateTime registeredIn, Department department) {
        super(name, phoneNo, salary, birthDate, registeredIn, department);
        this.SSN = SSN;
    }
    public IndividualClient() {
        super(null, null, null, null, null, null);
    }
    public long getSSN() {
        return SSN;
    }
    public void setSSN(long SSN) {
        this.SSN = SSN;
    }
    @Override
    public String toString() {
        return "id: "+super.getId()+" ssn: "+SSN+" name: "+super.getName()+" phoneNo: "+super.getPhoneNo().stream().reduce("",(x,y)->x+" "+y)+" salary: "+super.getSalary()+" birthDate: "+super.getBirthDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))+" registeredIn: "+super.getRegisteredIn().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))+" department: "+super.getDepartment();
    }
}
