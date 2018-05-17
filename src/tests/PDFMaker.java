package tests;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFMaker {
	
	public static void main(String[] args) throws IOException, DocumentException {
		String name = "Test";
		String path = "Documents/";
		String fileName = path + name + "_AP_Registration.pdf";
		Document doc = new Document();
		PdfWriter.getInstance(doc, new FileOutputStream(fileName));
		doc.open();
		PdfPTable table = new PdfPTable(9);
		for(int aw = 0; aw < 16; aw++){
            table.addCell(Integer.toString(aw));
        }
		doc.add(table);
		doc.close();
	}

}
