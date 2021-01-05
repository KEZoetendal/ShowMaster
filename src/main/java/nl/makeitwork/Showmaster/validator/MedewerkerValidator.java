package nl.makeitwork.Showmaster.validator;

import nl.makeitwork.Showmaster.model.Medewerker;
import nl.makeitwork.Showmaster.model.EmailMetToken;
import nl.makeitwork.Showmaster.repository.EmailMetTokenRepository;
import nl.makeitwork.Showmaster.service.MedewerkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author ****
 */

@Component
public class MedewerkerValidator implements Validator {

    @Autowired
    private MedewerkerService medewerkerService;
    @Autowired
    private EmailMetTokenRepository emailMetTokenRepository;


    @Override
    public boolean supports(Class<?> aClass) {
        return Medewerker.class.equals(aClass);
    }


    public void validateEmail(Object object, Errors errors) {
        EmailMetToken emailMetToken = (EmailMetToken) object;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailadres", "NotEmpty");
        if (emailMetToken.getEmailadres().length() < 3) {
            errors.rejectValue("emailadres", "Size.uitnodigingFormulierGebruiker.emailadres");
        }
        if (medewerkerService.findByUsername(emailMetToken.getEmailadres()) != null) {
            errors.rejectValue("emailadres", "Duplicate.uitnodigingFormulierGebruiker.emailadres");
        }
    }


    @Override
    public void validate(Object object, Errors errors) {
        Medewerker medewerker = (Medewerker) object;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gebruikersnaam", "NotEmpty");
        if (medewerker.getGebruikersnaam().length() < 3 || medewerker.getGebruikersnaam().length() > 32) {
            errors.rejectValue("gebruikersnaam", "Size.registratieFormulier.username");
        }
        if (medewerkerService.findByUsername(medewerker.getGebruikersnaam()) != null) {
            errors.rejectValue("gebruikersnaam", "Duplicate.registratieFormulier.gebruikersnaam");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "wachtwoord", "NotEmpty");
        if (medewerker.getWachtwoord().length() < 8 || medewerker.getWachtwoord().length() > 32) {
            errors.rejectValue("wachtwoord", "Size.registratieFormulier.wachtwoord");
        }
        if (!medewerker.getWachtwoordBevestigen().equals(medewerker.getWachtwoord())) {
            errors.rejectValue("wachtwoordBevestigen", "Diff.registratieFormulier.wachtwoordBevestigen");
        }
    }


    public void validateWachtwoord(Object object, Errors errors) {
        Medewerker medewerker = (Medewerker) object;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "wachtwoord", "NotEmpty");
        if (medewerker.getWachtwoord().length() < 8 || medewerker.getWachtwoord().length() > 32) {
            errors.rejectValue("wachtwoord", "Size.registratieFormulier.wachtwoord");
        }
        if (!medewerker.getWachtwoordBevestigen().equals(medewerker.getWachtwoord())) {
            errors.rejectValue("wachtwoordBevestigen", "Diff.registratieFormulier.wachtwoordBevestigen");
        }
    }
}