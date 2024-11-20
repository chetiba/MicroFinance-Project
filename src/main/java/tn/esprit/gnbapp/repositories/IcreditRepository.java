package tn.esprit.gnbapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gnbapp.entities.credit;

public interface IcreditRepository extends JpaRepository<credit, Integer> {
}
