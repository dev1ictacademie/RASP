package nl.pameijer.ictacademie.rasp.view.pdf;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Authenticator;
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

public class Test_EMail_ToSend_2
{

	public static void main(String[] args)
	{
		try
		{
			final String fromEmail = "yyyyyyyyyy"; //requires valid gmail id
	        final String password = "xxxxxxxxxx"; // correct password for gmail id
	        final String toEmail = "yyyyyyyyyyyy"; // can be any email id

	        System.out.println("TLSEmail Start");
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

		    Authenticator auth = new Authenticator()
		    {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication()
                {
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



            // creates message part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(message, "text/html");

            // creates multi-part
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);


            // adds attachment

            Multipart multipart_1 = new MimeMultipart();
            MimeBodyPart messageBodyPart_1 = new MimeBodyPart();

            multipart.addBodyPart(messageBodyPart);

            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            attachmentBodyPart.attachFile(new File("application/pdf"));
            multipart.addBodyPart(attachmentBodyPart);

			message.setContent(multipart);

            Transport.send(message);

            System.out.println("Mail Sent");

		 }
		 catch (MessagingException | IOException mex)
		 {
			 System.out.println("Mail fail");
			 mex.printStackTrace();
		 }

	}
}
