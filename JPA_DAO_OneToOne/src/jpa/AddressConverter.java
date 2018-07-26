package jpa;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class AddressConverter implements AttributeConverter<Address, String>{
    @Override
    public String convertToDatabaseColumn(Address address) {
        return address.toStringForConvertion();
    }
    @Override
    public Address convertToEntityAttribute(String addressString) {
        String[] tempArray = addressString.split("/");
        return new Address(tempArray[0], Integer.valueOf(tempArray[1]), tempArray[2], tempArray[3], tempArray[4]);
    }
    
}
