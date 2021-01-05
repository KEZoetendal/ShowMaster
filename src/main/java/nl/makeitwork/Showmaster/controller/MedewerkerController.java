package nl.makeitwork.Showmaster.controller;

import nl.makeitwork.Showmaster.model.*;
import nl.makeitwork.Showmaster.repository.*;
import nl.makeitwork.Showmaster.service.MedewerkerService;
import nl.makeitwork.Showmaster.service.MedewerkerServiceImplementatie;
import nl.makeitwork.Showmaster.service.SecurityService;
import nl.makeitwork.Showmaster.service.SecurityServiceImplementatie;
import nl.makeitwork.Showmaster.validator.MedewerkerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @author ****
 * 20-12-19 - Karin Zoetendal: profiel/wijzigen en profielpagina worden nu correct weergegeven
 */
@Controller
public class MedewerkerController {

    @Autowired
    SecurityServiceImplementatie securityServiceImplementatie;
    @Autowired
    private MedewerkerService medewerkerService;
    @Autowired
    private MedewerkerServiceImplementatie medewerkerServiceImplementatie;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private MedewerkerValidator medewerkerValidator;
    @Autowired
    private MedewerkerRepository medewerkerRepository;
    @Autowired
    private MedewerkerProfielGegevensRepository medewerkerProfielGegevensRepository;
    @Autowired
    VoorstellingsTaakRepository voorstellingsTaakRepository;
    @Autowired
    TaakRepository taakRepository;
    @Autowired
    VoorstellingRepository voorstellingRepository;
    @Autowired
    VerificatieTokenRepository verificatieTokenRepository;
    @Autowired
    EmailMetTokenRepository emailMetTokenRepository;
    @Autowired
    MedewerkerInschrijvingVoorstellingRepository medewerkerInschrijvingVoorstellingRepository;


    @GetMapping("/registreer")
    protected String omleidenNaarLogin() {
        return "redirect:/";
    }


    @GetMapping("/registreer/{token}")
    protected String showRegistratieFormulier(Model model, @PathVariable String token) {

        VerificatieToken verificatieToken = verificatieTokenRepository.findByToken(token);

        if (verificatieToken != null) {
            if (!verificatieToken.getTokenGebruikt()) {
                EmailMetToken emailMetToken = emailMetTokenRepository.findByVerificatieToken(verificatieToken);

                model.addAttribute(new Medewerker());
                model.addAttribute("gebruikersnaam", emailMetToken.getEmailadres());
                return "registratieFormulier";
            }
        }
        return "errorTokenUitnodiging";
    }


    @PostMapping("/registreer/{token}")
    public String saveGebruiker(@PathVariable String token, Medewerker registratieFormulier, BindingResult result, Model model) {

        VerificatieToken verificatieToken = verificatieTokenRepository.findByToken(token);
        EmailMetToken emailMetToken = emailMetTokenRepository.findByVerificatieToken(verificatieToken);

        if (verificatieToken.getTokenGebruikt()) {
            return "errorTokenUitnodiging";
        }

        registratieFormulier.setGebruikersnaam(emailMetToken.getEmailadres());

        medewerkerValidator.validate(registratieFormulier, result);
        if (result.hasErrors()) {
            model.addAttribute("gebruikersnaam", emailMetToken.getEmailadres());
            return "registratieFormulier";
        }
        medewerkerService.save(registratieFormulier);
        securityService.autoLogin(registratieFormulier.getGebruikersnaam(), registratieFormulier.getWachtwoordBevestigen());
        registratieFormulier.setWachtwoordBevestigen("");


        verificatieToken.setTokenGebruikt(true);
        verificatieTokenRepository.save(verificatieToken);

        return "redirect:/";
    }


    @GetMapping("/login")
    public String login(Model model, String error, String logout) {

        if (error != null)
            model.addAttribute("error", "Uw gebruikersnaam en/of wachtwoord is ongeldig");
        if (logout != null)
            model.addAttribute("message", "U bent succesvol uitgelogd");
        return "login";
    }


    @GetMapping("/medewerker/rooster")
    public String welkomMedewerker(Model model, @AuthenticationPrincipal Medewerker ingelogdeMedewerker) {
        model.addAttribute("medewerker", medewerkerRepository.findByGebruikersnaam(ingelogdeMedewerker.getGebruikersnaam()));
        model.addAttribute("medewerkerProfielGegevens", medewerkerProfielGegevensRepository.findByMedewerker(ingelogdeMedewerker));

        List<Voorstelling> voorstellingList = voorstellingRepository.findAllByStatus("Gepubliceerd");
        List<VoorstellingsTaak> voorstellingsTaken = voorstellingsTaakRepository.findByMedewerkerMedewerkerId(ingelogdeMedewerker.getMedewerkerId());

        List<MedewerkerInschrijvingVoorstelling> medewerkerInschrijvingVoorstellingList =
                medewerkerInschrijvingVoorstellingRepository.findAllByMedewerkerMedewerkerId(ingelogdeMedewerker.getMedewerkerId());

        medewerkerInschrijvingVoorstellingList.forEach(medewerkerInschrijvingVoorstelling ->
                voorstellingList.removeIf(voorstelling -> medewerkerInschrijvingVoorstelling.getVoorstelling()
                        .getVoorstellingId().equals(voorstelling.getVoorstellingId())));

        Integer inTeVullenVoorstellingen = voorstellingList.size();

        model.addAttribute("inTevullenVoorstellingen", inTeVullenVoorstellingen);
        model.addAttribute("allePersoonlijkeVoorstellingsTaken", voorstellingsTaken);

        return "welkomMedewerker";
    }


    @GetMapping("/")
    public String doorverwijzenStartpagina(@AuthenticationPrincipal Medewerker medewerker) {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_ASPIRANT");

        if (medewerker.getAuthorities().contains(simpleGrantedAuthority)) {
            return "redirect:/profiel/wijzigen";

        } else {
            return "redirect:/medewerker/rooster";
        }
    }


    @GetMapping("/profiel")
    protected String showProfielPagina(Model model, @AuthenticationPrincipal Medewerker ingelogdeMedewerker) {
        model.addAttribute("medewerkerProfielGegevens", medewerkerProfielGegevensRepository.findByMedewerker(ingelogdeMedewerker));
        return "profielPagina";
    }


    @PostMapping("/profiel")
    protected String goToProfielWijzigen(Model model, @AuthenticationPrincipal Medewerker ingelogdeMedewerker) {
        return "redirect:/profiel/wijzigen";
    }


    @GetMapping("/profiel/wijzigen")
    protected String showProfielWijzigen(Model model, @AuthenticationPrincipal Medewerker ingelogdeMedewerker) {

        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_ASPIRANT");

        if (ingelogdeMedewerker.getAuthorities().contains(simpleGrantedAuthority)) {
            model.addAttribute("aspirant", simpleGrantedAuthority);
        }

        model.addAttribute("medewerkerProfielGegevens", medewerkerProfielGegevensRepository.findByMedewerker(ingelogdeMedewerker));
        model.addAttribute("takenLijst", taakRepository.findAll());

        return "profielWijzigen";
    }


    @PostMapping("/profiel/wijzigen")
    public String updateMedewerker(@ModelAttribute("medewerkerProfielGegevens") MedewerkerProfielGegevens medewerkerProfielGegevens,
                                   BindingResult result) {

        if (result.hasErrors()) {
            return "profielWijzigen";
        }

        if (medewerkerProfielGegevens.getLocalDate() == null) {
            medewerkerProfielGegevens.setGeboortedatum("");
        } else {
            medewerkerProfielGegevens.localDateFormatterenNaarString();
            medewerkerProfielGegevensRepository.save(medewerkerProfielGegevens);
        }

        Optional<Medewerker> medewerker = medewerkerRepository.findById(medewerkerProfielGegevens.getMedewerker().getMedewerkerId());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());

        medewerker.ifPresent(value -> updatedAuthorities.addAll(value.getAuthorities()));

        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(newAuth);

        return "redirect:/profiel";
    }


    @GetMapping("/planner/gebruiker/overzicht")
    public String gebruikerOverzicht(Model model, @AuthenticationPrincipal Medewerker ingelogdeMedwerker) {
        List<Medewerker> alleGebruikers = medewerkerRepository.findAll();
        alleGebruikers.removeIf(medewerker -> medewerker.getMedewerkerId().equals(ingelogdeMedwerker.getMedewerkerId()));
        model.addAttribute("alleGebruikers", alleGebruikers);
        model.addAttribute("emailMetToken", new EmailMetToken());
        return "gebruikerOverzicht";
    }


    @GetMapping("/planner/gebruiker/overzicht/verwijderen/{medewerkerId}")
    public String verwijderGebruiker(@PathVariable Integer medewerkerId) {
        medewerkerRepository.deleteById(medewerkerId);
        return "redirect:/planner/gebruiker/overzicht";
    }


    @GetMapping("/wachtwoord/reset")
    protected String wachtwoordResetPagina(Model model) {
        String emailadres = "";


        model.addAttribute("emailadres", emailadres);
        return "wachtwoordResetAanvragen";
    }


    @GetMapping("/wachtwoord/reset/{token}")
    protected String wachtwoordResetPaginaToken(@PathVariable String token, Model model) {
        VerificatieToken verificatieToken = verificatieTokenRepository.findByToken(token);

        model.addAttribute(new Medewerker());

        if (verificatieToken.getTokenGebruikt()) {
            return "errorTokenWachtwoordReset";
        }

        return "wachtwoordResetPagina";
    }


    @PostMapping("/wachtwoord/reset/{token}")
    public String wachtResetPaginaToken(@PathVariable String token, Medewerker medewerker, BindingResult bindingResult) {

        VerificatieToken verificatieToken = verificatieTokenRepository.findByToken(token);
        EmailMetToken emailMetToken = emailMetTokenRepository.findByVerificatieToken(verificatieToken);

        Medewerker medewerkerNieuwWachtwoord = medewerkerRepository.findByGebruikersnaam(emailMetToken.getEmailadres());

        medewerkerNieuwWachtwoord.setWachtwoord(medewerker.getWachtwoord());
        medewerkerNieuwWachtwoord.setWachtwoordBevestigen(medewerker.getWachtwoordBevestigen());

        if (verificatieToken.getTokenGebruikt()) {
            return "errorTokenUitnodiging";
        }

        medewerkerValidator.validateWachtwoord(medewerker, bindingResult);
        if (bindingResult.hasErrors()) {
            return "wachtwoordResetPagina";
        }
        medewerkerService.save(medewerkerNieuwWachtwoord);
        securityService.autoLogin(medewerkerNieuwWachtwoord.getGebruikersnaam(), medewerkerNieuwWachtwoord.getWachtwoordBevestigen());
        medewerkerNieuwWachtwoord.setWachtwoordBevestigen("");

        verificatieToken.setTokenGebruikt(true);
        verificatieTokenRepository.save(verificatieToken);

        return "redirect:/";
    }
}