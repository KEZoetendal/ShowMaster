package nl.makeitwork.Showmaster.service;

import nl.makeitwork.Showmaster.model.Taak;
import nl.makeitwork.Showmaster.model.Voorstelling;

/**
 * @author Karin Zoetendal
 */

public interface VoorstellingsTaakService {

    void voorstellingOpslaanInclTaken(Voorstelling voorstelling);

    void standaardTaakOpslaanBijVoorstelling(int taakAantal, Voorstelling voorstelling, Taak taak);
}