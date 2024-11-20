package tn.esprit.gnbapp.services;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import java.nio.file.Paths;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;

import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.gnbapp.entities.event;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import tn.esprit.gnbapp.entities.feedback;
import tn.esprit.gnbapp.exception.ResourceNotFoundException;
import tn.esprit.gnbapp.repositories.IEventRepository;
import tn.esprit.gnbapp.repositories.IFeedbackRepository;
import tn.esprit.gnbapp.dto.FeedbackSummaryDto;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.kernel.font.PdfFontFactory;

import javax.validation.*;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FeedBackServicesImpl implements IFeedbackServices{
    private final IFeedbackRepository feedbackRepository;
    private final IEventRepository eventRepository;
    public List<feedback> retrieveAllFeedback(){
        return feedbackRepository.findAll();
    }

    public feedback addOrUpdateFeedback(feedback f) throws ConstraintViolationException {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<feedback>> violations = validator.validate(f);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        // Save the Feedback object
        return feedbackRepository.save(f);
    }



    public feedback  retrieveFeedback(Integer id_fb){
        return feedbackRepository.findById(id_fb).orElse(null);
    }
    public void removeFeedback(Integer id_fb){
        feedbackRepository.deleteById(id_fb);

    }
    public Iterable<feedback> GetAllFeedback() {
        return feedbackRepository.findAll();
    }
    public List<feedback> getFeedbackSortedByStars() {
        List<feedback> feedbackList = feedbackRepository.findAll();
        Collections.sort(feedbackList); // or feedbackList.sort((f1, f2) -> Double.compare(f1.getStars_fb(), f2.getStars_fb()));
        return feedbackList;
    }
    @Autowired
    public FeedBackServicesImpl(IEventRepository eventRepository, IFeedbackRepository feedbackRepository) {
        this.eventRepository = eventRepository;
        this.feedbackRepository = feedbackRepository;}
    @Override
    public List<FeedbackSummaryDto> generateFeedbackSummary(int id_event) {
        List<FeedbackSummaryDto> feedbackSummaries = new ArrayList<>();
        event event = eventRepository.findById(id_event).orElse(null);
        if (event == null) {
            throw new ResourceNotFoundException("Event not found with id: " + id_event);
        }
        List<feedback> feedbackList = feedbackRepository.findByEvent(event);
        if (feedbackList.isEmpty()) {
            return feedbackSummaries;
        }
        double totalStars = 0;
        int totalFeedback = feedbackList.size();
        List<String> comments = new ArrayList<>();
        for (feedback feedback : feedbackList) {
            totalStars += feedback.getStars_fb();
            if (feedback.getComment_fb() != null && !feedback.getComment_fb().isEmpty()) {
                comments.add(feedback.getComment_fb());
            }
        }
        double averageStars = totalStars / totalFeedback;
        FeedbackSummaryDto feedbackSummary = new FeedbackSummaryDto();
        feedbackSummary.setAverageStars(averageStars);
        feedbackSummary.setTotalFeedback(totalFeedback);
        feedbackSummary.setComments(comments);
        feedbackSummaries.add(feedbackSummary);
        return feedbackSummaries;
    }

    // other methods
    public byte[] generateFeedbackSummaryPDF(List<FeedbackSummaryDto> feedbackSummaries) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(outputStream);
        com.itextpdf.kernel.pdf.PdfDocument pdf = new com.itextpdf.kernel.pdf.PdfDocument(writer);
        Document document = new Document(pdf);
        String fontFilePath = "C:\\Users\\LENOVO\\Downloads\\helveticabolditalic\\Helvetica BoldItalic.ttf";
        String absolutePath = Paths.get(fontFilePath).toAbsolutePath().toString();
        // Create PdfFont object from font file path
        PdfFont font = PdfFontFactory.createFont(fontFilePath, PdfEncodings.IDENTITY_H, true);

        // Add title to PDF
        Paragraph title = new Paragraph("Feedback Summary Report")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(DeviceRgb.BLUE)
                .setFontSize(20);

        document.add(title);
        // Add header to PDF
        Paragraph header = new Paragraph("THE GNB BANK")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(DeviceRgb.RED)
                .setFontSize(24)
                .setMarginTop(50);
        document.add(header);


        // Add feedback summaries to PDF
        Table table = new Table(new UnitValue[] { new UnitValue(20, UnitValue.PERCENT), new UnitValue(20, UnitValue.PERCENT), new UnitValue(60, UnitValue.PERCENT) });
        table.addHeaderCell(new Cell().add(new Paragraph("Average Stars").setFont(font)));
        table.addHeaderCell(new Cell().add(new Paragraph("Total Feedback").setFont(font)));
        table.addHeaderCell(new Cell().add(new Paragraph("Comments").setFont(font)));
        for (FeedbackSummaryDto feedbackSummary : feedbackSummaries) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(feedbackSummary.getAverageStars()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(feedbackSummary.getTotalFeedback()))));
            table.addCell(new Cell().add(new Paragraph(String.join(", ", feedbackSummary.getComments()))));
        }
        document.add(table);

        // Close the document
        document.close();
        return outputStream.toByteArray();
    }


}

