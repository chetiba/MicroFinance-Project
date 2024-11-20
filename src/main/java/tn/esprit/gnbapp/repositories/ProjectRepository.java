package tn.esprit.gnbapp.repositories;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.gnbapp.entities.project;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<project, Integer> {
    @Query("select p.name from project p")
    String findName();

}
