package com.crud.product.services;


import com.crud.product.entities.Product;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ProductsPDFExporter {
    private List<Product> listProducts;

    public ProductsPDFExporter(List<Product> listProducts) {
        this.listProducts = listProducts;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLACK);
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("#", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Nombre", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Código", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Precio", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Calificación", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table) {
        for(Product product : listProducts) {
            PdfPCell cellId = new PdfPCell();
            cellId.setPadding(5);
            cellId.setPhrase(new Phrase(String.valueOf(product.getId())));
            cellId.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cellId);


            PdfPCell cellName= new PdfPCell();
            cellName.setPadding(5);
            cellName.setPhrase(new Phrase(product.getProductName()));
            cellName.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cellName);

            PdfPCell cellCode = new PdfPCell();
            cellCode.setPadding(5);
            cellCode.setPhrase(new Phrase(product.getProductCode()));
            cellCode.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cellCode);

            PdfPCell cellPrice = new PdfPCell();
            cellPrice.setPadding(5);
            cellPrice.setPhrase(new Phrase(String.valueOf(product.getPrice())));
            cellPrice.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cellPrice);


            PdfPCell cellStarRating = new PdfPCell();
            cellStarRating.setPadding(5);
            cellStarRating.setPhrase(new Phrase(String.valueOf(product.getStarRating())));
            cellStarRating.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cellStarRating);
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLACK);

        Paragraph p = new Paragraph("Lista de Productos", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f, 3.0f,3.0f, 1.5f, 1.5f});
        table.setSpacingBefore(10);
        table.setHorizontalAlignment(Element.ALIGN_CENTER);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);
        document.close();
    }
}
