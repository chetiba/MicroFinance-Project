package tn.esprit.gnbapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString
public class account implements Serializable {
    @Id
    @Column(name ="id_acc")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_acc;
    @Column
    private String rib;
    private LocalDate creationdate_acc;
    private double balance_acc;
    private float interest;
    private int index_interest;

    @Enumerated(EnumType.STRING)
    private typeAcc typeacc;
    @OneToOne(mappedBy = "account")
    private user user;

    public tn.esprit.gnbapp.entities.user getUser() {
        return user;
    }

    public void setUser(tn.esprit.gnbapp.entities.user user) {
        this.user = user;
    }

    @ManyToOne
    private creditDemand creditDemand;
    @OneToOne
    private card card;
    @OneToMany(mappedBy = "account")
    private Set<transaction> transactions;
    @ManyToOne
    private user user;
}
