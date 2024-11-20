package tn.esprit.gnbapp.controllers;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gnbapp.entities.fund;
import tn.esprit.gnbapp.services.FundService;
import tn.esprit.gnbapp.repositories.FundRepository;
import tn.esprit.gnbapp.services.IFundService;

import java.util.List;
@RestController
@RequestMapping("/Fund")
public class FundController {
    @Autowired
    FundService fundService;

    @GetMapping("/retrieve-all-funds")
    @ResponseBody
    public List<fund> getFund() {
        List<fund> listFund = fundService.retrieveAllFunds();
        return listFund;
    }

    @GetMapping("/retrieve-fund/{fund-id}")
    @ResponseBody
    public fund retrieveFund(@PathVariable("fund-id") Integer fundId) {
        return fundService.retrieveFund(fundId);
    }

    @PostMapping("/add-fund")
    @ResponseBody
    public fund addFund(@RequestBody fund f)
    {
        fund fund = fundService.addFund(f);
        return fund;
    }


    @DeleteMapping("/remove-fund/{fund-id}")
    @ResponseBody
    public void removeFund(@PathVariable("fund-id") Integer fundId) {
        fundService.deleteFund(fundId);
    }

    // http://localhost:8083/BKFIN/Fund/modify-fund
    @PutMapping("/modify-fund")
    @ResponseBody
    public fund modifyFund(@RequestBody fund fund) {
        return fundService.updateFund(fund);
    }

}
