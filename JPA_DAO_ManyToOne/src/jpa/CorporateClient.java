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
@Table(name="CORPORATE_CLIENT")
public class CorporateClient extends Client{
    @Column(name="FEIN", nullable=true)
    private long fein; // Federal Employer Identification Number.
    public CorporateClient(long fein, String name, List<String> phoneNo, BigDecimal salary, LocalDate birthDate, LocalDateTime registeredIn, Department department) {
        super(name, phoneNo, salary, birthDate, registeredIn, department);
        this.fein = fein;
    }
    public CorporateClient() {
        super(null, null, null, null, null, null);
    }
    public long getFein() {
        return fein;
    }
    public void setFein(long fein) {
        this.fein = fein;
    }
    @Override
    public String toString() {
        return "id: "+super.getId()+" fein: "+fein+" name: "+super.getName()+" phoneNo: "+super.getPhoneNo().stream().reduce("",(x,y)->x+" "+y)+" salary: "+super.getSalary()+" birthDate: "+super.getBirthDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))+" registeredIn: "+super.getRegisteredIn().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))+" department: "+super.getDepartment();
    }
}
