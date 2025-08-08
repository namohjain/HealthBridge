package in.co.ehealth.care.util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendReport {

	
	public static void sendReportFile(String fileName,String email) {
		
		
		 String to=email;//change accordingly  
		  final String user=PropertyReader.getValue("email.login");//change accordingly  
		  final String password=PropertyReader.getValue("email.pwd");;//change accordingly  
		   
		  //1) get the session object     
		  Properties props = System.getProperties();  
		  props.setProperty("mail.transport.protocol", "smtp");     
		    props.setProperty("mail.host", "smtp.gmail.com");  
		    props.put("mail.smtp.auth", "true");  
		    props.put("mail.smtp.port", "465");  
		    props.put("mail.debug", "true");  
		    props.put("mail.smtp.socketFactory.port", "465");  
		    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
		    props.put("mail.smtp.socketFactory.fallback", "false");
		  Session session = Session.getDefaultInstance(props,  
		   new javax.mail.Authenticator() {  
		   protected PasswordAuthentication getPasswordAuthentication() {  
		   return new PasswordAuthentication(user,password);  
		   }  
		  });  
		     
		  //2) compose message     
		  try{  
		    MimeMessage message = new MimeMessage(session);  
		    message.setFrom(new InternetAddress(user));  
		    message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
		    message.setSubject("E-Health Care Test Report");  
		      
		    //3) create MimeBodyPart object and set your message text     
		    BodyPart messageBodyPart1 = new MimeBodyPart();  
		    messageBodyPart1.setText("E-Health Care Managment Test Report !!");  
		      
		    //4) create new MimeBodyPart object and set DataHandler object to this object      
		    MimeBodyPart messageBodyPart2 = new MimeBodyPart();  
		  
		    String name = PropertyReader.getValue("filePath")+"\\"+fileName;//change accordingly  
		    DataSource source = new FileDataSource(name);  
		    messageBodyPart2.setDataHandler(new DataHandler(source));  
		    messageBodyPart2.setFileName(fileName);  
		     
		     
		    //5) create Multipart object and add MimeBodyPart objects to this object      
		    Multipart multipart = new MimeMultipart();  
		    multipart.addBodyPart(messageBodyPart1);  
		    multipart.addBodyPart(messageBodyPart2);  
		  
		    //6) set the multiplart object to the message object  
		    message.setContent(multipart );  
		     
		    //7) send message  
		    Transport.send(message);  
		   
		   System.out.println("message sent....");  
		   }catch (MessagingException ex) {ex.printStackTrace();}  
		
	}
	
}
