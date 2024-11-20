package tn.esprit.gnbapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gnbapp.entities.post;

public interface IpostRepository extends JpaRepository<post, Integer> {
}