package tn.esprit.gnbapp.services;

import tn.esprit.gnbapp.entities.user;
import tn.esprit.gnbapp.entities.user;

import javax.mail.MessagingException;
import java.util.List;

public interface IuserServices {
    List<user> retrieveAllusers() ;
    user addOrUpdateuser(user u);
    user retrieveuser (int id_user);
    void removeuser ( int id_user);
    void changePassword(user user, String mdp);
    boolean veriyUserPassword(user user, String password);
    int countUsers();
    public void sendmail(String toEmail, String subject, String htmlBody) throws MessagingException, MessagingException;

    }
