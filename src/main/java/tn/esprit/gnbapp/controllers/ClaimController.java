package tn.esprit.gnbapp.controllers;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gnbapp.entities.claim;
import tn.esprit.gnbapp.entities.consultation;
import tn.esprit.gnbapp.services.ClaimServicesImpl;
import tn.esprit.gnbapp.repositories.IclaimRepository;
import tn.esprit.gnbapp.services.IClaimServices;
import java.io.IOException;


import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/claim")
public class ClaimController {

    private final IClaimServices claimServices;

    @GetMapping("/getallclaims/")
    List<claim> getallclaims(){
        return claimServices.getallclaims();

    }
    @PostMapping("/add")
    claim addClaim(@RequestBody claim claim ){
        return claimServices.addOrUpdateClaim(claim);
    }
    @PutMapping("/assignClaimToCredit/{id_claim}/{id_credit}")
    public claim assignClaimToCredit(@PathVariable("id_claim") Integer id_claim,
                                         @PathVariable("id_credit") Integer id_credit){
        return claimServices.assignClaimToCredit(id_claim,id_credit);
    }

    @DeleteMapping("/delete/{id}")
    void deleteClaim(@PathVariable("id") Integer id_claim){
        claimServices.removeClaim(id_claim);
    }


    @PostMapping("/claims")
    public ResponseEntity<claim> Addclaim(@RequestBody claim claim) {
        try {
            claim newclaim = claimServices.Addclaim(claim);
            return new ResponseEntity<>(newclaim, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
