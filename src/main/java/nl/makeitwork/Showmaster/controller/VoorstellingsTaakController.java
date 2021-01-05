package nl.makeitwork.Showmaster.controller;

import nl.makeitwork.Showmaster.model.*;
import nl.makeitwork.Showmaster.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

/**
 * @author ******
 * beheren voorstellingsTaken
 */

@Controller
public class VoorstellingsTaakController {

    @Autowired
    private VoorstellingRepository voorstellingRepository;
    @Autowired
    private TaakRepository taakRepository;
    @Autowired
    private VoorstellingsTaakRepository voorstellingsTaakRepository;
    @Autowired
    private MedewerkerInschrijvingVoorstellingRepository medewerkerInschrijvingVoorstellingRepository;
    @Autowired
    private MedewerkerRepository medewerkerRepository;
    @Autowired
    private VoorstellingsTaakController voorstellingsTaakController;

    @GetMapping("/planner/voorstellingen/voorstellingsTaak/verwijderen/{voorstellingId}/{voorstellingsTaakId}")
    protected String verwijderenTaakBijVoorstelling(@PathVariable("voorstellingsTaakId") Integer voorstellingsTaakId,
                                                    @PathVariable("voorstellingId") Integer voorstellingId) {

        Optional<VoorstellingsTaak> voorstellingsTaak = voorstellingsTaakRepository.findById(voorstellingsTaakId);
        voorstellingsTaakRepository.deleteById(voorstellingsTaakId);

        return "redirect:/planner/voorstellingen/voorstelling/rooster/" + voorstellingId;
    }


    @GetMapping("/planner/voorstellingen/voorstellingsTaak/toevoegen/{voorstellingId}/{taakId}")
    protected String toevoegenTaakAanVoorstelling(@PathVariable("taakId") Integer taakId,
                                                  @PathVariable("voorstellingId") Integer voorstellingId) {

        Optional<Voorstelling> voorstelling = voorstellingRepository.findById(voorstellingId);
        Optional<Taak> taak = taakRepository.findById(taakId);

        VoorstellingsTaak voorstellingsTaak = new VoorstellingsTaak();

        voorstelling.ifPresent(voorstellingsTaak::setVoorstelling);
        taak.ifPresent(voorstellingsTaak::setTaak);

        voorstellingsTaakRepository.save(voorstellingsTaak);

        return "redirect:/planner/voorstellingen/voorstelling/rooster/" + voorstellingId;
    }


    @GetMapping("/planner/voorstellingen/voorstellingsTaak/medewerkerKoppelen/{voorstellingId}/{voorstellingsTaakId}")
    protected String koppelenMedewerkerAanVoorstellingsTaak(@PathVariable("voorstellingId") Integer voorstellingId,
                                                            @PathVariable("voorstellingsTaakId") Integer voorstellingsTaakId,
                                                            Model model) {

        Optional<VoorstellingsTaak> voorstellingsTaak = voorstellingsTaakRepository.findById(voorstellingsTaakId);
        Optional<Voorstelling> voorstelling = voorstellingRepository.findById(voorstellingId);

        List<MedewerkerInschrijvingVoorstelling> inschrijvingenBijVoorstellingId =
                medewerkerInschrijvingVoorstellingRepository.findByVoorstellingVoorstellingId(voorstellingId);

        List<VoorstellingsTaak> alleVoorstellingsTakenBijVoorstellingId =
                voorstellingsTaakRepository.findByVoorstellingVoorstellingIdOrderByTaakTaakNaam(voorstellingId);

        // Reeds ingevulde taken filteren om alle nog beschikbare medewerkers te kunnen laten zien
        alleVoorstellingsTakenBijVoorstellingId.forEach
                (d -> inschrijvingenBijVoorstellingId.removeIf(r -> r.getMedewerker() == d.getMedewerker()));

        voorstellingsTaak.ifPresent(taak -> model.addAttribute("voorstellingsTaak", taak));
        model.addAttribute("voorstellingId", voorstellingId);
        model.addAttribute("beschikbareMedewerkers", inschrijvingenBijVoorstellingId);
        voorstellingsTaak.ifPresent(taak -> model.addAttribute("taak", taak.getTaak().getTaakNaam()));
        voorstelling.ifPresent(value -> model.addAttribute("voorstelling", value.getNaam()));

        return "medewerkerKoppelenAanVoorstellingsTaak";
    }


    @GetMapping("/planner/voorstellingen/voorstellingsTaak/medewerkerKoppelen/{voorstellingId}/{voorstellingsTaakId}/{medewerkerId}")
    protected String opslaanMedewerkerBijVoorstellingstaak(@PathVariable("voorstellingId") Integer voorstellingId,
                                                           @PathVariable("voorstellingsTaakId") Integer voorstellingsTaakId,
                                                           @PathVariable("medewerkerId") Integer medewerkerId) {

        Optional<VoorstellingsTaak> voorstellingsTaak = voorstellingsTaakRepository.findById(voorstellingsTaakId);
        Optional<Medewerker> medewerker = medewerkerRepository.findById(medewerkerId);

        List<VoorstellingsTaak> takenBijVoorstelling = voorstellingsTaakRepository.findByVoorstellingVoorstellingId(voorstellingId);
        for (VoorstellingsTaak taak : takenBijVoorstelling) {
            if (taak.getMedewerker() != null) {
                if (taak.getMedewerker().getMedewerkerId().equals(medewerkerId)) {
                    taak.setMedewerker(null);
                    voorstellingsTaakRepository.save(taak);
                }
            }
        }

        if (voorstellingsTaak.isPresent() && medewerker.isPresent()) {
            voorstellingsTaak.get().setMedewerker(medewerker.get());
            voorstellingsTaakRepository.save(voorstellingsTaak.get());
        }
        return "redirect:/planner/voorstellingen/voorstelling/rooster/" + voorstellingId;
    }


    @GetMapping("/planner/voorstellingen/voorstellingsTaak/taakVrijGeven/{voorstellingId}/{voorstellingsTaakId}")
    protected String taakBijVoorstellingenVrijgeven(@PathVariable("voorstellingId") Integer voorstellingId,
                                                    @PathVariable("voorstellingsTaakId") Integer voorstellingsTaakId) {

        VoorstellingsTaak voorstellingsTaak = voorstellingsTaakRepository.findByVoorstellingsTaakId(voorstellingsTaakId);

        if (voorstellingsTaak.getMedewerker() != null) {
            voorstellingsTaak.setMedewerker(null);
            voorstellingsTaakRepository.save(voorstellingsTaak);
        }
        return "redirect:/planner/voorstellingen/voorstelling/rooster/" + voorstellingId;
    }


    @GetMapping("/planner/voorstellingen/voorstelling/rooster/genereer/{voorstellingId}")
    protected String genereerRooster(@PathVariable("voorstellingId") Integer voorstellingId) {

        List<Taak> alleTaken = taakRepository.findAll();

        List<MedewerkerInschrijvingVoorstelling> inschrijvingVoorstelling = medewerkerInschrijvingVoorstellingRepository.
                findByVoorstellingVoorstellingIdAndInschrijvingStatus(voorstellingId, "Beschikbaar");
        Collections.shuffle(inschrijvingVoorstelling);

        List<VoorstellingsTaak> voorstellingsTaken = voorstellingsTaakRepository.findByVoorstellingVoorstellingId(voorstellingId);
        Collections.shuffle(voorstellingsTaken);

        voorstellingsTaken
                .stream()
                .filter(x -> x.getMedewerker() != null)
                .forEach(x -> x.setMedewerker(null));

        for (Taak taak : alleTaken) {
            voorstellingsTaakController.genereerRoosterMetVoorkeursTaak(taak.getTaakNaam(),
                    inschrijvingVoorstelling,
                    voorstellingsTaken);
        }
        return "redirect:/planner/voorstellingen/voorstelling/rooster/" + voorstellingId;
    }


    protected void genereerRoosterMetVoorkeursTaak(String voorkeursTaak,
                                                   List<MedewerkerInschrijvingVoorstelling> inschrijvingVoorstelling,
                                                   List<VoorstellingsTaak> voorstellingsTaken) {

        List<MedewerkerInschrijvingVoorstelling> inschrijvingVoorstellingVoorkeur = new ArrayList<MedewerkerInschrijvingVoorstelling>() {
        };
        List<VoorstellingsTaak> voorstellingsTaakVoorkeur = new ArrayList<VoorstellingsTaak>() {
        };

        voorstellingsTaken
                .stream()
                .filter(x -> x.getTaak().getTaakNaam().equals(voorkeursTaak))
                .forEach(voorstellingsTaakVoorkeur::add);

        inschrijvingVoorstelling
                .stream()
                .filter(y -> y.getMedewerker().getMedewerkerProfielGegevens().getVoorkeurstaak() != null)
                .filter(z -> z.getMedewerker().getMedewerkerProfielGegevens().getVoorkeurstaak().getTaakNaam().equals(voorkeursTaak))
                .forEach(inschrijvingVoorstellingVoorkeur::add);

        for (int i = 0; i < inschrijvingVoorstellingVoorkeur.size(); i++) {
            if (i < voorstellingsTaakVoorkeur.size()) {
                if (voorstellingsTaakVoorkeur.get(i) != null) {
                    voorstellingsTaakVoorkeur.get(i).setMedewerker(inschrijvingVoorstellingVoorkeur.get(i).getMedewerker());
                    voorstellingsTaakRepository.save(voorstellingsTaken.get(i));
                }
            } else {
                break;
            }
        }
    }
}