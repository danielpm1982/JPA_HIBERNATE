package jpa;
import javax.persistence.*;

public class CallbackMethodsClient extends CallbackMethods{
    @PrePersist
    @Override
    public void prePersist(Object o) {
        super.prePersist(o);
        System.out.println(" Class: Client. "+o);
    }
    @PreRemove
    @Override
    public void preRemove(Object o) {
        super.preRemove(o);
        System.out.println(" Class: Client. "+o);
    }
    @PreUpdate
    @Override
    public void preUpdate(Object o) {
        super.preUpdate(o);
        System.out.println(" Class: Client. "+o);
    }
    @PostPersist
    @Override
    public void postPersist(Object o) {
        super.postPersist(o);
        System.out.println(" Class: Client. "+o);
    }
    @PostRemove
    @Override
    public void postRemove(Object o) {
        super.postRemove(o);
        System.out.println(" Class: Client. "+o);
    }
    @PostUpdate
    @Override
    public void postUpdate(Object o) {
        super.postUpdate(o);
        System.out.println(" Class: Client. "+o);
    }
    @PostLoad
    @Override
    public void postLoad(Object o) {
        super.postLoad(o);
        System.out.println(" Class: Client. "+o);
    }
}
