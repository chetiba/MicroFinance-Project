package tn.esprit.gnbapp.services;

import com.google.zxing.WriterException;
import tn.esprit.gnbapp.entities.consultation;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface IConsultationServices {
    List<consultation> retrieveAllConsultations();

    consultation addOrUpdateConsultation(consultation c);

    consultation retrieveConsultation (Integer id_consult);

    void removeConsultation (Integer id_consult);
    void assignConsultToUser(Integer id_cons,Integer id_user);
    void sendmail(String toEmail,String subject,String htmlBody) throws MessagingException;

    int generateQRCode(consultation c) throws WriterException, IOException;



}
