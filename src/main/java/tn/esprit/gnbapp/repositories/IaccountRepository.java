package tn.esprit.gnbapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gnbapp.entities.account;

public interface IaccountRepository extends JpaRepository<account, Integer> {
}