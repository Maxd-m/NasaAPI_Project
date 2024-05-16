package com.example.prueba_apod.reports;

import com.example.prueba_apod.database.dao.DAOUser;
import com.example.prueba_apod.models.User;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ReportUsers {
    public void createPdf(String dest) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf, PageSize.A4.rotate());
        document.setMargins(20, 20, 20, 20);


        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        //porcentaje de anchura de cada columna de la tabla
        Table table = new Table(UnitValue.createPercentArray(new float[]{1, 1, 2, 2, 2, 1}))
                .useAllAvailableWidth();



        process(table,new User(),bold,true);
        //System.out.println("despues titulo");
        for(User cliente: new DAOUser().findAll()){
            process(table,cliente,font,false);
        }
        //System.out.println("despues for");
        Paragraph title = new Paragraph("Registered Users")
                .setFontColor(new DeviceRgb(8, 73, 117))
                .setFontSize(20f).setFont(bold).setPaddingLeft(320);
        title.getAccessibilityProperties().setRole(StandardRoles.H1);

        document.add(title);
        document.add(table);
        // System.out.println("despues add table");

        //Close document
        document.close();
        //System.out.println("despues .close");
        openFile(dest);
    }

    public void process(Table table, User user, PdfFont font, boolean isHeader){
        if(isHeader){
            table.addHeaderCell(new Cell().add(new Paragraph("ID").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("Age").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("Name").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("Username").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("Email").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("Gender").setFont(font)));
        }else {
            table.addCell(new Cell().add(new Paragraph(user.getId()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(user.getAge()+"").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(user.getName()).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(user.getUser()).setFont(font)));
            table.addCell(new Cell().add(new Paragraph(user.getMail()).setFont(font)));
            if(user.getGender().equals("H")){
                table.addCell(new Cell().add(new Paragraph("M").setFont(font)));
            }
            if(user.getGender().equals("M")){
                table.addCell(new Cell().add(new Paragraph("F").setFont(font)));
            }


        }
    }

    private void openFile(String filename)
    {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(filename);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
    }
}
