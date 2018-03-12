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
public class Cat extends Animal implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name="cat_image")
    @Lob
    private byte[] catImg;
    
    public byte[] getCatImg() {
        return catImg;
    }

    public void setCatImg(byte[] catImg) {
        this.catImg = catImg;
    }
}
