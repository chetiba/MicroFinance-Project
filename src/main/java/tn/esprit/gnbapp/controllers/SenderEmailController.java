package tn.esprit.gnbapp.controllers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.gnbapp.services.EmailSenderService;

import javax.mail.SendFailedException;

@RestController
@RequiredArgsConstructor


public class SenderEmailController {


    @Autowired
    private final EmailSenderService service;

    @GetMapping("/sendemail")
    public void sendmail()throws SendFailedException {
        service.sendSimpleEmail("abir.seddiki@esprit.tn",
                "hello  ...",
                "test"
        );
    }









}
