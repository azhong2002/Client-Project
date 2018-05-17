package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.FileOutputStream;
import java.io.IOException;

public class Teacher {

	public ArrayList<String[]> examList;
	public String name = "no_name";
	
	public Teacher() {
		examList = new ArrayList<String[]>();
	}
	
	public Teacher(ArrayList<String[]> examParam) {
		examList = examParam;
		name = examList.get(0)[3];
		if(name.length() >= 10 && name.substring(0,10).equals("No Teacher")){
			name = "No Teacher";
		}
	}
	
	public void display() {
		for(String[] item: examList) {
			for(String metaTag: item) {
				System.out.print(metaTag + ",");
			}
			System.out.println();
		}
	}
	
	public String toString() {
		return name;
	}
	
	public void makePDF(String path) throws IOException, DocumentException{
		Collections.sort(examList, (String[] item1, String[] item2) -> item1[1].compareTo(item2[1]));
		Collections.sort(examList, (String[] item1, String[] item2) -> item1[2].compareTo(item2[2]));
		Collections.sort(examList, (String[] item1, String[] item2) -> item1[4].compareTo(item2[4]));
		String fileName = path + name + "_AP_Registration.pdf";
		Document doc = new Document();
		PdfWriter.getInstance(doc, new FileOutputStream(fileName));
		doc.open();
		PdfPTable table = new PdfPTable(6);
		for(String[] item : examList){
			for(String data : item) {
				table.addCell(data);
			}
            table.completeRow();
        }
		doc.add(table);
		doc.close();
		
	}
	
}
