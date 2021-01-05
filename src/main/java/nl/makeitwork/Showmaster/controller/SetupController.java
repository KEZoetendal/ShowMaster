package nl.makeitwork.Showmaster.controller;

import nl.makeitwork.Showmaster.model.Medewerker;
import nl.makeitwork.Showmaster.model.MedewerkerInschrijvingVoorstelling;
import nl.makeitwork.Showmaster.model.Taak;
import nl.makeitwork.Showmaster.model.Voorstelling;
import nl.makeitwork.Showmaster.repository.*;
import nl.makeitwork.Showmaster.service.*;
import nl.makeitwork.Showmaster.validator.MedewerkerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

/**
 * @author Karin Zoetendal
 * Deze klasse zorgt voor een eerste setup na een create drop
 */
@Controller
public class SetupController {

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
    @Autowired
    @Qualifier("voorstellingsTaakService")
    private VoorstellingsTaakService voorstellingsTaakService;


    @GetMapping("/setup")
    protected String setupTakenVoorstellingenMedewerkersInschrijvingen() {

        Taak taak1 = new Taak();
        taak1.setTaakNaam("Bar");
        taak1.setStandaardBezetting(2);

        Taak taak2 = new Taak();
        taak2.setTaakNaam("Kaartverkoop");
        taak2.setStandaardBezetting(1);

        Taak taak4 = new Taak();
        taak4.setTaakNaam("Kaartcontrole");
        taak4.setStandaardBezetting(1);

        Taak taak3 = new Taak();
        taak3.setTaakNaam("Garderobe");
        taak3.setStandaardBezetting(2);

        taakRepository.save(taak1);
        taakRepository.save(taak2);
        taakRepository.save(taak3);
        taakRepository.save(taak4);

        Medewerker medewerker1 = new Medewerker();
        medewerker1.setGebruikersnaam("geert@test.com");
        medewerker1.setWachtwoord("test1234");
        medewerker1.setWachtwoordBevestigen("test1234");
        medewerker1.setPlanner(true);
        medewerker1.getMedewerkerProfielGegevens().setVoornaam("Geert");
        medewerker1.getMedewerkerProfielGegevens().setAchternaam("Vrieswijk");
        medewerker1.getMedewerkerProfielGegevens().setEmailadres(medewerker1.getGebruikersnaam());
        medewerker1.getMedewerkerProfielGegevens().setLocalDate(LocalDate.of(1979,11,12));
        medewerker1.getMedewerkerProfielGegevens().localDateFormatterenNaarString();
        medewerker1.getMedewerkerProfielGegevens().setTelefoonnummer("0612345678");
        medewerker1.getMedewerkerProfielGegevens().setVoorkeurstaak(taakRepository.findByTaakNaam("Bar"));

        Medewerker medewerker2 = new Medewerker();
        medewerker2.setGebruikersnaam("peter@test.com");
        medewerker2.setWachtwoord("test1234");
        medewerker2.setWachtwoordBevestigen("test1234");
        medewerker2.setPlanner(true);
        medewerker2.getMedewerkerProfielGegevens().setVoornaam("Peter");
        medewerker2.getMedewerkerProfielGegevens().setAchternaam("Bosma");
        medewerker2.getMedewerkerProfielGegevens().setEmailadres(medewerker2.getGebruikersnaam());
        medewerker2.getMedewerkerProfielGegevens().setLocalDate(LocalDate.of(1982,1,3));
        medewerker2.getMedewerkerProfielGegevens().localDateFormatterenNaarString();
        medewerker2.getMedewerkerProfielGegevens().setTelefoonnummer("0623456789");
        medewerker2.getMedewerkerProfielGegevens().setVoorkeurstaak(taakRepository.findByTaakNaam("Bar"));

        Medewerker medewerker3 = new Medewerker();
        medewerker3.setGebruikersnaam("karin@test.com");
        medewerker3.setWachtwoord("test1234");
        medewerker3.setWachtwoordBevestigen("test1234");
        medewerker3.setPlanner(true);
        medewerker3.getMedewerkerProfielGegevens().setVoornaam("Karin");
        medewerker3.getMedewerkerProfielGegevens().setAchternaam("Zoetendal");
        medewerker3.getMedewerkerProfielGegevens().setEmailadres(medewerker3.getGebruikersnaam());
        medewerker3.getMedewerkerProfielGegevens().setLocalDate(LocalDate.of(1981,1,19));
        medewerker3.getMedewerkerProfielGegevens().localDateFormatterenNaarString();
        medewerker3.getMedewerkerProfielGegevens().setTelefoonnummer("0634567891");
        medewerker3.getMedewerkerProfielGegevens().setVoorkeurstaak(taakRepository.findByTaakNaam("Bar"));

        Medewerker medewerker4 = new Medewerker();
        medewerker4.setGebruikersnaam("gerard@test.com");
        medewerker4.setWachtwoord("test1234");
        medewerker4.setWachtwoordBevestigen("test1234");
        medewerker4.setPlanner(false);
        medewerker4.getMedewerkerProfielGegevens().setVoornaam("Gerda");
        medewerker4.getMedewerkerProfielGegevens().setTussenvoegsel("de");
        medewerker4.getMedewerkerProfielGegevens().setAchternaam("Jong");
        medewerker4.getMedewerkerProfielGegevens().setVoorkeurstaak(taakRepository.findByTaakNaam("Kaartverkoop"));

        Medewerker medewerker5 = new Medewerker();
        medewerker5.setGebruikersnaam("kees@test.com");
        medewerker5.setWachtwoord("test1234");
        medewerker5.setWachtwoordBevestigen("test1234");
        medewerker5.setPlanner(false);
        medewerker5.getMedewerkerProfielGegevens().setVoornaam("Kees");
        medewerker5.getMedewerkerProfielGegevens().setTussenvoegsel("de");
        medewerker5.getMedewerkerProfielGegevens().setAchternaam("Vries");

        Medewerker medewerker6 = new Medewerker();
        medewerker6.setGebruikersnaam("piet@test.com");
        medewerker6.setWachtwoord("test1234");
        medewerker6.setWachtwoordBevestigen("test1234");
        medewerker6.setPlanner(false);
        medewerker6.getMedewerkerProfielGegevens().setVoornaam("Piet");
        medewerker6.getMedewerkerProfielGegevens().setAchternaam("Janssen");

        medewerkerService.save(medewerker1);
        medewerkerService.save(medewerker2);
        medewerkerService.save(medewerker3);
        medewerkerService.save(medewerker4);
        medewerkerService.save(medewerker5);
        medewerkerService.save(medewerker6);

        Voorstelling voorstelling1 = new Voorstelling();

        voorstelling1.setNaam("Lordi - + Almanac + Flesh Roxon");
        voorstelling1.setLocalDateTime(LocalDateTime.of(2021, Month.MARCH, 5, 21, 0));
        voorstelling1.setStatus("Gepubliceerd");

        DateTimeFormatter aFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm");
        LocalDateTime localDateTime = voorstelling1.getLocalDateTime();
        String formattedString = localDateTime.format(aFormatter);

        voorstelling1.setDatum(formattedString);

        voorstellingRepository.save(voorstelling1);

        for (Taak taak : taakRepository.findAll()) {
            voorstellingsTaakService.standaardTaakOpslaanBijVoorstelling(taak.getStandaardBezetting(), voorstelling1, taak);
        }

        Voorstelling voorstelling2 = new Voorstelling();

        voorstelling2.setNaam("Hit The North/ Diamond Head");
        voorstelling2.setLocalDateTime(LocalDateTime.of(2021, Month.MARCH, 6, 21, 0));
        voorstelling2.setStatus("Gepubliceerd");

        LocalDateTime localDateTime2 = voorstelling2.getLocalDateTime();
        String formattedString2 = localDateTime2.format(aFormatter);

        voorstelling2.setDatum(formattedString2);

        voorstellingRepository.save(voorstelling2);

        for (Taak taak : taakRepository.findAll()) {
            voorstellingsTaakService.standaardTaakOpslaanBijVoorstelling(taak.getStandaardBezetting(), voorstelling2, taak);
        }

        Voorstelling voorstelling3 = new Voorstelling();

        voorstelling3.setNaam("Comedy Club Sneek #6 - Knock Out Comedy Crew");
        voorstelling3.setLocalDateTime(LocalDateTime.of(2021, Month.MARCH, 11, 21, 0));
        voorstelling3.setStatus("Gepubliceerd");

        LocalDateTime localDateTime3 = voorstelling3.getLocalDateTime();
        String formattedString3 = localDateTime3.format(aFormatter);

        voorstelling3.setDatum(formattedString3);

        voorstellingRepository.save(voorstelling3);

        for (Taak taak : taakRepository.findAll()) {
            voorstellingsTaakService.standaardTaakOpslaanBijVoorstelling(taak.getStandaardBezetting(), voorstelling3, taak);
        }

        for (Medewerker medewerker :medewerkerRepository.findAll()) {
            MedewerkerInschrijvingVoorstelling medewerkerInschrijvingVoorstelling =
                    new MedewerkerInschrijvingVoorstelling(medewerker, voorstelling1, "Beschikbaar");
            medewerkerInschrijvingVoorstellingRepository.save(medewerkerInschrijvingVoorstelling);
        }

        MedewerkerInschrijvingVoorstelling medewerkerInschrijvingVoorstelling2 =
                new MedewerkerInschrijvingVoorstelling(medewerker1, voorstelling2, "Misschien");
        medewerkerInschrijvingVoorstellingRepository.save(medewerkerInschrijvingVoorstelling2);

        MedewerkerInschrijvingVoorstelling medewerkerInschrijvingVoorstelling3 =
                new MedewerkerInschrijvingVoorstelling(medewerker1, voorstelling3, "Niet Beschikbaar");
        medewerkerInschrijvingVoorstellingRepository.save(medewerkerInschrijvingVoorstelling3);

        return "redirect:/";
    }
}
