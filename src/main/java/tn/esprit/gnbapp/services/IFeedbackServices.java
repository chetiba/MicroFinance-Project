package tn.esprit.gnbapp.services;
import tn.esprit.gnbapp.entities.feedback;
import tn.esprit.gnbapp.entities.account;
import tn.esprit.gnbapp.dto.FeedbackSummaryDto;



import javax.mail.MessagingException;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.List;

public interface IFeedbackServices {
    List<feedback> retrieveAllFeedback();

    feedback addOrUpdateFeedback(feedback f)throws ConstraintViolationException;
    feedback  retrieveFeedback (Integer id_fb);
    void removeFeedback (Integer id_fb);
    public Iterable<feedback> GetAllFeedback();
    public List<feedback> getFeedbackSortedByStars();
    public List<FeedbackSummaryDto> generateFeedbackSummary(int id_event);

    public byte[] generateFeedbackSummaryPDF(List<FeedbackSummaryDto> feedbackSummaries) throws IOException;


}
