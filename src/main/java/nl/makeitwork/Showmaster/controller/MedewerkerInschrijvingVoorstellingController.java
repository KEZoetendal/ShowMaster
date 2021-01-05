package nl.makeitwork.Showmaster.controller;

import nl.makeitwork.Showmaster.model.Medewerker;
import nl.makeitwork.Showmaster.model.MedewerkerInschrijvingVoorstelling;
import nl.makeitwork.Showmaster.model.Voorstelling;
import nl.makeitwork.Showmaster.repository.MedewerkerInschrijvingVoorstellingRepository;
import nl.makeitwork.Showmaster.repository.TaakRepository;
import nl.makeitwork.Showmaster.repository.VoorstellingRepository;
import nl.makeitwork.Showmaster.repository.VoorstellingsTaakRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author ****
 * deze klasse behandelt inschrijvingen van medewerkers op gepubliceerde voorstellingen
 */

@Controller
public class MedewerkerInschrijvingVoorstellingController {

    @Autowired
    private VoorstellingRepository voorstellingRepository;
    @Autowired
    private TaakRepository taakRepository;
    @Autowired
    private VoorstellingsTaakRepository voorstellingsTaakRepository;
    @Autowired
    private MedewerkerInschrijvingVoorstellingRepository medewerkerInschrijvingVoorstellingRepository;


    @GetMapping("/medewerker/rooster/openvoorstelling")
    public String openVoorstellingenOphalen(Model model, @AuthenticationPrincipal Medewerker ingelogdeMedewerker) {

        List<Voorstelling> voorstellingen = voorstellingRepository.findAll();

        List<MedewerkerInschrijvingVoorstelling> medewerkerInschrijvingVoorstellingList = medewerkerInschrijvingVoorstellingRepository.findAllByMedewerkerMedewerkerId(ingelogdeMedewerker.getMedewerkerId());

        medewerkerInschrijvingVoorstellingList.forEach(r -> voorstellingen.remove(r.getVoorstelling()));

        medewerkerInschrijvingVoorstellingList.forEach(r -> r.getVoorstelling().getNaam());

        model.addAttribute("inschrijvingen", medewerkerInschrijvingVoorstellingList);
        model.addAttribute("voorstellingLijst", voorstellingen);

        return "openVoorstellingen";
    }


    @GetMapping("/medewerker/rooster/openvoorstelling/inschrijven/{voorstellingId}/{inschrijvingStatus}")
    public String inschrijvenVoorstelling(@PathVariable Integer voorstellingId, @PathVariable String inschrijvingStatus, @AuthenticationPrincipal Medewerker ingelogdeMedewerker) {

        MedewerkerInschrijvingVoorstelling alIngeschrevenMedewerker = medewerkerInschrijvingVoorstellingRepository.findByVoorstellingVoorstellingIdAndMedewerkerMedewerkerId(voorstellingId, ingelogdeMedewerker.getMedewerkerId());

        if (inschrijvingStatus.matches("Beschikbaar|Misschien|Niet Beschikbaar")) {
            if (alIngeschrevenMedewerker != null) {
                alIngeschrevenMedewerker.setInschrijvingStatus(inschrijvingStatus);
                medewerkerInschrijvingVoorstellingRepository.save(alIngeschrevenMedewerker);
            } else {
                Voorstelling voorstelling = voorstellingRepository.findByVoorstellingId(voorstellingId);
                MedewerkerInschrijvingVoorstelling medewerkerInschrijvingVoorstelling =
                        new MedewerkerInschrijvingVoorstelling(ingelogdeMedewerker, voorstelling, inschrijvingStatus);
                medewerkerInschrijvingVoorstellingRepository.save(medewerkerInschrijvingVoorstelling);
            }
        }
        return "redirect:/medewerker/rooster/openvoorstelling";
    }
}