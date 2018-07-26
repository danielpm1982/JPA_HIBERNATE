package jpa;
import javax.persistence.*;

public class CallbackMethodsDepartment extends CallbackMethods{
    @PrePersist
    @Override
    public void prePersist(Object o) {
        super.prePersist(o);
        System.out.println(" Class: Department. "+o);
    }
    @PreRemove
    @Override
    public void preRemove(Object o) {
        super.preRemove(o);
        System.out.println(" Class: Department. "+o);
    }
    @PreUpdate
    @Override
    public void preUpdate(Object o) {
        super.preUpdate(o);
        System.out.println(" Class: Department. "+o);
    }
    @PostPersist
    @Override
    public void postPersist(Object o) {
        super.postPersist(o);
        System.out.println(" Class: Department. "+o);
    }
    @PostRemove
    @Override
    public void postRemove(Object o) {
        super.postRemove(o);
        System.out.println(" Class: Department. "+o);
    }
    @PostUpdate
    @Override
    public void postUpdate(Object o) {
        super.postUpdate(o);
        System.out.println(" Class: Department. "+o);
    }
    @PostLoad
    @Override
    public void postLoad(Object o) {
        super.postLoad(o);
        System.out.println(" Class: Department. "+o);
    }
}
