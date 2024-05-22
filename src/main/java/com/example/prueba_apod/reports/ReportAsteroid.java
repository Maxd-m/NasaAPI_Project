package com.example.prueba_apod.reports;

import com.example.prueba_apod.models.Body;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

import java.io.IOException;
import java.util.List;


public class ReportAsteroid {

    public static void main(String args[]) throws IOException {

    }

    public void createPdf(String dest, List<Body> bodyList) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf, PageSize.A4.rotate());
        document.setMargins(20, 20, 20, 20);

        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
        Table table = new Table(UnitValue.createPercentArray(new float[]{4, 4, 4, 4, 4}))
                .useAllAvailableWidth();
//        BufferedReader br = new BufferedReader(new FileReader(DATAB));
//        List<Body> bodiesList;

        table.addHeaderCell(new Cell().add(new Paragraph("ID").setFont(bold)));
        table.addHeaderCell(new Cell().add(new Paragraph("NAME").setFont(bold)));
        table.addHeaderCell(new Cell().add(new Paragraph("URL CONSULT").setFont(bold)));
        table.addHeaderCell(new Cell().add(new Paragraph("ABSOLUTE MAGNITUDE").setFont(bold)));
        table.addHeaderCell(new Cell().add(new Paragraph("IS POTENTIAL HAZARD").setFont(bold)));

        //process(table,employeeDAOList.get(i) , bold, true);

        for (int i=0;i<bodyList.size();i++) {
            process(table, bodyList.get(i), font);
        }
//        br.close();
        document.add(table);

        //Close document
        document.close();
    }

    public void process(Table table, Body body, PdfFont font/*, boolean isHeader*/) {
//        String aux= String.valueOf(employee.getEmp_no());
        table.addCell(new Cell().add(new Paragraph(String.valueOf(body.getId())).setFont(font)));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(body.getName())).setFont(font)));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(body.getNasaJplUrl())).setFont(font)));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(body.getEstimatedDiameter().getKilometers())).setFont(font)));
        table.addCell(new Cell().add(new Paragraph(String.valueOf(body.getIsPotentiallyHazardousAsteroid())).setFont(font)));

    }
}
