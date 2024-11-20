package tn.esprit.gnbapp.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gnbapp.entities.creditDemand;
import tn.esprit.gnbapp.repositories.creditDemandRepository;
import tn.esprit.gnbapp.repositories.creditRepository;
import tn.esprit.gnbapp.services.IcreditDemandServices;
import tn.esprit.gnbapp.entities.amortissement;
import tn.esprit.gnbapp.services.creditDemandServicesImpl;
import tn.esprit.gnbapp.entities.typeCd;
import org.springframework.http.MediaType;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import javax.annotation.Resource;
import javax.mail.*;



import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/creditDemand")
public class creditDemandRestController {
    @Autowired
    private creditDemandServicesImpl creditDemandServiceImpl;
    @Autowired
    private final IcreditDemandServices creditDemandServices;
    @Autowired
    private creditDemandRepository creditDemandRepository;
    @Autowired
    private creditRepository creditrepository;

    @PostMapping("/addcreditD/{idacc}")
    @ResponseBody
    public ResponseEntity<creditDemand> addCreditDemand(@RequestBody creditDemand creditDemand, @PathVariable("idacc") int id_acc) {
        creditDemand crd = creditDemandServiceImpl.addCreditDemand(creditDemand, id_acc);
        return new ResponseEntity<>(crd, HttpStatus.CREATED);
    }
    @PutMapping("/update")
    creditDemand updatecreditDemand (@RequestBody creditDemand creditDemand) {
        return creditDemandServices.addOrUpdatecreditDemand (creditDemand);
    }
    @GetMapping("/get/{id}")
    creditDemand getcreditDemand (@PathVariable("id") int id_cd) {
        return creditDemandServices.retrievecreditDemand(id_cd);
    }
    @GetMapping("/all")
    List<creditDemand> getAllcreditDemands() { return creditDemandServices.retrieveAllcreditDemands(); }

    @DeleteMapping("/delete/{id}")
    void deletecreditDemand (@PathVariable("id") int id_cd) {
        creditDemandServices.removecreditDemand (id_cd);
    }

    @PostMapping("/{id_cd}/assigntocredit/{id_credit}")
    public ResponseEntity<creditDemand> assignCreditDemandToCredit(@PathVariable int id_cd, @PathVariable int id_credit) {
        creditDemand creditDemand = creditDemandServiceImpl.assignCreditDemandToCredit(id_cd, id_credit);
        return ResponseEntity.ok().body(creditDemand);
    }

    @GetMapping("/retrieve-all-demandcredit/{idacc}")
    @ResponseBody
    public List<creditDemand> getDuesHistoryByCredit(@PathVariable("idacc") int id_acc) {
        List<creditDemand> listdemandecr = creditDemandServices.retrieveAllcreditDemandsbyacc(id_acc);
        return listdemandecr;
    }

    @GetMapping("/monthly-payment")
    public ResponseEntity<Double> calculateMonthlyPayment(@RequestParam int value_cd, @RequestParam int duration_cd, @RequestParam typeCd typeCd) {
        double monthlyPayment = creditDemandServices.calculateMonthlyPayment(value_cd, duration_cd, typeCd);
        return ResponseEntity.ok(monthlyPayment);
    }

    @GetMapping("/duration")
    public ResponseEntity<Integer> calculateDuration(@RequestParam int value_cd, @RequestParam double sum_cd, @RequestParam typeCd typeCd) {
        int duration = creditDemandServices.calculateDuration(value_cd, sum_cd, typeCd);
        return ResponseEntity.ok(duration);
    }
  /*  @PutMapping("/{id}")
    public ResponseEntity<?> updateCreditDemandStatus(@PathVariable("id") int id_cd,
                                                      @RequestBody creditDemand creditDemand) {
        Optional<creditDemand> existingCreditDemand = creditDemandRepository.findById(id_cd);

        if(existingCreditDemand.isPresent()) {
            creditDemand.setId_cd(id_cd);
            creditDemandRepository.save(creditDemand);

            creditDemandServiceImpl.sendCreditNotification(creditDemand);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCreditDemandStatus(@PathVariable("id") int id_cd,
                                                      @RequestBody creditDemand creditDemand) throws MessagingException {
        Optional<creditDemand> existingCreditDemand = creditDemandRepository.findById(id_cd);

        if(existingCreditDemand.isPresent()) {
            creditDemand.setId_cd(id_cd);
            creditDemandRepository.save(creditDemand);

            if (creditDemand.isStatus_cd()) {
                // Envoyer un email d'approbation à quelqu'un
                String recipientEmail = "ons.maghraoui@esprit.tn"; // Adresse email de la personne à qui envoyer le mail
                String subject = "Une nouvelle demande de crédit a été approuvée";
                String body = "Bonjour,\n\nUne nouvelle demande de crédit a été approuvée. Voici les détails:\n\nNom: " + creditDemand.getSum_cd() + "\nMontant: " + creditDemand.getValue_cd() + "\nDurée: " + creditDemand.getDuration_cd() + "\n\nMerci.";
                creditDemandServiceImpl.sendEmail(recipientEmail, subject, body);
            } else {
                // Envoyer un email de refus à la personne qui a fait la demande
                String recipientEmail = "ons.maghraoui@esprit.tn";
                String subject = "Votre demande de crédit a été refusée";
                String body = "Bonjour " + creditDemand.getSum_cd() + ",\n\nNous sommes désolés de vous informer que votre demande de crédit a été refusée. Pour plus d'informations, veuillez nous contacter.\n\n...";
                creditDemandServiceImpl.sendEmail("ons.maghraoui@esprit.tn", subject, body);


            }

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    private amortissement amortissement;
    @GetMapping("/{id}/amortization")
    public List<amortissement> createAmortizationTable(@PathVariable("id") int id_cd) {
        creditDemand creditDemand = creditDemandRepository.findById(id_cd).orElse(null);
        if (creditDemand == null) {
            throw new RuntimeException("Credit demand not found");
        }

        double montantR = creditDemand.getSum_cd();
        double interest = creditDemand.getValue_cd();
        double mensualite = calculateMensualite(montantR, interest, creditDemand.getDuration_cd());
        double capitalRestant = montantR;
        double capitalRembourse = 0;
        double amortissement = 0;

        List<amortissement> amortizationTable = new ArrayList<>();
        for (int i = 1; i <= creditDemand.getDuration_cd(); i++) {
            interest = calculateInterest(capitalRestant, creditDemand.getValue_cd());
            amortissement = mensualite - interest;
            capitalRembourse = capitalRembourse + amortissement;
            capitalRestant = montantR - capitalRembourse;

            amortissement a = new amortissement();
            a.setDate(new Date());
            a.setRemainingBalance(capitalRestant);
            a.setInterest(interest);
            a.setPrincipal(amortissement);
            a.setPayment(mensualite);
            a.setCredit(creditDemand.getCredit());
            amortizationTable.add(a);
        }

        creditDemand.getCredit().setAmortizationTable(amortizationTable);
        creditrepository.save(creditDemand.getCredit());

        return amortizationTable;
    }

    private double calculateMensualite(double montantR, double interest, int duration) {
        double tauxMensuel = interest / 12;
        double nbEcheances = duration * 12;
        return montantR * tauxMensuel / (1 - Math.pow(1 + tauxMensuel, -nbEcheances));
    }

    private double calculateInterest(double capitalRestant, double interestRate) {
        return capitalRestant * interestRate / 12;
    }


    @GetMapping("/creditDemand/{id}/amortizationTable/excel")
    public ResponseEntity<Resource> generateAmortizationTableExcel(@PathVariable("id") int id_cd) throws IOException {
        List<amortissement> amortizationTable = creditDemandServices.createAmortizationTable(id_cd);
        creditDemandServiceImpl.createExcelFile(amortizationTable);

        Path path = Paths.get("./amortization.xlsx").toAbsolutePath().normalize();
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=amortization.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(Files.size(path))
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body((Resource) resource);
    }





}


