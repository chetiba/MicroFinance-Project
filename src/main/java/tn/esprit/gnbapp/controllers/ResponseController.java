package tn.esprit.gnbapp.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gnbapp.entities.response;
import tn.esprit.gnbapp.services.IResponseServices;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/response")
public class ResponseController {
    private final IResponseServices responseServices;


    @PostMapping("/add")
    response addResponse(@RequestBody response response ){
        return responseServices.addOrUpdateResponse(response);
    }
    @PutMapping("/update")
    response updateResponse(@RequestBody response response ){
        return responseServices.addOrUpdateResponse(response);
    }
    @GetMapping("/get/{id}")
    response getResponse(@PathVariable("id") Integer id_response){
        return responseServices.retrieveResponse(id_response);
    }
    @GetMapping("/all")
    List<response> getAllResponses(){
        return responseServices.retrieveAllResponses();
    }
    @DeleteMapping("/delete/{id}")
    void deleteResponse(@PathVariable("id") Integer id_response){
        responseServices.removeResponse(id_response);
    }

    @PutMapping("/assignResponseToQuestion/{id_response}/{id_question}")
    public response assignResponseToQuestion(@PathVariable("id_response") Integer id_response,
                                             @PathVariable("id_question") Integer id_question){
        return responseServices.assignResponseToQuestion(id_response,id_question);
    }
}
