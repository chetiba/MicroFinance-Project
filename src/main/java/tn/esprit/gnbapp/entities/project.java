package tn.esprit.gnbapp.entities;

import lombok.*;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.*;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class project implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String description;
    private String estimated_time;
    private float investable_amout;
    private int numberInv;
    private Double shareInv;



    @ManyToMany
    private Set<user> users;


}
