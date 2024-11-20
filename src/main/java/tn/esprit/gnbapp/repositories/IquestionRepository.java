package tn.esprit.gnbapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gnbapp.entities.question;

public interface IquestionRepository extends JpaRepository<question, Integer> {
}