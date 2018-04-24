package source;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class EmailTest {

	public static void main(String[] args) {
		Properties prop = System.getProperties();
		prop.setProperty("mail.smtp.host", "localhost");
		prop.put("mail.smtp.auth", true);
		
		Session s = Session.getInstance(prop, 
				new javax.mail.Authenticator() {  								//password authentication
				protected PasswordAuthentication getPasswordAuthentication() {  
					return new PasswordAuthentication("fireflySMCS2020@gmail.com","lightningbug");  
				}  
		});
		MimeMessage msg = new MimeMessage(s);
		try{
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress("fireflySMCS2020@gmail.com"));
				msg.setFrom(new InternetAddress("fireflySMCS2020@gmail.com"));
				msg.setSubject("Test Email");
				msg.setText("This is a test text email");
				Transport.send(msg);
				System.out.println("Successfully sent message");
		}
		catch(AddressException e){
			System.out.println("Invalid email address");
		}
		catch(MessagingException e){
			System.out.println("Messaging error");
		}
		
	}

}
