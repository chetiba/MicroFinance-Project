package tn.esprit.gnbapp.services;


import tn.esprit.gnbapp.entities.consultation;
import tn.esprit.gnbapp.entities.question;

import java.util.List;

public interface IQuestionServices {
    List<question> retrieveAllQuestions();

    question addOrUpdateQuestion(question q);

    question retrieveQuestion (Integer id_question);

    void removeQuestion (Integer id_question);

    question assignQuestionToPost(Integer id_question, Integer id_post);

}
