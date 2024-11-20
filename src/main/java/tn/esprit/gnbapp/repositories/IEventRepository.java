package tn.esprit.gnbapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.gnbapp.entities.event;
import tn.esprit.gnbapp.entities.feedback;

import java.util.List;

public interface IEventRepository extends JpaRepository<event, Integer> {


   // List<feedback> findFeedbackByEventId(Long id_event);

}

