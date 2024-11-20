package tn.esprit.gnbapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tn.esprit.gnbapp.entities.user;

import tn.esprit.gnbapp.repositories.accountRepository;
import tn.esprit.gnbapp.repositories.userRepository;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class userServicesImpl implements IuserServices {
    @Autowired
    private final userRepository userRepository ;
    @Autowired
    IuserServices iuser ;
    @Autowired
    private JavaMailSender mailSender;

    private final tn.esprit.gnbapp.repositories.accountRepository accountRepository;

    @Override
    public List<user> retrieveAllusers() {
        return userRepository.findAll();
    }

    @Override
    public user addOrUpdateuser(user u) {
        return userRepository.save(u);
    }

    @Override
    public user retrieveuser(int id) {
        return userRepository.findById(id);
    }

    @Override
    public void removeuser(int id_user) {
        userRepository.deleteById(id_user);

    }
    @Override
    public void changePassword(user u, String mdp) {u.setPwd_user(mdp);userRepository.save(u);}
    @Override
    public boolean veriyUserPassword(user u, String password) {
        if (u.getPwd_user().matches(password)==true)return true;
        else return false;
}
    @Override
    public int countUsers() {
        Long nbr= userRepository.count();
        return nbr.intValue();

    }
    @Override
    public void sendmail(String toEmail, String subject, String htmlBody) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHealper = new MimeMessageHelper(mimeMessage,true);
        mimeMessageHealper.setFrom("sara.wahada@esprit.tn");
        mimeMessageHealper.setTo(toEmail);
        mimeMessageHealper.setText(htmlBody,true);
        mimeMessageHealper.setSubject(subject);
        mailSender.send(mimeMessage);
        System.out.println("Mail sent successfully!");
    }

}
