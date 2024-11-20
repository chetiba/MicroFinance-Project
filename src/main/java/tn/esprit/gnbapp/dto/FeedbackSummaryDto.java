package tn.esprit.gnbapp.dto;

import java.util.ArrayList;
import java.util.List;

public class FeedbackSummaryDto {
    private double averageStars;
    private int totalFeedback;
    private List<String> comments;

    // constructors
    public FeedbackSummaryDto() {
        this.averageStars = 0.0;
        this.totalFeedback = 0;
        this.comments = new ArrayList<>();
    }

    public FeedbackSummaryDto(double averageStars, int totalFeedback, List<String> comments) {
        this.averageStars = averageStars;
        this.totalFeedback = totalFeedback;
        this.comments = comments;
    }

    // getters and setters
    public double getAverageStars() {
        return averageStars;
    }

    public void setAverageStars(double averageStars) {
        this.averageStars = averageStars;
    }

    public int getTotalFeedback() {
        return totalFeedback;
    }

    public void setTotalFeedback(int totalFeedback) {
        this.totalFeedback = totalFeedback;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }
}

