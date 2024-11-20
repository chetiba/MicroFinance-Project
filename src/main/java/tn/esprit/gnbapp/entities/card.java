package tn.esprit.gnbapp.entities;


import java.io.Serializable;
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
@ToString
public class card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_card;
    private long num_card;
    private LocalDate expirationdate_card;
    private String code_card;


}
