package nl.pameijer.ictacademie.rasp.view.pdf;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Test_EMail_ToSend
{

	public static void main(String[] args)
	{
		try
		{
			final String fromEmail = "YYYYYY"; //requires valid gmail id
	        final String password = "XXXXXX"; // correct password for gmail id
	        final String toEmail = "YYYYYY"; // can be any email id

	        System.out.println("TLSEmail Start");
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

		    Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(toEmail, password);
                }
            };

            Session session = Session.getInstance(props, auth);
            System.out.println("Mail Check 2");

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

            message.setSubject("Zit plaatsen overzicht");
            message.setText("");

            System.out.println("Mail Check 3");

            Transport.send(message);

            System.out.println("Mail Sent");

		 }
		 catch (MessagingException mex)
		 {
			 System.out.println("Mail fail");
			 mex.printStackTrace();
		 }
	}
}
