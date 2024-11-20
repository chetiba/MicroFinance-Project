package tn.esprit.gnbapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.gnbapp.entities.consultation;

import java.time.LocalDate;

public interface IconsultationRepository extends JpaRepository<consultation, Integer> {
    @Query("SELECT c.creationdate_cons FROM  consultation c where c.id_cons = :id_cons")
    LocalDate findDateConsById(int id_cons);

    @Query("select c.user.lastname from consultation c where c.id_cons = :id_cons")
    String findLastName(int id_cons);
    @Query("select c.user.firstname from consultation c where c.id_cons = :id_cons")
    String findFirstName(int id_cons);
    @Query("select c.user.job from consultation c where c.id_cons = :id_cons")
    String findJob(int id_cons);
    @Query("select c.user.phonenumber from consultation c where c.id_cons = :id_cons")
    String Contact(int id_cons);
    @Query("SELECT c.code_cons FROM  consultation c where c.id_cons = :id_cons")
    int Verify(int id_cons);

}