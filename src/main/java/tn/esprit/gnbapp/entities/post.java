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
public class post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_post;
    private String text_post;
    private boolean status_post;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private user user;
    @JsonIgnore
    @OneToMany(mappedBy = "post",cascade = CascadeType.REMOVE)
    private Set<question> questions;

}
