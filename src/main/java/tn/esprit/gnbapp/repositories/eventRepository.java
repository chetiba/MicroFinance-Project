package tn.esprit.gnbapp.repositories;

import jdk.jfr.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.gnbapp.entities.event;

import java.util.List;

public interface eventRepository extends JpaRepository<event, Integer> {



}