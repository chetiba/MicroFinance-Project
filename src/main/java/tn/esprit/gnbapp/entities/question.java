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
public class question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_question;
    private String txt_question;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private post post;
    @JsonIgnore
    @OneToMany(mappedBy = "question",cascade = CascadeType.REMOVE)
    private Set<response> responses;


}
