package tn.esprit.gnbapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class creditDemand implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cd;
    private long sum_cd;
    private int duration_cd;
    private  boolean status_cd ;
    private double value_cd;
    private float interestRate;



    @OneToMany(mappedBy = "creditDemand", cascade = CascadeType.ALL)
    private List<amortissement> amortisationTable;

    @Enumerated(EnumType.STRING)
    private typeCd typecd;
    /**  @OneToMany(mappedBy = "creditDemand")
      private Set<account> accounts; **/
    @OneToOne
    private credit credit;
    @ManyToOne
    private account account;


    }
