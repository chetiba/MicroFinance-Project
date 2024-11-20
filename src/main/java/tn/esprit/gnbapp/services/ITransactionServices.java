package tn.esprit.gnbapp.services;
import tn.esprit.gnbapp.entities.transaction;
import tn.esprit.gnbapp.entities.account;

import javax.mail.MessagingException;
import java.util.List;

public interface ITransactionServices {
    List<transaction> retrieveAllTransaction();
    int  addTransaction(transaction t )throws MessagingException ;

    transaction addOrUpdateTransaction(transaction t);
    transaction  retrieveTransaction (Integer id_trans);
    void removeTransaction (Integer id_trans);

    account assignAccountToTransaction(Integer id_acc, Integer id_trans);
    //public String  approveTransaction(transaction t )throws MessagingException;
    //Transaction updateTransaction(Transaction u);


    //public List<transaction> retrieveAllTransactionsEmisesByRib(Integer id_acc) ;
    //public List<transaction> retrieveTransactions(Integer id_acc) ;
    public List<transaction> AllTransactionsEmisesByRib( long rib) ;
    public String  approveTransaction(transaction t ) throws MessagingException;
    public String  approveTransactionAng(transaction t, Long code ) throws MessagingException;




}
