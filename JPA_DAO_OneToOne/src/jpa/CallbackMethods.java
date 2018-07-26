package jpa;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class CallbackMethods {    
    public void prePersist(Object o){
        String dateTime = ZonedDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.LONG));
        System.out.print("Entity instance event: prePersist has been caught at "+dateTime+".");
    }
    public void preRemove(Object o){
        String dateTime = ZonedDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.LONG));
        System.out.print("Entity instance event: preRemove has been caught at "+dateTime+".");
    }
    public void preUpdate(Object o){
        String dateTime = ZonedDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.LONG));
        System.out.print("Entity instance event: preUpdate has been caught at "+dateTime+".");
    }
    public void postPersist(Object o){
        String dateTime = ZonedDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.LONG));
        System.out.print("Entity instance event: postPersist has been caught at "+dateTime+".");
    }
    public void postRemove(Object o){
        String dateTime = ZonedDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.LONG));
        System.out.print("Entity instance event: postRemove has been caught at "+dateTime+".");
    }
    public void postUpdate(Object o){
        String dateTime = ZonedDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.LONG));
        System.out.print("Entity instance event: postUpdate has been caught at "+dateTime+".");
    }
    public void postLoad(Object o){
        String dateTime = ZonedDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.LONG));
        System.out.print("Entity instance event: postLoad has been caught at "+dateTime+".");
    }
}
