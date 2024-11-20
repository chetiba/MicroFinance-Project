package tn.esprit.gnbapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.gnbapp.dto.FeedbackSummaryDto;
import tn.esprit.gnbapp.entities.event;
import tn.esprit.gnbapp.entities.feedback;

import java.util.List;

public interface IFeedbackRepository extends JpaRepository<feedback, Integer> {
    List<feedback> findByEvent(event event);

    @Query("SELECT AVG(f.stars_fb) FROM feedback f WHERE f.event = :event")
    Double getAverageStarRatingForEvent(@Param("event") event event);

   @Query("SELECT COUNT(f) FROM feedback f WHERE f.event = :event")
    Long getTotalNumberOfFeedbackForEvent(@Param("event") event event);

    @Query("SELECT f.comment_fb FROM feedback f WHERE f.event = :event AND f.comment_fb IS NOT NULL AND f.comment_fb <> ''")
    List<String> getCommentsForEvent(@Param("event") event event);
    //public List<feedback> generateFeedbackSummary(int eventId);
    //public List<FeedbackSummaryDto> generateFeedbackSummary(int id_event);
}