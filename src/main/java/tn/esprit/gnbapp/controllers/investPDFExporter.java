package tn.esprit.gnbapp.controllers;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import tn.esprit.gnbapp.entities.investesment;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;


public class investPDFExporter {
    private List<investesment> listinvestesment;

    public investPDFExporter(List<investesment> listinvestesment) {
        this.listinvestesment = listinvestesment;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.CYAN);

        //cell.setPadding(5);
        //Font font =FontFactory.getFont(FontFactory.HELVETICA);
        //font.setColor(Color.WHITE);


        cell.setPhrase(new Phrase("Amount"));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Taux"));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Invesstissor Email"));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Invesstissor Name"));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Invesstissor Second Name"));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Invesstissor Profession"));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {
        for (investesment investesments : listinvestesment) {
            table.addCell(String.valueOf(investesments.getAmoutInvestesment()));
            table.addCell(String.valueOf(investesments.getTauxInves()));
            table.addCell(investesments.getMailInvestesment());
            table.addCell(investesments.getNameInvestesment());
            table.addCell(investesments.getSecondnnameInvestesment());
            table.addCell(investesments.getProfessionInvestesment());
        }
    }

    void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document,response.getOutputStream());
        document.open();
        //Font font =FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        //font.setColor(Color.BLUE);
        //font.setSize(18);
        Paragraph title = new Paragraph("List of all investissement");
        document.add(title);
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);
        writeTableHeader(table);
        writeTableData(table);
        document.add(table);
        document.close();
    }

}