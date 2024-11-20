package tn.esprit.gnbapp.entities;

import lombok.*;
import org.apache.catalina.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_event;
    private String name_event;
    @Enumerated(EnumType.STRING)
    private TypeEvent type_event;
    private double price_event;
    private String place_event ;
    private LocalDate date_event ;
    private String description_event ;
    private int likes;
    private int dislikes;

    @ElementCollection
    private Set<Integer> likesByUser;
    @ElementCollection
    private Set<Integer> dislikesByUser;


//many to one yaani event howa lparent w user howa child w generalement dima li aal ymin fel relation howa mta l'entité expl many events to one user
    //fel many to one hedhi, khdhina l primary key mta l user hatineha foreign key fel event li ahna fih
    @ManyToOne
   public user user;
    //mapped by khater event howa lchild
    @OneToMany(mappedBy = "event")
    private Set<feedback> feedbacks;



}
