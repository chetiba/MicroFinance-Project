package tn.esprit.gnbapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.gnbapp.entities.user;

import java.util.Collection;

public interface IuserRepository extends JpaRepository<user, Integer> {



}