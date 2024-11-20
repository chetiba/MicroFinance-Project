package tn.esprit.gnbapp.services;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.gnbapp.entities.question;
import tn.esprit.gnbapp.entities.response;
import tn.esprit.gnbapp.repositories.IquestionRepository;
import tn.esprit.gnbapp.repositories.IresponseRepository;

import java.util.List;
import java.util.Set;

import static tn.esprit.gnbapp.entities.response.*;

@Service
@AllArgsConstructor
public class ResponsesServicesImpl implements IResponseServices {
    private final IresponseRepository responseRepository;
    private final IquestionRepository questionRepository;

    @Override
    public List<response> retrieveAllResponses() {
        return responseRepository.findAll();
    }

    @Override
    public response addOrUpdateResponse(response r) {
        return responseRepository.save(r);
    }

    @Override
    public response retrieveResponse(Integer id_response) {
        return responseRepository.findById(id_response).orElse(null);
    }
    @Override
    public void removeResponse(Integer id_response) {
        responseRepository.deleteById(id_response);
    }
    @Override
    public response assignResponseToQuestion(Integer id_response, Integer id_question) {
        response r = responseRepository.findById(id_response).orElse(null);
        question q = questionRepository.findById(id_question).orElse(null);
        r.setQuestion(q);
        return responseRepository.save(r);
    }
}
