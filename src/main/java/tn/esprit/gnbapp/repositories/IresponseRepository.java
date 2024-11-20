package tn.esprit.gnbapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gnbapp.entities.response;

public interface IresponseRepository extends JpaRepository<response, Integer> {
}