package nl.makeitwork.Showmaster.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import javax.persistence.*;

/**
 * @author ******
 */

@Entity
public class EmailMetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer emailMetTokenId;
    private String emailadres;
    private String bericht;

    @Transient
    private MailSender mailSender;

    @Transient
    private SimpleMailMessage simpleMailMessage;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "verificatieTokenId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private VerificatieToken verificatieToken;

    public VerificatieToken getVerificatieToken() {
        return verificatieToken;
    }

    public void setVerificatieToken(VerificatieToken verificatieToken) {
        this.verificatieToken = verificatieToken;
    }

    public Integer getEmailMetTokenId() {
        return emailMetTokenId;
    }

    public void setEmailMetTokenId(Integer emailMetTokenId) {
        this.emailMetTokenId = emailMetTokenId;
    }

    public MailSender getMailSender() {
        return mailSender;
    }

    public SimpleMailMessage getSimpleMailMessage() {
        return simpleMailMessage;
    }

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