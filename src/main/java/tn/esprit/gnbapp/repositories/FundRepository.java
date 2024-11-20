package tn.esprit.gnbapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gnbapp.entities.fund;

public interface FundRepository extends JpaRepository<fund, Integer> {
}
