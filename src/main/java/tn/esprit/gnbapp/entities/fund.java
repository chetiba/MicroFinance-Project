package tn.esprit.gnbapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class fund implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idFund ;
    private float amountFund ;
    private float tauxFund ;
    private float tauxGain ;
    @JsonIgnore
    @OneToMany(mappedBy = "fund")
    private Set<investesment> investesments;
}
