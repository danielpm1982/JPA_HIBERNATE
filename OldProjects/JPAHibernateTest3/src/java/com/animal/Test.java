package com.animal;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Test {
    public static void main(String[] args){
        
        Animal animal1 = new Animal();
        animal1.setName("indefinido");
        
        Cat cat1 = new Cat();
        cat1.setName("Elvis");
        String url = "..\\JPAHibernateTest3\\src\\java\\com\\animal\\cat1.jpg";
        try {
            cat1.setCatImg(Files.readAllBytes(new File(url).toPath()));
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Dog dog1 = new Dog();
        dog1.setName("Luke");
        url = "..\\JPAHibernateTest3\\src\\java\\com\\animal\\dog1.jpg";
        try {
            dog1.setDogImg(Files.readAllBytes(new File(url).toPath()));
        } catch (IOException ex) {
            Logger.getLogger(Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPAHibernateTest3PU");
        EntityManager em = factory.createEntityManager();
        em.getTransaction().begin();
        em.persist(animal1);
        em.persist(cat1);
        em.persist(dog1);
        em.getTransaction().commit();
    }
}
