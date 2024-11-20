package tn.esprit.gnbapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gnbapp.entities.user;

import java.util.Optional;

public interface userRepository extends JpaRepository<user, Integer> {
    user findById ( int id);
    Optional<user> findByEmail(String email);

}