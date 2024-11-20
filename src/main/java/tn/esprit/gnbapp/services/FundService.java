package tn.esprit.gnbapp.services;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.gnbapp.entities.fund;
import tn.esprit.gnbapp.repositories.FundRepository;

import java.util.List;
@Service
@RequiredArgsConstructor
public class FundService implements IFundService {
    @Autowired
    FundRepository fundrepository;

    @Override
    public List<fund> retrieveAllFunds() {
        return (List<fund>) fundrepository.findAll();
    }

    @Override
    public fund addFund(fund f) {
        return fundrepository.save(f);
    }

    @Override
    public void deleteFund(Integer idFund) {
        fundrepository.deleteById(idFund);
    }

    @Override
    public fund updateFund(fund fun) {
        fundrepository.save(fun);
        return fun;
    }

    @Override
    public fund retrieveFund(Integer idFund) {
        return fundrepository.findById(idFund).orElse(null);
    }
}