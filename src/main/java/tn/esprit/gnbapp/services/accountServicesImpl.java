package tn.esprit.gnbapp.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.gnbapp.entities.account;

import tn.esprit.gnbapp.entities.typeAcc;
import tn.esprit.gnbapp.repositories.accountRepository;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

import java.io.FileOutputStream;
import java.util.List;


@Service
@RequiredArgsConstructor
public class accountServicesImpl implements IaccountServices {
    private final accountRepository accountrepository;

    @Override
    public List<account> retrieveAllAccounts() {
        return accountrepository.findAll();
    }

    @Override
    public account addOrUpdateaccount(account a) {

        a.setRib(GenerateRib());
        return accountrepository.save(a);
    }

    @Override
    public account retrieveaccount(int id_acc) {
        return accountrepository.findById(id_acc).orElse(null);
    }

    @Override
    public void removeaccount(int id_acc) {
        accountrepository.deleteById(id_acc);

    }

    @Override
    public String GenerateRib() {
        String codeBanque = "01920";
        String codeGuichet = "00410";

        int targetStringLength = 11;
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < targetStringLength; i++) {
            int digit = random.nextInt(10);
            stringBuilder.append(digit);
        }

        String numeroCompte = stringBuilder.toString();
        String codeGV = codeBanque + codeGuichet + numeroCompte;
        String rib = "TN" + codeGV;

        long b = Long.parseLong(codeGV.substring(0, 5));
        long g = Long.parseLong(codeGV.substring(5, 10));
        long c = Long.parseLong(codeGV.substring(10));

        long x = 97 - ((89 * b + 15 * g + 3 * c) % 97);
        rib += String.format("%02d", x);

        return rib;
    }

    @Scheduled(cron = "10 * * * * *", zone="Africa/Tunis")
    @Transactional
    public void GetInterestAmount() {
        // Calcul taux journalier
        final float T_journalier = (float) (0.05 / 360);
        List<account> List_Acc = accountrepository.findByTypeacc(typeAcc.savings);
        for (account acc : List_Acc) {
            float balance = (float) acc.getBalance_acc();
            float interest = acc.getInterest();
            int index = acc.getIndex_interest();

            float newInterest = (balance + interest) * T_journalier + interest;
            index += 1;

            if (index == 90) {
                // Retrancher la retenue à la source de 20%
                balance += interest * 0.8f;
                index = 0;
                newInterest = 0;
            }

            acc.setBalance_acc(balance);
            acc.setInterest(newInterest);
            acc.setIndex_interest(index);

            // Print out the relevant information
            System.out.println("Account: " + acc.getId_acc());
            System.out.println("Original balance: " + balance);
            System.out.println("New interest rate: " + newInterest);
            System.out.println("Updated balance: " + (balance + newInterest * 0.8f));

            accountrepository.save(acc);
        }

        System.out.println("Interest calculation complete.");
    }

@Override
public void generatePdf(List<account> accounts, String fileName) throws DocumentException, IOException {
    Document document = new Document();
    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileName));
    writer.setPageEvent((PdfPageEvent) new PageNumberHandler()); // Add page numbering and date
    document.open();
    Image logo = Image.getInstance("file:/C:/Users/ahmed/Downloads/logo.png");
    logo.scaleAbsolute(100, 100);
    document.add(logo);
    Paragraph welcome = new Paragraph("Welcome to GNB BANK", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20));
    welcome.setAlignment(Element.ALIGN_CENTER);
    welcome.setSpacingAfter(20);
    document.add(welcome);
    Paragraph message = new Paragraph("This is your Bank Account Informations", FontFactory.getFont(FontFactory.HELVETICA, 14));
    message.setAlignment(Element.ALIGN_CENTER);
    message.setSpacingAfter(150); // Add some space after the message
    document.add(message);

    // Add date in the top right corner
    Paragraph date = new Paragraph(new Date().toString(), FontFactory.getFont(FontFactory.HELVETICA, 10));
    date.setAlignment(Element.ALIGN_RIGHT);
    welcome.setSpacingAfter(50);
    document.add(date);

    // Add table header
    PdfPTable table = new PdfPTable(6);
    table.setWidthPercentage(100);
    table.addCell(getHeaderCell("Client Reference"));
    table.addCell(getHeaderCell("First Name"));
    table.addCell(getHeaderCell("Last Name"));
    table.addCell(getHeaderCell("Your Rib"));
    table.addCell(getHeaderCell("Your Balance "));
    table.addCell(getHeaderCell("Your Type Account"));

    // Sort accounts by user ID
    Collections.sort(accounts, (a1, a2) -> a1.getUser().getId_user() - a2.getUser().getId_user());

    // Add table data
    for (account account : accounts) {
        table.addCell(getDataCell(String.valueOf(account.getUser().getId_user())));
        table.addCell(getDataCell(account.getUser().getFirstname()));
        table.addCell(getDataCell(account.getUser().getLastname()));
        table.addCell(getDataCell(account.getRib()));
        table.addCell(getDataCell(String.format("%.2f", account.getBalance_acc())));
        table.addCell(getDataCell(account.getTypeacc().toString()));
    }

    document.add(table);
    document.close();
}

    private PdfPCell getHeaderCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPadding(8);
        return cell;
    }

    private PdfPCell getDataCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(6);
        return cell;
    }

    class PageNumberHandler extends PdfPageEventHelper {
        private boolean skipFirstPage = true;

        public void onEndPage(PdfWriter writer, Document document) {
            if (skipFirstPage) {
                skipFirstPage = false;
                return;
            }
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            table.addCell(new PdfPCell());
            PdfPCell cell = new PdfPCell(new Phrase("Page " + writer.getPageNumber(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
            try {
                document.add(table);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public int countSavingsAccounts() {
        Long count = accountrepository.countByTypeacc(typeAcc.savings);
        return count.intValue();
    }

    @Override
    public int countCurrentAccounts() {
        Long count = accountrepository.countByTypeacc(typeAcc.current);
        return count.intValue();
    }

    @Override
    public int countAccounts() {
        Long nbr= accountrepository.count();
        return nbr.intValue();

    }
}
