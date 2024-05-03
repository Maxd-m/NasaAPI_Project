package com.example.prueba_apod.reports;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.ColumnDocumentRenderer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ReportAPOD {
    public void createReport(String dest) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);
        PageSize ps = PageSize.A4;

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf, ps);
        document.setMargins(20, 20, 20, 20);

        //Set column parameters
        float offSet = 36;
        float columnWidth = (ps.getWidth() - offSet * 2 + 10) / 3;
        float columnHeight = ps.getHeight() - offSet * 2;

        //Define column areas
        Rectangle[] columns = {new Rectangle(offSet - 5, offSet, columnWidth, columnHeight),
                new Rectangle(offSet + columnWidth, offSet, columnWidth, columnHeight),
                new Rectangle(offSet + columnWidth * 2 + 5, offSet, columnWidth, columnHeight)};
        document.setRenderer(new ColumnDocumentRenderer(document, columns));

        for (int i = 0; i < 28; i++) {
            ReportAPOD.addArticle(document,"Prueba de pruebas apod"+i,"BY sdlafkhluke","Imagen","aslkdfjgsdagfuklsd ldksafjhkjds falksdjfbjklnds lksadfjhkldsa flkjdbslkjas");

        }

        document.close();

    }
    public static void addArticle(Document doc, String title, String author,String image, String text) throws IOException {
        Paragraph p1 = new Paragraph(title)

                .setFontSize(14);
        doc.add(p1);
        //doc.add(image);
        Paragraph p2 = new Paragraph()

                .setFontSize(7)
                .setFontColor(ColorConstants.GRAY)
                .add(author);
        doc.add(p2);
        Paragraph p3 = new Paragraph()

                .setFontSize(10)
                .add(text);
        doc.add(p3);
    }
}
