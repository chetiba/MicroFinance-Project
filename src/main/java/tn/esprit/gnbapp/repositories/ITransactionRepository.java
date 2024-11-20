package tn.esprit.gnbapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.gnbapp.entities.transaction;

import java.util.List;
import java.util.Optional;

public interface ITransactionRepository extends JpaRepository<transaction, Integer> {
    @Query("SELECT t FROM transaction t WHERE t.account.rib= :rib")
    List<transaction> getTransactionEmiseByRibAccount(@Param("rib")  Long rib);

    @Query("SELECT T FROM transaction T WHERE T.RibRecipient= :rib OR T.RibEmetteur= :rib ")
    List<transaction> getTransactionByRibAccount(@Param("rib") long rib);

    @Override
    Optional<transaction> findById(Integer integer);
}