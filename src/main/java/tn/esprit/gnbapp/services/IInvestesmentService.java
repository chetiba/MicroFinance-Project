package tn.esprit.gnbapp.services;
import tn.esprit.gnbapp.entities.investesment;

import java.util.List;

public interface IInvestesmentService {
    List<investesment> retrieveAllInvestesments();

    investesment addInvestesment(investesment i , Integer idFund);

    investesment updateInvestesment(investesment i);

    investesment retrieveInvestesment(Integer IdInvestesment);

    void CalculateAmoutOfInves(Integer idInvestesment);

    float CalculateRateOfInves(Integer idInvestissement);

    void finalAmount();

    List<investesment> retrieveInvestesmentbyFund(Integer idFund);

    double Rate(float amountInvestestesment);
}
