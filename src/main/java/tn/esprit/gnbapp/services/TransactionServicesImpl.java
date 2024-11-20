package tn.esprit.gnbapp.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import tn.esprit.gnbapp.entities.account;
import tn.esprit.gnbapp.repositories.IAccountRepository;
import tn.esprit.gnbapp.repositories.ITransactionRepository;
import tn.esprit.gnbapp.entities.transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TransactionServicesImpl implements ITransactionServices {
    private final ITransactionRepository TransactionRepository;
    private final IAccountRepository AccountRepository;
    @Autowired
    public JavaMailSender emailSender;
    int code=0;

    @Override
    public List<transaction> retrieveAllTransaction() {
        return TransactionRepository.findAll();
    }

    @Override
    public transaction addOrUpdateTransaction(transaction t) {
        return TransactionRepository.save(t);
    }

    @Override
    public transaction retrieveTransaction(Integer id_trans) {
        return TransactionRepository.findById(id_trans).orElse(null);
    }

    @Override
    public void removeTransaction(Integer id_trans) {
         TransactionRepository.deleteById(id_trans);

    }

    @Override
    public account assignAccountToTransaction(Integer id_acc, Integer id_trans) {
        account a = AccountRepository.findById(id_acc).orElse(null);
        return AccountRepository.save(a);
    }
    @Override
    public List<transaction> AllTransactionsEmisesByRib(long rib) {
        List<transaction> ltr=TransactionRepository.getTransactionByRibAccount(rib);
        for (transaction tr: ltr) {
        System.out.println("Transactions Emises :" + ltr);
        }
        return (List<transaction>) TransactionRepository.getTransactionEmiseByRibAccount(rib);
    }

    public int sendAttachmentEmail(String ReciverEmail) throws MessagingException {

        MimeMessage message = emailSender.createMimeMessage();

        boolean multipart = true;

        MimeMessageHelper helper = new MimeMessageHelper(message, multipart, "utf-8");
        int max = 999999 ;
        int min = 9999 ;
        SecureRandom secureRandom = new SecureRandom();
        int randomWithSecureRandomWithinARange = secureRandom.nextInt(max - min) + min;

        String htmlMsg = "<h3>Validate this Transaction by Using this number  </h3>"
                +"<img src='http://www.apache.org/images/asf_logo_wide.gif'>"
                + randomWithSecureRandomWithinARange;

        message.setContent(htmlMsg, "text/html");
        helper.setTo(ReciverEmail);
        helper.setSubject("GNB Transaction endorsment ");
        this.emailSender.send(message);

        return randomWithSecureRandomWithinARange ;
    }
    @Transactional
    public int addTransaction(transaction t ) throws MessagingException
    {
        //Il n'as pas ajouter la trasaction au comptes qu'il l'a effectué !
        account acc_emet =AccountRepository.findByRib(t.getRibEmetteur()).orElse(null) ;
        account acc_dest =AccountRepository.findByRib(t.getRibRecipient()).orElse(null);
       /* if (acc_emet != null) {
            System.out.println("not null :" );
        } else {
            // the account was not found, so handle the situation accordingly
            System.out.println("null :" );
        }
        if (acc_dest != null) {
            System.out.println("not null :" );
        } else {
            // the account was not found, so handle the situation accordingly
            System.out.println("null :" );
        }*/

        // Methode te3 GetSoldbesh
        // ajouter motif te3 transaction
        System.out.println("acc_emet is null :" +acc_emet);
        System.out.println("user is null :"+ acc_emet.getUser() );
        int code_tr = sendAttachmentEmail(acc_emet.getUser().getEmail()) ;
        this.code=code_tr;
        if  (t.getAmount() < acc_emet.getBalance_acc())
        {
            acc_emet.setBalance_acc(acc_emet.getBalance_acc()-t.getAmount());
            t.setAccount(acc_emet);

            // SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            //   Date date = new Date();
            //  formatter.format(date);

            //t.setDate_trans(new Date());
            acc_dest.setBalance_acc(acc_dest.getBalance_acc()+t.getAmount());

        }
        else
        {
            code_tr=0;
        }

        return code_tr ;
    }
    @Override
    public String  approveTransaction(transaction t ) throws MessagingException {


        if ((addTransaction(t) == code)) {
            TransactionRepository.save(t);
            return "transaction approuvée ";
        } else {
            return "Transaction non approuvée";
        }

    }

    @Override
    public String  approveTransactionAng(transaction t, Long code ) throws MessagingException {

        if(this.code==code)
        {
            TransactionRepository.save(t);
            return "transaction approuvee " ;
        }
        else
        {
            return "Transaction non approuvée" ;
        }
    }

    }
