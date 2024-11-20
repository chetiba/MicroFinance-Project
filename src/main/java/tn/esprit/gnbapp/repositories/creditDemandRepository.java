package tn.esprit.gnbapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.gnbapp.entities.credit;
import tn.esprit.gnbapp.entities.creditDemand;

import java.util.List;
import java.util.Optional;

public interface creditDemandRepository extends JpaRepository<creditDemand, Integer> {


    @Query("SELECT cd FROM creditDemand cd WHERE cd.account.id_acc = :id_acc")
    List<creditDemand> getCredit_acc(@Param("id_acc") int id_acc);

    Optional<creditDemand> findById(int id);
}