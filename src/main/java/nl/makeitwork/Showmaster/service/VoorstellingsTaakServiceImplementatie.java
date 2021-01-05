package nl.makeitwork.Showmaster.service;

import nl.makeitwork.Showmaster.model.Taak;
import nl.makeitwork.Showmaster.model.Voorstelling;
import nl.makeitwork.Showmaster.model.VoorstellingsTaak;
import nl.makeitwork.Showmaster.repository.TaakRepository;
import nl.makeitwork.Showmaster.repository.VoorstellingRepository;
import nl.makeitwork.Showmaster.repository.VoorstellingsTaakRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Karin Zoetendal
 * aangemaakt zodat onderstaande methodes vanuit meerdere controllers kunnen worden aangeroepen
 */

@Service("voorstellingsTaakService")
public class VoorstellingsTaakServiceImplementatie implements VoorstellingsTaakService {

    @Autowired
    private VoorstellingRepository voorstellingRepository;
    @Autowired
    private TaakRepository taakRepository;
    @Autowired
    private VoorstellingsTaakRepository voorstellingsTaakRepository;

    @Override
    public void voorstellingOpslaanInclTaken(Voorstelling voorstelling) {
        voorstelling.setStatus("Ongepubliceerd");

        voorstelling.localDateTimeFormatterenNaarString();

        voorstellingRepository.save(voorstelling);
        for (Taak taak : taakRepository.findAll()) {
            standaardTaakOpslaanBijVoorstelling(taak.getStandaardBezetting(), voorstelling, taak);
        }
    }

    @Override
    public void standaardTaakOpslaanBijVoorstelling(int taakAantal, Voorstelling voorstelling, Taak taak) {

        for (int i = 0; i < taakAantal; i++) {
            VoorstellingsTaak voorstellingsTaak = new VoorstellingsTaak();
            voorstellingsTaak.setTaak(taak);
            voorstellingsTaak.setVoorstelling(voorstelling);
            voorstellingsTaakRepository.save(voorstellingsTaak);
        }
    }

}