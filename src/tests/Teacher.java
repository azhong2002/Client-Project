package tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.swing.*;

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
	
	public void addAll(ArrayList<String[]> newData) {
		examList.addAll(newData);
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
	
	public String makePDF(String path) throws IOException, DocumentException{
		Collections.sort(examList, (String[] item1, String[] item2) -> item1[1].compareTo(item2[1]));
		Collections.sort(examList, (String[] item1, String[] item2) -> item1[2].compareTo(item2[2]));
		Collections.sort(examList, (String[] item1, String[] item2) -> item1[4].compareTo(item2[4]));	//sort by test, period, and name
																										//in that order
		String fileName = path + name + "_AP_Registration.pdf";
		Document doc = new Document();
		PdfWriter.getInstance(doc, new FileOutputStream(fileName));	//makes doc to edit
		doc.open();
		
		Font font = new Font();
		font.setSize(12);
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100);
		table.setWidths(new float[] { 5, 18, 2, 10, 10, 3});
		for(String[] item : examList){	//Adds all exam items as rows of their data
			for(String data : item) {
				table.addCell(new Paragraph(data,font));
			}
            table.completeRow();	//go to new row
        }
		
		doc.add(table);
		doc.close();
		
		return fileName;
		
	}
	
	public void sendEmail(String user, String pass, String path) throws MessagingException, IOException, DocumentException{
		Properties prop = new Properties();
		prop.setProperty("mail.smtp.host", "smtp.gmail.com");	//change accordingly with email service
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", true);
		
		Session s = Session.getDefaultInstance(prop, 
				new javax.mail.Authenticator() {  								//password authentication
				protected PasswordAuthentication getPasswordAuthentication() {  
					return new PasswordAuthentication(user, pass);  
				}  
		});
		MimeMessage msg = new MimeMessage(s);
		Multipart multipart = new MimeMultipart();
		
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress("fireflySMCS2020@gmail.com"));
		msg.setFrom(new InternetAddress(user));
		msg.setSubject("AP Registration Information");
		
		MimeBodyPart msgBody = new MimeBodyPart();	//Add body message bodypart
		msgBody.setContent("Attached is a PDF containing the AP Registration information for your students.", "text/html");
		multipart.addBodyPart(msgBody);
		System.out.println("Text added");
		
		String filePath = makePDF(path);	//attach and make PDF file
		
		MimeBodyPart attachPart = new MimeBodyPart();	//Add attachment bodypart
        try {
            attachPart.attachFile(filePath);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        multipart.addBodyPart(attachPart);
        System.out.println("PDF attatched");
		
        msg.setContent(multipart);
		//msg.setText("This is a test email with attachment");
        
		System.out.println("sending email");
		long start = System.nanoTime();
		Transport.send(msg);
		System.out.printf("Message sent in %f seconds.", (System.nanoTime() - start)/1000000000.0);
	}
	
}
