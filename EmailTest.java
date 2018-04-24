package source;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EmailTest {

	public static void main(String[] args) {
		Properties prop = new Properties();
		prop.setProperty("mail.smtp.host", "smtp.gmail.com");
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
		try{
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress("fireflySMCS2020@gmail.com"));
				msg.setFrom(new InternetAddress("fireflySMCS2020@gmail.com"));
				msg.setSubject("Test Email");
				msg.setText("This is a test text email");
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
