package tn.esprit.gnbapp.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class claim implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_claim;
    private LocalDate creationdate_claim;
    private String description_claim;
    private Boolean state ;



    @Enumerated(EnumType.STRING)
    private subjectClaim subjectclaim;
    @ManyToOne
    private credit credit;
}
