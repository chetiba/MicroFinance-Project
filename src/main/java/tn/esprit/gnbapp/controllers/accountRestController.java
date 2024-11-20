package tn.esprit.gnbapp.controllers;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gnbapp.entities.account;
import tn.esprit.gnbapp.services.IaccountServices;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class accountRestController {

    private final IaccountServices accountServices;
    private final tn.esprit.gnbapp.repositories.accountRepository accountRepository;

    @PostMapping("/add")
    account addaccount (@RequestBody account account) { return accountServices.addOrUpdateaccount (account);
    }
    @PutMapping("/update")
    account updateaccount (@RequestBody account account) {
        return accountServices.addOrUpdateaccount (account);
    }
    @GetMapping("/get/{id}")
    account getaccount (@PathVariable("id") int id_acc) {
        return accountServices.retrieveaccount(id_acc);
    }
    @GetMapping("/all")
    List<account> getAllaccounts() { return accountServices.retrieveAllAccounts(); }
    @DeleteMapping("/delete/{id}")
    void deleteaccount (@PathVariable("id") int id_acc) {
        accountServices.removeaccount(id_acc);
    }
    @GetMapping("/generate")
    @ResponseBody
    public String generateRib ()
    {
        return accountServices.GenerateRib() ;
    }


    @PostMapping("/interet")
    @ResponseBody
    public ResponseEntity<String> getInterestAmount() {
        accountServices.GetInterestAmount();
        return ResponseEntity.status(HttpStatus.OK).body("Interest calculation complete");
    }
    @GetMapping("/accounts/{cin}/pdf")
    public ResponseEntity<?> generatePdf(@PathVariable("cin")long cin) {
        List<account> accounts = accountRepository.findByUser_Cin(cin);
        String fileName = "accounts_" + cin + ".pdf";

        try {
            accountServices.generatePdf(accounts, fileName);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileName).build());
            InputStreamResource isr = new InputStreamResource(new FileInputStream(fileName));
            return new ResponseEntity<>(isr, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/accountStats")
    public ResponseEntity<String> getAccountStats() throws UnsupportedEncodingException {
        int totalAccounts = accountServices.countAccounts();
        int savingsAccounts = accountServices.countSavingsAccounts();
        int currentAccounts = accountServices.countCurrentAccounts();
        double savingsPercentage = ((double) savingsAccounts / totalAccounts) * 100;
        double currentPercentage = ((double) currentAccounts / totalAccounts) * 100;

        // Create the data for the chart
        String chartUrl = "https://quickchart.io/chart?c=" + URLEncoder.encode(
                "{ \"type\": \"pie\", \"data\": { \"labels\": [\"Savings Accounts\", \"Current Accounts\"], \"datasets\": [{ \"backgroundColor\": [\"#36A2EB\", \"#FFCE56\"], \"data\": [" + savingsPercentage + ", " + currentPercentage + "] }] }, \"options\": { \"title\": { \"display\": true, \"text\": \"GNB ACCOUNTS TYPES STATS\" } } }",
                "UTF-8");

        // Display the chart in Postman
        String responseBody = "<html><body><img src=\"" + chartUrl + "\"/></body></html>";
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "text/html");
        return new ResponseEntity<String>(responseBody, responseHeaders, HttpStatus.OK);
    }
}
