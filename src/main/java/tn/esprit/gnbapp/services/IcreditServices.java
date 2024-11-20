package tn.esprit.gnbapp.services;

import tn.esprit.gnbapp.entities.credit;
import tn.esprit.gnbapp.entities.credit;

import java.util.List;

public interface IcreditServices {
    List<credit> retrieveAllcredits() ;
    credit addOrUpdatecredit(credit cr);
    credit retrievecredit (int id_credit);
    void removecredit ( int id_credit);


    credit addCredit(credit credit, int id_cd);
    double calculateRemainingAmount(int creditId, double amountPaid, double interestRate);


}
