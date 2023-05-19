package com.restopos.security.services;

//import com.restopos.models.Email;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Service
public class EmailService {
    public boolean sendEmail(String subject, String to,String token) throws MessagingException
//        code
    {
        boolean f=false;
        //Variable for gmail
        String host="smtp.gmail.com";
        String from="sharmaanurag0829@gmail.com";

        //get the system properties
        Properties properties = System.getProperties();
        System.out.println("PROPERTIES "+properties);

        //setting important information to properties object

        //host set
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");

        //Step 1: to get the session object..
        Session session=Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("sharmaanurag0829@gmail.com", "eyqymqimdjubooye");
            }



        });

        session.setDebug(true);

        //Step 2 : compose the message [text,multi media]
        MimeMessage m = new MimeMessage(session);
        String htmlMsg = "Thanks For register Resto POS ' <br>"
                +""
                + "Please use this Link to Generate Your Password " +" -> "+
                "<a href=\"http://127.0.0.1:5502/tabs.html?token="+token+"\">Generate Password</a> <br>"+
                "Password is confidential, do not share this  with anyone.</body>";
        try {

            //from email
            m.setFrom(from);
            m.setContent(htmlMsg,"text/html");
            //adding recipient to message
            m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            //adding subject to message
            m.setSubject(subject);
            m.setSentDate(new Date());


            //adding text to message
            m.setText(htmlMsg);
            m.setContent(htmlMsg, "text/html");
            //send

            //Step 3 : send the message using Transport class
            Transport.send(m);

            System.out.println("Sent success...................");
            f=true;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }
}
