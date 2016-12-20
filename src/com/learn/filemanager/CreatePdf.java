package com.learn.filemanager;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * First iText example: Hello World.
 */
public class CreatePdf {

    /**
     * Path to the resulting PDF file.
     */
    public static final String RESULT
            = "./hello.txt";

    /**
     * Creates a PDF file: hello.pdf
     *
     * @param args no arguments needed
     */
    public static void main(String[] args)
            throws DocumentException, IOException {
        new CreatePdf().createPdf(RESULT);
    }

    /**
     * Creates a PDF document.
     *
     * @param filename the path to the new PDF document
     * @throws DocumentException
     * @throws IOException
     */
    public void createPdf(String filename)
            throws DocumentException, IOException {

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filename));

        document.open();

        document.add(new Paragraph("Hello World!"));

        document.close();
    }

}