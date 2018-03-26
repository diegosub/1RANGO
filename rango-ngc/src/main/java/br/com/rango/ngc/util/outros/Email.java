package br.com.rango.ngc.util.outros;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email
{
	private static final String USER  = "naoresponda@1rango.com.br";
	private static final String PASSWORD  = "carlos1988";
	
	public void enviar(String to, String assunto, String texto) throws AddressException, MessagingException, UnsupportedEncodingException 
	{
		String host = "mx1.hostinger.com.br";
		
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
        props.put("mail.smtp.socketFactory.port", "587");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
 
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator()
			{
				protected PasswordAuthentication getPasswordAuthentication()
				{
					return new PasswordAuthentication(USER, PASSWORD);
				}
			});
 
		
		Message message = new MimeMessage(session);
		InternetAddress me = new InternetAddress(USER);
        me.setPersonal("1Rango!");
		message.setFrom(me);		
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to));
		message.setSubject(assunto);
		message.setContent(texto, "text/html; charset=utf-8");
		
 
		Transport.send(message);
	}
}