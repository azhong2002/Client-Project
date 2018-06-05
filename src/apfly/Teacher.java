package apfly;

//Andrew Zhong 5/19/18 Teacher object for APFly AP Exam Registration notification app
//Holds list of students and creates a PDF table to send to the corresponding teacher

import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.io.*;

import javax.mail.*;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.*;

public class Teacher {

	public static String defaultEmail = "Debra_L_Dresser@mcpsmd.org";
	public ArrayList<String[]> examList;
	public String name = "no_name";
	public String teacherEmail;	//email address of teacher
	
	public Teacher() {
		examList = new ArrayList<String[]>();
	}
	
	public void setEmail() throws Exception{
		URL staffDir = new URL("http://www.montgomeryschoolsmd.org/schools/poolesvillehs/staff/directory.aspx");	//TODO customize
        BufferedReader in = new BufferedReader(
        new InputStreamReader(staffDir.openStream()));

        String source = "";
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
        	source += inputLine;
        }
        int start = source.indexOf("mailto:",source.indexOf(name + "</p>"));
        if(source.indexOf(name + "</p>") == -1) {
        	teacherEmail = defaultEmail;
        } else {
        	teacherEmail = source.substring(start + 7, source.indexOf("\">", start));
        }
        in.close();
	}
	
	public Teacher(ArrayList<String[]> examParam) {	//preconditon examParam has a first element
		examList = examParam;
		name = examList.get(0)[3];
		if(name.length() >= 10 && name.substring(0,10).equals("No Teacher")){
			name = "No Teacher";
			teacherEmail = defaultEmail;
		}
		else {
			try {
				setEmail();
			} catch (Exception e) {
				System.out.println("Bad URL for staff directory; unable to retrieve email.");	//TODO fix in UI
				e.printStackTrace();
			}
		}
	}
	
	public void addAll(ArrayList<String[]> newData) {	//adds all new entries, no longer used
		for(String[] item: newData) {
			boolean isNew = true;
			for(String[] oldItem : examList) {
				if (item.equals(oldItem)) {
					isNew = false;
				}
			}
			if(isNew) {
				examList.add(item);
			}
		}
	}
	
	public int countRegistered(){
		int count = 0;
		for(String[] item : examList){
			if(item[5].trim().toLowerCase().equals("yes")){
				count++;
			}
		}
		return count;
	}
	
	public int[] countRegistered(int period){	//registration per class as tuple (registered, total)
		int[] count = new int[2];
		for(String[] item : examList){
			if(Integer.parseInt(item[2]) == period) {
				count[1]++;
				if(item[5].trim().toLowerCase().equals("yes")){
					count[0]++;
				}
			}
			
		}
		return count;
	}
	
	public void display() {	//testing purposes only
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
	
	public String makePDF() throws IOException, DocumentException{	//makes PDF w/ table of exam registries and returns its path
		Collections.sort(examList, (String[] item1, String[] item2) -> item1[1].compareTo(item2[1]));
		Collections.sort(examList, (String[] item1, String[] item2) -> item1[2].compareTo(item2[2]));
		Collections.sort(examList, (String[] item1, String[] item2) -> item1[4].compareTo(item2[4]));	//sort by test, period, and name
																										//in that order
		String fileName = name + "_AP_Registration.pdf";
		Document doc = new Document();
		PdfWriter.getInstance(doc, new FileOutputStream(fileName));	//makes doc to edit
		doc.open();
		
		Font font = new Font();
		font.setSize(12);
		PdfPTable header = new PdfPTable(5);
		header.setWidths(new float[] {15, 20, 4, 15, 4});
		PdfPCell nameCell = createHeaderCell(name);
		nameCell.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
		header.addCell(nameCell);
		header.addCell(createHeaderCell("Total Exams Registered:"));
		header.addCell(createHeaderCell(Integer.toString(countRegistered())));
		header.addCell(createHeaderCell("Total Students:"));
		header.addCell(createHeaderCell(Integer.toString(examList.size())));
		
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100);
		table.setWidths(new float[] { 5, 18, 2, 10, 10, 3});
		int currentPeriod = 0;
		for(String[] item : examList){	//Adds all exam items as rows of their data
			if(currentPeriod != Integer.parseInt(item[2])){	//if it's a new class, add an information header 
				currentPeriod = Integer.parseInt(item[2]);
				
				int[] registered = countRegistered(currentPeriod);	//gets tuple per period (registered, total)
				
				table.addCell(createHeaderCell("Period " + currentPeriod));
				table.addCell(createHeaderCell("Total Exams Registered:"));
				table.addCell(createHeaderCell(Integer.toString(registered[0])));
				table.addCell(createHeaderCell("Total Students:"));
				table.addCell(createHeaderCell(Integer.toString(registered[1])));
				table.addCell(createHeaderCell(""));
				
	            table.completeRow();	//go to new row
			}
			for(String data : item) {
				table.addCell(new Paragraph(data,font));
			}
            table.completeRow();	//go to new row
        }
		
		header.setSpacingAfter(12f);	//intertable space
		
		doc.add(header);
		doc.add(table);
		doc.close();
		
		return fileName;
		
	}
	
	private PdfPCell createHeaderCell(String string) {	//makes header cell
		PdfPCell out = new PdfPCell(new Phrase(string));
		out.setBorderWidthBottom(2);
		out.setBorderWidthTop(2);
		out.setBorderWidthLeft(0);
		out.setBorderWidthRight(0);
		out.setPaddingBottom(6);
		out.setPaddingTop(6);
		out.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
		return out;
	}

	public void sendEmail(String user, String pass, String customMessage) throws MessagingException, IOException, DocumentException{	//sends PDF w/ email
		Properties prop = new Properties();
		
		String smtp = "smtp.gmail.com";
	    int port = 587;
	    
	    if(user.indexOf("@outlook.com") != -1 || user.indexOf("@hotmail.com") != -1) {
	    	smtp = "smtp.live.com";
	    }
	    else if(user.indexOf("@yahoo.com") != -1){
	    	smtp = "mail.yahoo.com";
	    }
	    else if(user.indexOf("@verizon.net") != -1){
	    	smtp = "outgoing.verizon.net";
	    	port = 465;
	    }
	    else if(user.indexOf("@aol.com") != -1) {
	    	smtp = "smtp.aol.com";
	    }
		
		prop.setProperty("mail.smtp.host", smtp);	//change accordingly with email service
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.port", port);
		prop.put("mail.smtp.auth", true);
		
		Session s = Session.getDefaultInstance(prop, 
				new javax.mail.Authenticator() {  								//password authentication
				protected javax.mail.PasswordAuthentication getPasswordAuthentication() {  
					return new PasswordAuthentication(user, pass);  
				}  
		});
		MimeMessage msg = new MimeMessage(s);
		Multipart multipart = new MimeMultipart();
		
		msg.addRecipient(Message.RecipientType.TO, new InternetAddress(teacherEmail));
		msg.setFrom(new InternetAddress(user));
		msg.setSubject("AP Registration Information");
		
		MimeBodyPart msgBody = new MimeBodyPart();	//Add body message bodypart
		msgBody.setContent(customMessage, "text/html");
		multipart.addBodyPart(msgBody);
		System.out.println("Text added");
		
		String filePath = makePDF();	//attach and make PDF file
		
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
		System.out.printf("Message sent in %f seconds.\n", (System.nanoTime() - start)/1000000000.0);
		
		File doc = new File(filePath);
		if(doc.delete()) {
			System.out.println("Deleted " + filePath);
		}
	}
	
}
