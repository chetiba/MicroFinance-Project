package tn.esprit.gnbapp.entities;

import lombok.*;
import tn.esprit.gnbapp.entities.credit;

import javax.persistence.*;
import java.util.Date;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class amortissement {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_amortis", nullable = false)
    private Long id_amortis;

    private Date date;
        private double remainingBalance;
        private double interest;
        private double principal;
        private double payment;

    @ManyToOne
    private tn.esprit.gnbapp.entities.credit credit;
    @ManyToOne
    private creditDemand creditDemand;
}
