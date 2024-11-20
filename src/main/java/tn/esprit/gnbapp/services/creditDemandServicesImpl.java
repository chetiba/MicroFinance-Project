package tn.esprit.gnbapp.services;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tn.esprit.gnbapp.entities.*;
import tn.esprit.gnbapp.repositories.accountRepository;
import tn.esprit.gnbapp.repositories.creditDemandRepository;
import tn.esprit.gnbapp.repositories.creditRepository;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class creditDemandServicesImpl implements IcreditDemandServices {
    @Autowired
    private final creditRepository creditRepository ;



    @Autowired
    creditDemandRepository creditDemandRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public List<creditDemand> retrieveAllcreditDemands() {
        return creditDemandRepository.findAll();
    }


    @Override
    public creditDemand retrievecreditDemand(int id_cd) {
        return creditDemandRepository.findById(id_cd).orElse(null);
    }

    @Override
    public void removecreditDemand(int id_cd) {
        creditDemandRepository.deleteById(id_cd);

    }
    @Override
    public creditDemand assignCreditDemandToCredit(int id_cd, int id_credit){
        creditDemand creditDemand = creditDemandRepository.findById(id_cd).orElse(null);
        credit credit = creditRepository.findById(id_credit).orElse(null);
        creditDemand.setCredit(credit);
        return creditDemandRepository.save(creditDemand);
    }




    @Autowired
    private accountRepository accountRepository;
    public creditDemand addCreditDemand(creditDemand creditDemand, int accountId) {
        account account = accountRepository.findById(accountId).orElseThrow(() -> new IllegalArgumentException("Invalid account Id:" + accountId));
        creditDemand.setAccount(account);
        return creditDemandRepository.save(creditDemand);
    }


    @Override
    public creditDemand addOrUpdatecreditDemand(creditDemand creditDemand) {
        return creditDemandRepository.save(creditDemand);
    }

    @Override
    public List<creditDemand> retrieveAllcreditDemandsbyacc(int id_acc) {
        return (List<creditDemand>) creditDemandRepository.getCredit_acc(id_acc);
    }

    @Override
    public double calculateMonthlyPayment(int value_cd, int duration_cd, typeCd typeCd) {
        double interestRate = typeCd.getInterestRate();
        double monthlyInterestRate = interestRate / 12;
        double numberOfPayments = duration_cd * 12;
        double monthlyPayment = (monthlyInterestRate * (double) value_cd) /
                (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
        return Math.round(monthlyPayment * 100) / 100.0;
    }

    @Override
    public int calculateDuration(int value_cd, double sum_cd, typeCd typeCd) {
        double interestRate = typeCd.getInterestRate();
        double monthlyInterestRate = interestRate / 12;
        double numberOfPayments = Math.log(sum_cd / (sum_cd - (monthlyInterestRate * (double) value_cd))) /
                Math.log(1 + monthlyInterestRate);
        return (int) Math.round(numberOfPayments / 12);
    }

       public void sendCreditNotification(creditDemand creditDemand) {

        if(creditDemand.isStatus_cd()) {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(creditDemand.getAccount().getEmail());
            message.setSubject("Credit Status Notification");

            String text = "Your credit demand has been ";

            if(creditDemand.getValue_cd() == 1) {
                text += "approved.";
            } else {
                text += "refused.";
            }

            message.setText(text);

            javaMailSender.send(message);
        }
    }



    public void sendEmail(String recipientEmail, String subject, String body) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(recipientEmail);
        helper.setSubject(subject);
        helper.setText(body, true);

        mailSender.send(message);

    }


    @Override
    public List<amortissement> createAmortizationTable(int id_cd) {
        creditDemand creditDemand = creditRepository.findCreditDemand(id_cd);
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
        creditRepository.save(creditDemand.getCredit());

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


    public void createExcelFile(List<amortissement> amortizationTable) throws IOException {
        // Create a new Excel workbook
        Workbook workbook = new XSSFWorkbook();

        // Create a new sheet
        Sheet sheet = workbook.createSheet("Amortization Table");

        // Add column headers
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Date");
        headerRow.createCell(1).setCellValue("Remaining Balance");
        headerRow.createCell(2).setCellValue("Interest");
        headerRow.createCell(3).setCellValue("Principal");
        headerRow.createCell(4).setCellValue("Payment");

        // Add data rows
        int rowNum = 1;
        for (amortissement a : amortizationTable) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(a.getDate().toString());
            row.createCell(1).setCellValue(a.getRemainingBalance());
            row.createCell(2).setCellValue(a.getInterest());
            row.createCell(3).setCellValue(a.getPrincipal());
            row.createCell(4).setCellValue(a.getPayment());
        }

        // Auto-size columns
        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the workbook to a file
        FileOutputStream outputStream = new FileOutputStream("amortization.xlsx");
        workbook.write(outputStream);
        workbook.close();
    }



}


