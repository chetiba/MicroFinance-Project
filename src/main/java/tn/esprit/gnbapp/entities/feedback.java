package tn.esprit.gnbapp.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class feedback implements Serializable , Comparable<feedback> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id_fb;
    @Min(value = 0, message = "Star rating must be at least 0")
    @Max(value = 5, message = "Star rating cannot be greater than 5")
    private double stars_fb ;
    private String comment_fb ;

    @ManyToOne
    private event event;

    public int getId_fb() {
        return id_fb;
    }

    public void setId_fb(int id_fb) {
        this.id_fb = id_fb;
    }

    public double getStars_fb() {
        return stars_fb;
    }

    public void setStars_fb(double stars_fb) {
        this.stars_fb = stars_fb;
    }

    public String getComment_fb() {
        return comment_fb;
    }

    public void setComment_fb(String comment_fb) {
        this.comment_fb = comment_fb;
    }

    public tn.esprit.gnbapp.entities.event getEvent() {
        return event;
    }

    public void setEvent(tn.esprit.gnbapp.entities.event event) {
        this.event = event;
    }
    @Override
    public int compareTo(feedback other) {
        return Double.compare(this.getStars_fb(), other.getStars_fb());
    }
}
