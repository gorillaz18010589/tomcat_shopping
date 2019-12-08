package com.mail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendingEmail {
	private String userEmail;
	private String myHash;
	
	
	public SendingEmail (String userEmail, String myHash) {//接收到user輸入的email,跟這個user的hash
		this.userEmail = userEmail;
		this.myHash = myHash;
	}
	
	public void sendMail() {
		String email = "gorillaz1801058@gmail.com"; //自己的email
		String password = "049310528"; //自己的密碼
		String host = "smtp.gmail.com";  //設置mail主機為gmail
		String port ="587";
		Properties properties = new Properties();
		
		 // 指定发送邮件的主机为 smtp.qq.com
		    properties.put("mail.smtp.auth", "true");
		    properties.put("mail.smtp.host", host);    //設置mail主機為gmail 
	        properties.put("mail.smtp.starttls.enable", "true");
	        properties.put("mail.smtp.port", port);  //設定port號
	        
	        
	        //獲娶密碼驗證,回傳密碼認證到
	        Session session = Session.getDefaultInstance(properties,new Authenticator() {
	        	protected PasswordAuthentication getPasswordAuthentication() {
	        		return new PasswordAuthentication(email, password);
	        	}
	        });

	        try {
	        MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(email)); //從哪裡季信(新的網路地址)
				message.addRecipient(Message.RecipientType.TO,//寄信到哪裡(寄信方式.TO(新的地址))
                         new InternetAddress(userEmail));
				message.setSubject("一封信");
				message.setText("恭喜登入成功");
				//寄信同時川地參userEmail跟myHash參數給ActiveAccount
				message.setText("http://localhost:8080/page/Controller?key1=" +userEmail +"&key2=" +myHash +"&page=mymail");
				Transport.send(message); //運輸物件.send(Message)
				System.out.println("寄信成功" + userEmail);
	        }catch (Exception e) {
				System.out.println("寄信失敗" + e.toString());
			}	
			
		
	} 
	
}
