package nl.pameijer.ictacademie.rasp.view.pdf;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Class to send an email with attachment.
 * @author hintveld
 *
 */
public class EMailAndAttachment
{
	/**
	 * Sending an e-mail with attachment.
	 * @param locationAttachment
	 */
	public void send(String locationAttachment )
	{
		//https://stackoverflow.com/questions/18075323/how-to-attach-generated-pdf-file-mail-in-java
	 	String SMTP_HOST_NAME = "smtp.gmail.com";
	    String SMTP_PORT = "587";

	    //add here from email address, to email address, password
	    String SMTP_FROM_ADDRESS = Account.EMAIL_ADDRESS;
	    String SMTP_TO_ADDRESS = Account.EMAIL_ADDRESS;
	    String PASS_WORD = Account.PASSWORD;

	    String strSubject = "Bezetting";
	    String fileAttachment = locationAttachment;

	    Properties props = new Properties();

	    props.put("mail.smtp.host", SMTP_HOST_NAME);
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.debug", "true");
	    props.put("mail.smtp.port", SMTP_PORT );
	    props.put("mail.smtp.starttls.enable", "true");

	    Session session = Session.getInstance(props,new javax.mail.Authenticator()
	    {
	    	protected javax.mail.PasswordAuthentication getPasswordAuthentication()
	        {
	    		return new javax.mail.PasswordAuthentication(SMTP_FROM_ADDRESS, PASS_WORD);
	        }
	    });

	    try
	    {
	    	Message msg = new MimeMessage(session);

	    	msg.setFrom(new InternetAddress(SMTP_FROM_ADDRESS));
	    	// create the message part
	    	MimeBodyPart messageBodyPart = new MimeBodyPart();
	    	// fill message
	    	messageBodyPart.setText("Test mail one");
	    	Multipart multipart = new MimeMultipart();
	    	multipart.addBodyPart(messageBodyPart);
	    	// Part two is attachment
	    	messageBodyPart = new MimeBodyPart();
	    	DataSource source = new FileDataSource(fileAttachment);
	    	messageBodyPart.setDataHandler(new DataHandler(source));
	    	messageBodyPart.setFileName(fileAttachment);
	    	multipart.addBodyPart(messageBodyPart);
	    	// Put parts in message
	    	msg.setContent(multipart);

	    	msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(SMTP_TO_ADDRESS));

	    	msg.setSubject(strSubject);
	    	// msg.setContent(content, "text/plain");

	    	Transport.send(msg);

	    }
	    catch(Exception e)
	    {
	    	e.printStackTrace();
	    }
	} // end method send
} // end class EMailAndAttachment
