package model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
public class StringLongConverter implements AttributeConverter<String, Long>{

    @Override
    public Long convertToDatabaseColumn(String x) {
        return Long.valueOf(x);
    }

    @Override
    public String convertToEntityAttribute(Long y) {
        if(y<10000000000L){
            return "0"+String.valueOf(y);
        }
        return String.valueOf(y);
    }
}
