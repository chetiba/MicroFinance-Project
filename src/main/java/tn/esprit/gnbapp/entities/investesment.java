package tn.esprit.gnbapp.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class investesment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idInvestesment ;
    private long cinInvestesment ;
    private String nameInvestesment ;
    private String secondnnameInvestesment ;
    private String professionInvestesment ;
    private float tauxInves;
    private boolean state ;
    private float amoutInvestesment;
    private String mailInvestesment;
    private float finalAmount;

    @ManyToOne
    private fund fund;

}
