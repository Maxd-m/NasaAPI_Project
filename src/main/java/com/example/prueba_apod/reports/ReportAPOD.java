package com.example.prueba_apod.reports;

import com.example.prueba_apod.models.APOD;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.ColumnDocumentRenderer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class ReportAPOD {
    public void createReport(String dest, List<APOD> apodList, String titleText) throws IOException {
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


        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        Paragraph title = new Paragraph(titleText)
                .setFontColor(new DeviceRgb(8, 73, 117))
                .setFontSize(20f).setFont(bold);//.setPaddingLeft(320);
        //title.getAccessibilityProperties().setRole(StandardRoles.H1);

        //Define column areas
        Rectangle[] columns = {new Rectangle(offSet - 5, offSet, columnWidth, columnHeight),
                new Rectangle(offSet + columnWidth, offSet, columnWidth, columnHeight),
                new Rectangle(offSet + columnWidth * 2 + 5, offSet, columnWidth, columnHeight)};
        document.setRenderer(new ColumnDocumentRenderer(document, columns));
        /*
        * for (int i = 0; i < apodList.size(); i++) {
           Image image = new Image(ImageDataFactory.create(apodList.get(i).getUrl())).setWidth(columnWidth);
           // Image apple = new Image(ImageDataFactory.create(APPLE_IMG)).setWidth(columnWidth);

            ReportAPOD.addArticle(document,"Prueba de pruebas apod"+i,"BY sdlafkhluke","Imagen","aslkdfjgsdagfuklsd ldksafjhkjds falksdjfbjklnds lksadfjhkldsa flkjdbslkjas");

        }
        * */

        document.add(title);

        for (APOD element : apodList){
            if(element.getMedia_type().equals("image")){
                System.out.println("procesando...");
                Image img = new Image(ImageDataFactory.create(element.getUrl())).setWidth(columnWidth);
                ReportAPOD.addArticle(document,element.getTitle(),"Date: "+element.getDate()+" Copyright: "+element.getCopyright(),img,element.getExplanation());
            }
        }

        document.close();

    }
    public static void addArticle(Document doc, String title, String author,Image image, String text) throws IOException {
        Paragraph p1 = new Paragraph(title)

                .setFontSize(14);
        doc.add(p1);
        doc.add(image);
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
