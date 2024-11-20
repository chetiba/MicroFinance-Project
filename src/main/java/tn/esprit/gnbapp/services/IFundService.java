package tn.esprit.gnbapp.services;
import tn.esprit.gnbapp.entities.fund;

import java.util.List;

public interface IFundService {
    List<fund> retrieveAllFunds();

    fund addFund(fund f);

    void deleteFund(Integer idFund);

    fund updateFund(fund fun);

    fund retrieveFund(Integer idFund);
}
