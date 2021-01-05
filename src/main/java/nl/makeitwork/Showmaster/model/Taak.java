package nl.makeitwork.Showmaster.model;

import javax.persistence.*;

/**
 * @author Karin Zoetendal
 * 11-12-19: Met deze klasse kun je een generieke taak aanmaken die -uitzonderingen daargelaten- voor elke voorstelling
 * geldt, met een standaard aantal personen dat voor die taak moet worden ingepland (standaard bezetting).
 * Voor showspecifieke extra taken zal later een apart veld moeten worden toegevoegd in de jsp voor voorstelling.
 */

@Entity
@Table(name = "taak")
public class Taak {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer taakId;
    private String taakNaam;
    private Integer standaardBezetting;

    @Override
    public String toString() {
        return "Taak{" +
                "taakId=" + taakId +
                ", taakNaam='" + taakNaam + '\'' +
                ", standaardBezetting=" + standaardBezetting +
                ", voorstellingsTaak=" +
                '}';
    }

    public Integer getTaakId() {
        return taakId;
    }

    public void setTaakId(Integer taakId) {
        this.taakId = taakId;
    }

    public String getTaakNaam() {
        return taakNaam;
    }

    public void setTaakNaam(String taakNaam) {
        this.taakNaam = taakNaam;
    }

    public Integer getStandaardBezetting() {
        return standaardBezetting;
    }

    public void setStandaardBezetting(Integer standaardBezetting) {
        this.standaardBezetting = standaardBezetting;
    }

}