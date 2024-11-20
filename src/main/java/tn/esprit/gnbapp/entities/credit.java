package tn.esprit.gnbapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString


public class credit implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_credit;
    private LocalDate startdate_credit;
    private LocalDate duedate_credit;
    private double amount_credit;
    private double remainingamount_credit ;
    private float interestRate;




    @OneToMany(mappedBy = "credit", cascade = CascadeType.ALL)
    private List<amortissement> amortizationTable;




    @OneToOne(cascade = CascadeType.REMOVE)
    private creditDemand creditDemand;
    @JsonIgnore
    @OneToMany(mappedBy = "credit",cascade = CascadeType.REMOVE)
    private Set<claim> claims;
    @ManyToOne
    @JsonIgnore
    private user user;
}
