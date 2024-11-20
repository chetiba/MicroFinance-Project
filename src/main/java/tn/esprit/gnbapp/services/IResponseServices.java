package tn.esprit.gnbapp.services;

import tn.esprit.gnbapp.entities.response;

import java.util.List;

public interface IResponseServices {
    List<response> retrieveAllResponses();

    response addOrUpdateResponse(response r);

    response retrieveResponse (Integer id_response);

    void removeResponse (Integer id_response);
    response assignResponseToQuestion(Integer id_response, Integer id_question);
    }
