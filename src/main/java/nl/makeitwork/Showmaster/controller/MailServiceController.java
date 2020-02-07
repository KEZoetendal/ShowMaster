package nl.makeitwork.Showmaster.controller;

import nl.makeitwork.Showmaster.mail.MailService;
import nl.makeitwork.Showmaster.mail.MailServiceConfiguratie;
import nl.makeitwork.Showmaster.model.Medewerker;
import nl.makeitwork.Showmaster.model.UitnodigingMedewerker;
import nl.makeitwork.Showmaster.model.VerificatieToken;
import nl.makeitwork.Showmaster.repository.MedewerkerRepository;
import nl.makeitwork.Showmaster.repository.UitnodigingMedewerkerRepository;
import nl.makeitwork.Showmaster.repository.VerificatieTokenRepository;
import nl.makeitwork.Showmaster.validator.MedewerkerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;

/**
 * @author Pieter Dijkema
 * behandelen mailverzoeken vanuit de applicatie
 */
@Controller
public class MailServiceController {

    @Autowired
    UitnodigingMedewerkerRepository uitnodigingMedewerkerRepository;

    @Autowired
    VerificatieTokenRepository verificatieTokenRepository;

    @Autowired
    MedewerkerValidator medewerkerValidator;

    @Autowired
    private MedewerkerRepository medewerkerRepository;


    @PostMapping("/planner/gebruiker/overzicht/uitnodigen")
    protected String verstuurUitnodiging(@ModelAttribute("uitnodigingMedewerker") UitnodigingMedewerker uitnodiging, BindingResult result, Model model, @AuthenticationPrincipal Medewerker ingelogdeMedewerker) {

        medewerkerValidator.validateEmail(uitnodiging, result);


        if (result.hasErrors()) {
            List<Medewerker> alleGebruikers = medewerkerRepository.findAll();
            alleGebruikers.removeIf(medewerker -> medewerker.getMedewerkerId().equals(ingelogdeMedewerker.getMedewerkerId()));
            model.addAttribute("alleGebruikers", alleGebruikers);
            model.addAttribute("error", result);
            return "gebruikerOverzicht";
        } else {

            VerificatieToken verificatieToken = new VerificatieToken();
            verificatieTokenRepository.save(verificatieToken);

            uitnodiging.setVerificatieToken(verificatieToken);
            uitnodigingMedewerkerRepository.save(uitnodiging);

            String onderwerp = "Uitnodiging";
            String emailBody = uitnodiging.getBericht() + "\n\nKlik op deze link om je in te schrijven: http://localhost:8080/registreer/" + verificatieToken.getToken()
                    + "\n\nMet vriendelijke groet,\n\nPlanning Showmaster";
            // String emailBody = uitnodiging.getBericht() + "klik op deze link om je in te schrijven " + "192.168.1.126:8080/registreer";

            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MailServiceConfiguratie.class);
            MailService bean = context.getBean(MailService.class);
            bean.verstuurMail(uitnodiging.getEmailadres(), onderwerp, emailBody);
            return "redirect:/planner/gebruiker/overzicht";
        }
    }



    @PostMapping("/wachtwoord/reset")
    protected String wachtwoordResetEmail(@ModelAttribute("emailadres") String emailadres){
        VerificatieToken verificatieToken = new VerificatieToken();
        verificatieTokenRepository.save(verificatieToken);

        if (medewerkerRepository.findByGebruikersnaam(emailadres) != null) {
            String onderwerp = "Wachtwoord Reset";
            String emailBody = "\n\nKlik op deze link om je wachtwoord te resetten: http://localhost:8080/wachtwoord/reset/" + verificatieToken.getToken()
                    + "\n\nMet vriendelijke groet,\n\n Showmaster";
            // String emailBody = uitnodiging.getBericht() + "klik op deze link om je in te schrijven " + "192.168.1.126:8080/registreer";

            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MailServiceConfiguratie.class);
            MailService bean = context.getBean(MailService.class);
            bean.verstuurMail(emailadres, onderwerp, emailBody);

            System.out.println("test");

        }

        return "login";








    }
}

