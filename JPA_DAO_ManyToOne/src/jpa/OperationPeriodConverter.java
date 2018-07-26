package jpa;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
public class OperationPeriodConverter implements AttributeConverter<Period, String>{
    @Override
    public String convertToDatabaseColumn(Period period) {
        switch(period){
            case FULL_24_HOURS:
                return "Full-Time";
            case PARTIAL_BUSINESS_HOURS:
                return "Partial-Time";
            default:
                return "Invalid/Unknown Period";
        }
    }
    @Override
    public Period convertToEntityAttribute(String periodString) {
        switch(periodString){
            case "Full-Time":
                return Period.FULL_24_HOURS;
            case "Partial-Time":
                return Period.PARTIAL_BUSINESS_HOURS;
            default:
                return null;
        }
    }
}
