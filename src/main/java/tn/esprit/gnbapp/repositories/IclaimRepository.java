package tn.esprit.gnbapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gnbapp.entities.claim;

public interface IclaimRepository extends JpaRepository<claim, Integer> {


}