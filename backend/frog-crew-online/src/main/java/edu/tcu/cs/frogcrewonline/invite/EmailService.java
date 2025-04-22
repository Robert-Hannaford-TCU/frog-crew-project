package edu.tcu.cs.frogcrewonline.invite;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    // prints email to console

    public void sendEmail(String to, String subject, String body) {
        System.out.println("--------------------------------------------------");
        System.out.println("Sending email to: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body:\n" + body);
        System.out.println("--------------------------------------------------");
    }
}
