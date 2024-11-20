package tn.esprit.gnbapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.gnbapp.entities.credit;
import tn.esprit.gnbapp.entities.creditDemand;

import java.util.Optional;
public interface creditRepository extends JpaRepository<credit, Integer> {
    @Query("select c.creditDemand from credit c where c.creditDemand.id_cd = :id_cd")
    creditDemand findCreditDemand(int id_cd);

}