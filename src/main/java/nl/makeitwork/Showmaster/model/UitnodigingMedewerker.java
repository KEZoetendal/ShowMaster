package nl.makeitwork.Showmaster.model;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class UitnodigingMedewerker {

    private String emailadres;
    private String bericht;

    private MailSender mailSender;
    private SimpleMailMessage simpleMailMessage;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }

    public String getEmailadres() {
        return emailadres;
    }

    public void setEmailadres(String emailadres) {
        this.emailadres = emailadres;
    }

    public String getBericht() {
        return bericht;
    }

    public void setBericht(String bericht) {
        this.bericht = bericht;
    }
}