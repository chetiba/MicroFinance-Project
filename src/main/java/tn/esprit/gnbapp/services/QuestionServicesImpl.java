package tn.esprit.gnbapp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.gnbapp.entities.post;
import tn.esprit.gnbapp.entities.question;
import tn.esprit.gnbapp.repositories.IpostRepository;
import tn.esprit.gnbapp.repositories.IquestionRepository;
import tn.esprit.gnbapp.repositories.IuserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionServicesImpl implements IQuestionServices {
    private final IquestionRepository questionRepository;

    private final IpostRepository postRepository;
    private final IuserRepository iuserRepository;

    @Override
    public List<question> retrieveAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public question addOrUpdateQuestion(question q) {
        return questionRepository.save(q);
    }

    @Override
    public question retrieveQuestion(Integer id_question) {
        return questionRepository.findById(id_question).orElse(null);
    }
    @Override
    public void removeQuestion(Integer id_question) {
        questionRepository.deleteById(id_question);

    }
    @Override
    public question assignQuestionToPost(Integer id_question, Integer id_post) {
        question q = questionRepository.findById(id_question).orElse(null);
        post p = postRepository.findById(id_post).orElse(null);
        q.setPost(p);
        return questionRepository.save(q);
    }
}
