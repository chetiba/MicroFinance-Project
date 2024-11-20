package tn.esprit.gnbapp.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gnbapp.entities.question;
import tn.esprit.gnbapp.services.IQuestionServices;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {
    private final IQuestionServices questionServices  ;


    @PostMapping("/add")
    question addQuestion(@RequestBody question question ){
        return questionServices.addOrUpdateQuestion(question);
    }
    @PutMapping("/update")
    question updateQuestiont(@RequestBody question question ){
        return questionServices.addOrUpdateQuestion(question);
    }
    @GetMapping("/get/{id}")
    question getQuestion(@PathVariable("id") Integer id_question){
        return questionServices.retrieveQuestion(id_question);
    }
    @GetMapping("/all")
    List<question> getAllQuestions(){
        return questionServices.retrieveAllQuestions();
    }
    @DeleteMapping("/delete/{id}")
    void deleteQuestion(@PathVariable("id") Integer id_question){
        questionServices.removeQuestion(id_question);
    }

    @PutMapping("/assignQuestionToPost/{id_question}/{id_post}")
    public question assignQuestionToPost(@PathVariable("id_question") Integer id_question,
                                                 @PathVariable("id_post") Integer id_post){
        return questionServices.assignQuestionToPost(id_question,id_post);
    }
}
