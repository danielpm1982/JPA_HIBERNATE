package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Table(name = "Author")
public class Author implements Serializable{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "birthDate") //@Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime birth;
    
    public Author() {
    }
    
    public Author(Long id, String name, LocalDateTime birth) {
        this.id = id;
        this.name = name;
        this.birth=birth;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirth() {
        return birth;
    }

    public void setBirth(LocalDateTime birth) {
        this.birth = birth;
    }
    
    @Override
    public String toString() {
        return id+" "+name+" "+birth.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
