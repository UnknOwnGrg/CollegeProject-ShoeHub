package com.CollegeProject.Ecomm.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class CommonUtil {

    @Autowired
    private  JavaMailSender mailSender;

    //To send mail
    public  Boolean sendMail(String url, String recipientEmail) throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        //to set the data
        helper.setFrom("gurungchyangba49@gmail.com", "ShoeHub");
        helper.setTo(recipientEmail);

        //to Send the Conntent
        String content = "<p>Hello,</p>" + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>" + "<p><a href=\"" + url
                + "\">Change my password</a></p>";

        helper.setSubject("Password Reset"); //to Set the Subject
        helper.setText(content, true); // to set he Body
        mailSender.send(message); //To send the message

        return true;
    }

    //TO Generate a Url
    public static String generateUrl(HttpServletRequest request) {
        String siteUrl = request.getRequestURL().toString();
        return siteUrl.replace(request.getServletPath(),"");
    }
}
