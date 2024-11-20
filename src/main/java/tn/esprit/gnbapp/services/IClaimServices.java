package tn.esprit.gnbapp.services;

import tn.esprit.gnbapp.entities.claim;
import tn.esprit.gnbapp.entities.consultation;

import java.io.IOException;
import java.util.List;

public interface IClaimServices {
    claim assignClaimToCredit(Integer id_claim, Integer id_credit);
    public List<claim> getallclaims();
    claim addOrUpdateClaim(claim c);
    void removeClaim (Integer id_claim);
    public claim Addclaim(claim claim) throws IOException;



}
