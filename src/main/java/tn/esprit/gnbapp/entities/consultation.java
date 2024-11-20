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
public class consultation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cons;
    private String motive_cons;
    private LocalDate creationdate_cons;
    private boolean status_cons=false ;

    private int code_cons;

    @Enumerated(EnumType.STRING)
    private  typeCons typecons ;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private user user;


}
