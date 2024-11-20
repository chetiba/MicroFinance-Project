package tn.esprit.gnbapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.gnbapp.Exceptions.CreditNotFoundException;
import tn.esprit.gnbapp.entities.amortissement;
import tn.esprit.gnbapp.entities.credit;

import tn.esprit.gnbapp.entities.creditDemand;
import tn.esprit.gnbapp.repositories.creditDemandRepository;
import tn.esprit.gnbapp.repositories.creditRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class creditServicesImpl implements IcreditServices {
    private final creditRepository creditRepository ;
    private final creditDemandRepository creditDemandRepository;

    @Override
    public List<credit> retrieveAllcredits() {
        return creditRepository.findAll();
    }

    @Override
    public credit addOrUpdatecredit(credit cr) {
        return creditRepository.save(cr);
    }

    @Override
    public credit retrievecredit(int id_credit) {
        return creditRepository.findById(id_credit).orElse(null);
    }

    @Override
    public void removecredit(int id_credit) {
        creditRepository.deleteById(id_credit);

    }


    @Override
    public credit addCredit(credit credit, int id_crd) {
        creditDemand creditDemand = creditDemandRepository.findById(id_crd).orElseThrow(() -> new IllegalArgumentException("Invalid credit demand Id:" + id_crd));
        credit.setCreditDemand(creditDemand);
        return creditRepository.save(credit);
    }



    @Override
    public double calculateRemainingAmount(int creditId, double amountPaid, double interestRate) throws CreditNotFoundException {
        Optional<credit> optionalCredit = creditRepository.findById(creditId);
        if (!optionalCredit.isPresent()) {
            throw new CreditNotFoundException("Credit not found with ID: " + creditId);
        }
        credit credit = optionalCredit.get();
        double remainingAmount = credit.getAmount_credit() - amountPaid;
        if (remainingAmount < 0) {
            throw new IllegalArgumentException("Amount paid cannot exceed the total credit amount.");
        }
        double interest = remainingAmount * interestRate / 100;
        return remainingAmount + interest;
    }


}
