package tn.esprit.gnbapp.controllers;

import com.itextpdf.text.DocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gnbapp.entities.investesment;
import tn.esprit.gnbapp.services.InvestesmentService;
import tn.esprit.gnbapp.repositories.InvestesmentRepository;
import tn.esprit.gnbapp.services.IInvestesmentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.text.Document;


@RestController
@RequiredArgsConstructor
@RequestMapping("/Investesment")
public class InvestesmentController {
    @Autowired
    IInvestesmentService  investesmentService;



    @GetMapping("/retrieve-all-investesments")
    @ResponseBody
    public List<investesment> getInvestesment() {
        List<investesment> listInvestesment = investesmentService.retrieveAllInvestesments();
        return listInvestesment;
    }



    @GetMapping("/retrieve-investesment/{investesment-id}")
    @ResponseBody
    public investesment retrieveInvestesment(@PathVariable("investesment-id") Integer investesmentId) {
        return investesmentService.retrieveInvestesment(investesmentId);
    }



    @GetMapping("/retrieve-investesments-by-fund/{Fund-id}")
    @ResponseBody
    public List<investesment> getInvestesmentbyFund(@PathVariable("Fund-id") Integer idFund) {
        List<investesment> listInvestesment = investesmentService.retrieveInvestesmentbyFund(idFund);
        return listInvestesment;
    }


    @PostMapping("/add_investesment/{Fund-id}")
    @ResponseBody
    public investesment addInvestesment(@RequestBody investesment i,@PathVariable("Fund-id") Integer idFund)
    {
        System.out.println(idFund);
        investesment investesment = investesmentService.addInvestesment(i,idFund);
        return investesment;
    }


    @PutMapping("/modify-investesment")
    @ResponseBody
    public investesment modifyInvestesment(@RequestBody investesment i) {
        return investesmentService.updateInvestesment(i);}


    @GetMapping("/export")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
        String currentDateTime = dateFormater.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "Attachement;filename=inves_"+ currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<investesment> listInvestesment = investesmentService.retrieveAllInvestesments();
        investPDFExporter exporter = new investPDFExporter(listInvestesment);
        exporter.export(response);
    }


    @GetMapping("/CalculateAmoutOfInves/{Investesment-id}")
    @ResponseBody
    public void CalculateAmoutOfInves(@PathVariable("Investesment-id") Integer idInvestesment) {
        investesmentService.CalculateAmoutOfInves(idInvestesment);
    }


    @GetMapping("/finalAmount")
    public void finalAmount() {
        investesmentService.finalAmount();
    }

    @GetMapping("/CalculateRateOfInves/{Investesment-id}")
    @ResponseBody
    public float CalculateRateOfInves(@PathVariable("Investesment-id") Integer idInvestesment) {
        return investesmentService.CalculateRateOfInves(idInvestesment);
    }

    @GetMapping("/Rate/{amount}")
    @ResponseBody
    public double Rate(@PathVariable("amount") float AmountInvestestesment) {
        return investesmentService.Rate(AmountInvestestesment);
    }

}
