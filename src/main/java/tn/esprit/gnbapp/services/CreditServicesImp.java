package tn.esprit.gnbapp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.gnbapp.entities.consultation;
import tn.esprit.gnbapp.entities.credit;
import tn.esprit.gnbapp.repositories.IcreditRepository;
import tn.esprit.gnbapp.repositories.IuserRepository;

@RequiredArgsConstructor
@Service
public class CreditServicesImp implements IcreditServices{
    private final IcreditRepository creditRepository;
    @Override
    public credit addOrUpdateCredit(credit cr) {
        return creditRepository.save(cr);
    }
}
