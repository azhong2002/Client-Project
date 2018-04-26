package tests;

//FireflySMCS2020 Andrew Zhong 4/25/18 
//Test email program, fill in recipients and senders

import java.io.IOException;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EmailTest {

	public static void main(String[] args) {
		Properties prop = new Properties();
		prop.setProperty("mail.smtp.host", "smtp.gmail.com");	//change accordingly with email service
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.port", "587");
		prop.put("mail.smtp.auth", true);
		
		Session s = Session.getDefaultInstance(prop, 
				new javax.mail.Authenticator() {  								//password authentication
				protected PasswordAuthentication getPasswordAuthentication() {  
					return new PasswordAuthentication("fireflySMCS2020@gmail.com", "lightningbug");  
				}  
		});
		MimeMessage msg = new MimeMessage(s);
		Multipart multipart = new MimeMultipart();
		
		try{
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress("fireflySMCS2020@gmail.com"));
				msg.setFrom(new InternetAddress("fireflySMCS2020@gmail.com"));
				msg.setSubject("Test Email");
				
				MimeBodyPart msgBody = new MimeBodyPart();	//Add body message bodypart
				msgBody.setContent("This is a test email with attachment", "text/html");
				multipart.addBodyPart(msgBody);
				
				String filePath = "README.txt";
				
				MimeBodyPart attachPart = new MimeBodyPart();	//Add attatchment bodypart
                try {
                    attachPart.attachFile(filePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                multipart.addBodyPart(attachPart);
				
                msg.setContent(multipart);
				//msg.setText("This is a test email with attachment");
                
				System.out.println("sending email");
				long start = System.nanoTime();
				Transport.send(msg);
				System.out.printf("Message sent in %f seconds.", (System.nanoTime() - start)/1000000000.0);
		}
		catch(AddressException e){
			System.out.println("Invalid email address");
			e.printStackTrace();
		}
		catch(MessagingException e){
			System.out.println("Messaging error");
			e.printStackTrace();
		}
		
	}

}
