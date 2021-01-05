package nl.makeitwork.Showmaster.controller;

import nl.makeitwork.Showmaster.model.Taak;
import nl.makeitwork.Showmaster.model.Voorstelling;
import nl.makeitwork.Showmaster.model.VoorstellingsTaak;
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
import java.util.*;

/**
 * @author Karin Zoetendal
 * 13-02-20: met deze klasse kan een standaardtaak worden aangemaakt, gewijzigd of verwijderd
 * Bij het aanpassen van standaardbezetting, of verwijderen van een standaardtaak, worden deze veranderingen ook
 * doorgevoerd bij ongepubliceerde voorstellingen
 */

@Controller
public class TaakController {

    @Autowired
    private TaakRepository taakRepository;
    @Autowired
    private VoorstellingRepository voorstellingRepository;
    @Autowired
    private VoorstellingsTaakRepository voorstellingsTaakRepository;
    @Autowired
    @Qualifier("voorstellingsTaakService")
    private VoorstellingsTaakService voorstellingsTaakService;

    @GetMapping("/planner/taak/beheer")
    protected String showAlleTaken(Model model) {
        // Alle voorstellingstaken bij ongepubliceerde voorstellingen ophalen
        List<VoorstellingsTaak> voorstellingsTaakBijGepubliceerdeVoorstelling = voorstellingsTaakRepository.findByVoorstellingStatus("Gepubliceerd");
        List<Taak> alleTaken = taakRepository.findAll();

        model.addAttribute("alleTaken", alleTaken);

        // Voor iedere voorstellingstaak de bijbehorende taak in een lijst zetten
        List<Taak> taakBijVoorstellingsTaak = new ArrayList<>();

        for (VoorstellingsTaak voorstellingstaak : voorstellingsTaakBijGepubliceerdeVoorstelling) {
            taakBijVoorstellingsTaak.add(voorstellingstaak.getTaak());
        }

        // HashMap aanmaken met alle taken met bijbehorende boolean, welke aangeeft of taak voorkomt in voorstellingstaken ongepubliceerde voorstellingen
        Map<Taak, Boolean> takenIngeplandBijGepubliceerdeVoorstellingen = new HashMap<>();

        for (Taak taak : alleTaken) {
            boolean ingeplandBijGepubliceerdeVoorstelling = taakBijVoorstellingsTaak.contains(taak);
            takenIngeplandBijGepubliceerdeVoorstellingen.put(taak, ingeplandBijGepubliceerdeVoorstelling);
        }
        model.addAttribute("takenIngeplandBijGepubliceerdeVoorstellingen", takenIngeplandBijGepubliceerdeVoorstellingen);

        return "taakBeheer";
    }


    @GetMapping("/planner/taak/aanmaken")
    protected String showTaakAanmaken(Taak taak) {
        return "taakAanmaken";
    }


    @PostMapping("/planner/taak/aanmaken")
    protected String saveOrUpdateTaak(Taak taak, BindingResult result) {
        if (!result.hasErrors()) {
            if (taak.getTaakNaam() != null && !taak.getTaakNaam().isEmpty() &&
                    taak.getStandaardBezetting() != null) {
                taakRepository.save(taak);

                for (Voorstelling ongepubliceerdeVoorstelling : voorstellingRepository.findAllByStatus("Ongepubliceerd")) {
                    voorstellingsTaakService.standaardTaakOpslaanBijVoorstelling(taak.getStandaardBezetting(), ongepubliceerdeVoorstelling, taak);
                }
            }
            return "redirect:/planner/taak/beheer";
        } else {
            return "taakAanmaken";
        }
    }


    @GetMapping("/planner/taak/wijzigen/{taakId}")
    protected String wijzigTaak(@PathVariable Integer taakId, Model model, HttpServletRequest request) {

        Optional<Taak> taak = taakRepository.findById(taakId);
        if (!taak.isPresent()) {
            return "redirect:/planner/taak/beheer";
        } else {
            request.getSession().setAttribute("taakId", taakId);
            model.addAttribute("taak", taak.get());
            return "wijzigTaak";
        }
    }


    @PostMapping("/planner/taak/wijzigen")
    protected String updateTaak(@ModelAttribute("taak") Taak taak, BindingResult result) {

        if (!result.hasErrors()) {

            taakRepository.save(taak);

            for (Voorstelling voorstelling : voorstellingRepository.findAllByStatus("Ongepubliceerd")) {

                Integer nieuweStandaardBezetting = taak.getStandaardBezetting();
                Integer aantalKeerIngeplandAlsVoorstellingsTaak =
                        voorstellingsTaakRepository.countByVoorstellingVoorstellingIdAndTaakTaakId(voorstelling.getVoorstellingId(), taak.getTaakId());

                if (nieuweStandaardBezetting > aantalKeerIngeplandAlsVoorstellingsTaak) {
                    voorstellingsTaakService.standaardTaakOpslaanBijVoorstelling((nieuweStandaardBezetting - aantalKeerIngeplandAlsVoorstellingsTaak), voorstelling, taak);
                } else if (nieuweStandaardBezetting < aantalKeerIngeplandAlsVoorstellingsTaak) {
                    voorstellingsTaakRepository.deleteByTaakAndVoorstelling(taak, voorstelling);
                    voorstellingsTaakService.standaardTaakOpslaanBijVoorstelling(nieuweStandaardBezetting, voorstelling, taak);
                }
            }
        } else {
            return "wijzigTaak";
        }
        return "redirect:/planner/taak/beheer";
    }


    @GetMapping("/planner/taak/verwijderen/{taakId}")
    public String verwijderStandaardTaak(@PathVariable Integer taakId) {
        voorstellingsTaakRepository.deleteByTaakTaakIdAndVoorstellingStatus(taakId, "Ongepubliceerd");
        taakRepository.deleteById(taakId);
        return "redirect:/planner/taak/beheer";
    }
}
