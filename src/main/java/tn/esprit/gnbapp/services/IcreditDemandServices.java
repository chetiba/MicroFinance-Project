package tn.esprit.gnbapp.services;

import tn.esprit.gnbapp.entities.amortissement;
import tn.esprit.gnbapp.entities.creditDemand;
import tn.esprit.gnbapp.entities.typeCd;

import java.util.List;

public interface IcreditDemandServices {




    List<creditDemand> retrieveAllcreditDemands() ;
    creditDemand retrievecreditDemand (int id_cd);
    void removecreditDemand ( int id_cd);

    creditDemand assignCreditDemandToCredit(int id_cd, int id_credit);



    creditDemand addOrUpdatecreditDemand(creditDemand creditDemand);

    List<creditDemand> retrieveAllcreditDemandsbyacc(int id_acc);





    double calculateMonthlyPayment(int value_cd, int duration_cd, typeCd typeCd);

    int calculateDuration(int value_cd, double sum_cd, typeCd typeCd);


    List<amortissement> createAmortizationTable(int id_cd);
}
