package tn.esprit.gnbapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gnbapp.entities.card;

public interface IcardRepository extends JpaRepository<card, Integer> {
}