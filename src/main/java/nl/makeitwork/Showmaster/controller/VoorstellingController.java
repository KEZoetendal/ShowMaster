package nl.makeitwork.Showmaster.controller;

import nl.makeitwork.Showmaster.model.MedewerkerInschrijvingVoorstelling;
import nl.makeitwork.Showmaster.model.Voorstelling;
import nl.makeitwork.Showmaster.model.VoorstellingsTaak;
import nl.makeitwork.Showmaster.repository.MedewerkerInschrijvingVoorstellingRepository;
import nl.makeitwork.Showmaster.repository.TaakRepository;
import nl.makeitwork.Showmaster.repository.VoorstellingRepository;
import nl.makeitwork.Showmaster.repository.VoorstellingsTaakRepository;
import nl.makeitwork.Showmaster.service.VoorstellingsTaakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author ******
 * opgeslagen voorstellingen weergeven en een nieuwe voorstelling opslaan
 */

@Controller
public class VoorstellingController {

    @Autowired
    private VoorstellingRepository voorstellingRepository;
    @Autowired
    private TaakRepository taakRepository;
    @Autowired
    private VoorstellingsTaakRepository voorstellingsTaakRepository;
    @Autowired
    private VoorstellingController voorstellingController;
    @Autowired
    private MedewerkerInschrijvingVoorstellingRepository medewerkerInschrijvingVoorstellingRepository;
    @Autowired
    @Qualifier("voorstellingsTaakService")
    private VoorstellingsTaakService voorstellingsTaakService;


    @GetMapping("/planner/voorstellingen")
    protected String alleVoorstellingen(Model model) {

        model.addAttribute("alleVoorstellingen", voorstellingRepository
                .findAllByOrderByLocalDateTimeAsc());

        Map<Integer, Integer> openstaandeTaken = new HashMap<>();

        for (Voorstelling voorstelling : voorstellingRepository.findAll()) {
            openstaandeTaken.put(voorstelling.getVoorstellingId(),
                    voorstellingsTaakRepository.countByVoorstellingVoorstellingIdAndMedewerkerIsNull(voorstelling.getVoorstellingId()));
        }
        model.addAttribute("openstaandeTaken", openstaandeTaken);

        return "alleVoorstellingen";
    }


    @GetMapping("/planner/voorstellingen/voorstelling/toevoegen")
    protected String toevoegenVoorstellingen(Voorstelling voorstelling,
                                             Model model) {

        model.addAttribute("alleTaken",
                taakRepository.findAll());

        return "toevoegenVoorstelling";
    }


    @PostMapping("/planner/voorstellingen/voorstelling/toevoegen")
    protected String saveVoorstelling(@ModelAttribute("voorstelling")
                                              Voorstelling voorstelling,
                                      BindingResult result) {
        if (!result.hasErrors()) {
            voorstellingsTaakService.voorstellingOpslaanInclTaken(voorstelling);
        } else {
            return "toevoegenVoorstelling";
        }
        return "redirect:/planner/voorstellingen";
    }


    @GetMapping("/planner/voorstellingen/voorstelling/publiceren/{voorstellingId}")
    protected String publiceerVoorstelling(@PathVariable Integer voorstellingId) {
        Optional<Voorstelling> voorstelling = voorstellingRepository.findById(voorstellingId);

        voorstelling.ifPresent(value -> value.setStatus("Gepubliceerd"));
        voorstelling.ifPresent(value -> voorstellingRepository.save(value));

        return "redirect:/planner/voorstellingen";
    }


    @GetMapping("/planner/voorstellingen/voorstelling/wijzigen/{voorstellingId}")
    protected String wijzigenVoorstellingen(@PathVariable Integer voorstellingId, Model model, HttpServletRequest request) {

        Optional<Voorstelling> voorstelling = voorstellingRepository.findById(voorstellingId);
        model.addAttribute("alleTaken", taakRepository.findAll());
        if (!voorstelling.isPresent() || voorstelling.get().getStatus().equals("Geannuleerd")) {
            return "redirect:/planner/voorstellingen";
        } else {
            request.getSession().setAttribute("voorstellingId", voorstellingId);
            model.addAttribute("voorstelling", voorstelling.get());
            return "wijzigVoorstelling";
        }
    }


    @PostMapping("/planner/voorstellingen/voorstelling/wijzigen")
    protected String UpdateVoorstelling(@ModelAttribute("voorstelling") Voorstelling voorstelling, BindingResult result) {

        if (!result.hasErrors()) {
            voorstelling.localDateTimeFormatterenNaarString();

            voorstellingRepository.save(voorstelling);
        } else {
            return "wijzigVoorstelling";
        }
        return "redirect:/planner/voorstellingen";
    }


    @GetMapping("/planner/voorstellingen/voorstelling/annuleren/{voorstellingId}")
    protected String annuleerVoorstelling(@PathVariable Integer voorstellingId) {
        Optional<Voorstelling> voorstelling = voorstellingRepository.findById(voorstellingId);

        voorstelling.ifPresent(value -> value.setStatus("Geannuleerd"));
        voorstelling.ifPresent(value -> voorstellingRepository.save(value));

        return "redirect:/planner/voorstellingen";
    }


    @GetMapping("/planner/voorstellingen/voorstelling/rooster/{voorstellingId}")
    protected String roosterVoorstelling(@PathVariable Integer voorstellingId, Model model, HttpServletRequest request) {

        model.addAttribute("alleTaken", taakRepository.findAll());
        Optional<Voorstelling> voorstelling = voorstellingRepository.findById(voorstellingId);

        // Lijst alle inschrijvingen op één voorstelling met status beschikbaar
        List<MedewerkerInschrijvingVoorstelling> inschrijvingenBijVoorstellingId =
                medewerkerInschrijvingVoorstellingRepository.findByVoorstellingVoorstellingIdAndInschrijvingStatus(voorstellingId, "Beschikbaar");

        // Lijst alle taken bij één voorstelling
        List<VoorstellingsTaak> alleVoorstellingsTakenBijVoorstellingId =
                voorstellingsTaakRepository.findByVoorstellingVoorstellingIdOrderByTaakTaakNaam(voorstellingId);

        // Reeds ingevulde taken filteren om alle nog beschikbare medewerkers te kunnen laten zien
        alleVoorstellingsTakenBijVoorstellingId.forEach
                (d -> inschrijvingenBijVoorstellingId.removeIf(r -> r.getMedewerker() == d.getMedewerker()));

        List<VoorstellingsTaak> voorstellingsTaken = voorstellingsTaakRepository.findByVoorstellingVoorstellingIdOrderByTaakTaakNaam(voorstellingId);

        if (!voorstelling.isPresent() || voorstelling.get().getStatus().equals("Geannuleerd")) {
            return "redirect:/planner/voorstellingen";
        } else {
            request.getSession().setAttribute("voorstellingId", voorstellingId);
            model.addAttribute("takenBijVoorstelling", voorstellingsTaken);
            model.addAttribute("voorstelling", voorstelling.get());
            model.addAttribute("beschikbareMedewerkers", inschrijvingenBijVoorstellingId);
            return "roosterVoorstelling";
        }
    }


    @GetMapping("/rooster/voorstelling/{voorstellingId}")
    protected String roosterVoorstelling(@PathVariable Integer voorstellingId, Model model) {

        List<VoorstellingsTaak> voorstellingOverzicht =
                voorstellingsTaakRepository.findByVoorstellingVoorstellingIdAndMedewerkerIsNotNull(voorstellingId);

        Voorstelling voorstelling = voorstellingRepository.findByVoorstellingId(voorstellingId);

        model.addAttribute("voorstellingOverzicht", voorstellingOverzicht);
        model.addAttribute("voorstelling", voorstelling);

        return "ingeplandeMedewerkersBijVoorstelling";
    }


    @GetMapping("/planner/voorstellingen/voorstelling/verwijderen/{voorstellingId}")
    protected String verwijderVoorstelling(@PathVariable Integer voorstellingId) {
        voorstellingRepository.deleteById(voorstellingId);
        return "redirect:/planner/voorstellingen";
    }
}