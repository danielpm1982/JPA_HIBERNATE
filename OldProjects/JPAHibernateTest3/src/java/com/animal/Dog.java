package com.animal;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.TableGenerator;

@Entity
public class Dog extends Animal implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Column(name="dog_image")
    @Lob
    private byte[] dogImg;

    public byte[] getDogImg() {
        return dogImg;
    }

    public void setDogImg(byte[] dogImg) {
        this.dogImg = dogImg;
    }
}
