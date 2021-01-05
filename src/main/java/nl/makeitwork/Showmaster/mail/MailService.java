package nl.makeitwork.Showmaster.mail;

import nl.makeitwork.Showmaster.controller.ErrorsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

/**
 * @author ******
 */
@Component
public class MailService {

    @Autowired
    private MailSender mailSender;
    @Autowired
    private SimpleMailMessage mailMessage;

    private ErrorsController errorsController;

    public void verstuurMail(String emailAdres, String onderwerp, String bericht) {
        SimpleMailMessage uitnodiging = new SimpleMailMessage(mailMessage);
        uitnodiging.setTo(emailAdres);
        uitnodiging.setSubject(onderwerp);
        uitnodiging.setText(bericht);
        try {
            mailSender.send(uitnodiging);
        } catch (MailException exception) {
            errorsController.getErrorPath();
        }
    }
}