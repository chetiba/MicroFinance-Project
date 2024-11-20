package tn.esprit.gnbapp.controllers;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gnbapp.dto.FeedbackSummaryDto;
import tn.esprit.gnbapp.entities.event;
import tn.esprit.gnbapp.entities.feedback;
import tn.esprit.gnbapp.entities.transaction;
import tn.esprit.gnbapp.repositories.IEventRepository;
import tn.esprit.gnbapp.repositories.IFeedbackRepository;
import tn.esprit.gnbapp.services.IFeedbackServices;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import java.util.Collections;
import java.util.List;
@RestController
@RequiredArgsConstructor
@ComponentScan
@RequestMapping("/feedback")

public class FeedBackRestController {
    private final IFeedbackServices feedbackServices;
    private final IFeedbackRepository feedbackRepository;
    private final IEventRepository eventRepository;
    @PostMapping("/add")
    feedback addFeedBack(@RequestBody feedback feedback ){
        return feedbackServices.addOrUpdateFeedback(feedback);
    }
    @PutMapping("/update")
    feedback UpdateFeedBack(@RequestBody feedback feedback ){
        return feedbackServices.addOrUpdateFeedback(feedback);
    }

    @GetMapping("/get/{id}")
    feedback getFeedBack(@PathVariable("id")Integer id_fb)
    {
        return feedbackServices.retrieveFeedback(id_fb);
    }
    @GetMapping("/all")
    List<feedback>getAllFeedBack() {
        return feedbackServices.retrieveAllFeedback();
    }
    @DeleteMapping("/delete/{id}")
    void deleteFeedback(@PathVariable("id")Integer id_fb)
    {
        feedbackServices.removeFeedback(id_fb);
    }

    @GetMapping("/feedback")
    public @ResponseBody String getAllFeedbacks() {
        // This returns a JSON or XML with the Feedbacks
        Iterable<feedback> allFB = feedbackRepository.findAll();
        return "<html>\n"
                + "<head>\n"
                + "	<style>\n"
                + "		.center {\n"
                + "	  		text-align: center;\n"
                + "	  	}\n"
                + "	  	\n"
                + "	</style>\n"
                + "</head>\n"
                + "<body style=\"background-color:lightblue;\">\n"
                + "	<div class=\"center\">\n"
                + "<h1>Feedback Table</h1>\n"
                + allFB.toString()
                + "	</div>\n"
                + "</body>\n"
                + "</html>";
    }
    @GetMapping("/sorted-by-stars")
    public List<feedback> getFeedbackSortedByStars() {
        List<feedback> feedbackList = feedbackServices.retrieveAllFeedback();
        Collections.sort(feedbackList);
        return feedbackList;
    }
    @GetMapping("/events/{id_event}/feedback-summary-pdf")
    public ResponseEntity<byte[]> generateFeedbackSummaryPdf(@PathVariable int id_event) throws IOException {
        List<FeedbackSummaryDto> feedbackSummaries = feedbackServices.generateFeedbackSummary(id_event);
        byte[] pdfBytes = feedbackServices.generateFeedbackSummaryPDF(feedbackSummaries);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("feedback-summary.pdf").build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}
