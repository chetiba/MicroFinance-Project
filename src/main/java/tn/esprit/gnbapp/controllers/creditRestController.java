package tn.esprit.gnbapp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gnbapp.entities.credit;
import tn.esprit.gnbapp.services.IcreditServices;
import tn.esprit.gnbapp.services.creditServicesImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/credit")
public class creditRestController {

    private final IcreditServices creditServices;
private final creditServicesImpl creditServicesImpl;

    @PostMapping("/addcredit/{idcd}")
    @ResponseBody
    public ResponseEntity<credit> addCredit(@RequestBody credit credit, @PathVariable("idcd") int id_cd) {
        credit cr = creditServices.addCredit(credit, id_cd);
        return new ResponseEntity<>(cr, HttpStatus.CREATED);
    }


    @PutMapping("/update")
    credit updatecredit (@RequestBody credit credit) {
        return creditServices.addOrUpdatecredit (credit);
    }
    @GetMapping("/get/{id}")
    credit getcredit (@PathVariable("id") int id_credit) {
        return creditServices.retrievecredit(id_credit);
    }
    @GetMapping("/all")
    List<credit> getAllcredits() { return creditServices.retrieveAllcredits(); }
    @DeleteMapping("/delete/{id}")
    void deletecredit (@PathVariable("id") int id_credit) {
        creditServices.removecredit (id_credit);
    }

    @GetMapping("/{creditId}/remaining-amount")
    public ResponseEntity<Double> calculateRemainingAmount(@PathVariable int creditId,
                                                           @RequestParam Double amountPaid,
                                                           @RequestParam Double interestRate) {
        double remainingAmount = creditServices.calculateRemainingAmount(creditId, amountPaid, interestRate);
        return ResponseEntity.ok(remainingAmount);
    }




}
