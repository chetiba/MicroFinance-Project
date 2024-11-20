package tn.esprit.gnbapp.services;

import com.itextpdf.text.DocumentException;
import tn.esprit.gnbapp.entities.account;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface IaccountServices {
    List<account> retrieveAllAccounts() ;
    account addOrUpdateaccount(account a);
    account retrieveaccount (int id_acc);
    void removeaccount ( int id_acc);
    public String GenerateRib() ;
    public void GetInterestAmount();
    public void generatePdf(List<account> accounts, String fileName) throws DocumentException, IOException;
    public int countCurrentAccounts();
    public int countSavingsAccounts();
    public int countAccounts();
}
