package tn.esprit.gnbapp.controllers;

import com.google.zxing.WriterException;
import lombok.RequiredArgsConstructor;

import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gnbapp.entities.consultation;
import tn.esprit.gnbapp.repositories.IconsultationRepository;
import tn.esprit.gnbapp.services.IConsultationServices;


import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/consultation")
public class ConsultationRestController {
    private final IConsultationServices consultationServices;
    private final IconsultationRepository consultationRepository;
    String link = "https://meet.google.com/lookup/consultation";


    @PostMapping("/add")
    void addConsultation(@RequestBody consultation consultation ) throws IOException, WriterException {
        consultationServices.addOrUpdateConsultation(consultation);
        consultationServices.generateQRCode(consultation);
    }
    @PutMapping("/update")
    void updateConsultation(@RequestBody consultation consultation ) throws IOException, WriterException {
        consultationServices.addOrUpdateConsultation(consultation);
        consultationServices.generateQRCode(consultation);

    }
    @GetMapping("/get/{id}")
    consultation getConsultation(@PathVariable("id") Integer id_cons){
        return consultationServices.retrieveConsultation(id_cons);
    }
    @GetMapping("/all")
    List<consultation> getAllConsultations(){
        return consultationServices.retrieveAllConsultations();
    }
    @DeleteMapping("/delete/{id}")
    void deleteConsultation(@PathVariable("id") Integer id_cons){
        consultationServices.removeConsultation(id_cons);
    }
    @PutMapping("/assignConsultToUser/{id_cons}/{id_user}")
    void assignConsultToUser(@RequestBody consultation c ,@PathVariable("id_cons") Integer id_cons,
                                 @PathVariable("id_user") Integer id_user) throws MessagingException, IOException, WriterException {
        consultationServices.assignConsultToUser(id_cons, id_user);
        File file = ResourceUtils.getFile("/Users/sarawahada/app/gnbApp/src/main/java/tn/esprit/gnbapp/utils/AssignConsToUser.html");
        System.out.println("File Found : " + file.exists());
        String content = new String(Files.readAllBytes(file.toPath()));
        content = content.replace("${date}", consultationRepository.findDateConsById(id_cons).toString());
        content = content.replace("${lastname}", consultationRepository.findLastName(id_cons));
        content = content.replace("${name}", consultationRepository.findFirstName(id_cons));
        content = content.replace("${consultantDesc}", consultationRepository.findJob(id_cons));
        content = content.replace("${contact}", consultationRepository.Contact(id_cons));
        consultationServices.sendmail("sara.wahada@esprit.tn", "GoliathBank", content);
        consultationServices.generateQRCode(c);
    }
    @GetMapping("/get/{code}/{id_cons}")
    String VerifyCode(@PathVariable("code") Integer code,@PathVariable("id_cons")Integer id_cons){
     if (consultationRepository.Verify(id_cons) != code){
        return "verify code";
     }
     else System.out.println(link);
     return link;
}
}
