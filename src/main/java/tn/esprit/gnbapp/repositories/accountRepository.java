package tn.esprit.gnbapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.gnbapp.entities.account;
import tn.esprit.gnbapp.entities.typeAcc;

import java.util.List;
import java.util.Optional;


public interface accountRepository extends JpaRepository<account, Integer> {

    List<account> findByTypeacc(typeAcc type);
    List<account> findByUser_Cin(long cin );
    Optional<account> findByRib(String rib);
    Long countByTypeacc(typeAcc type);



}