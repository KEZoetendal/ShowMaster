package nl.makeitwork.Showmaster.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * @author ****
 */

@Entity(name = "medewerkerInschrijvingVoorstelling")
@Table(name = "medewerker_inschrijving_voorstelling")
public class MedewerkerInschrijvingVoorstelling {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer medewerkerInschrijvingTaakId;

    @ManyToOne
    @JoinColumn(name = "medewerkerId", referencedColumnName = "medewerkerId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Medewerker medewerker;

    @ManyToOne
    @JoinColumn(name = "voorstellingId", referencedColumnName = "voorstellingId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Voorstelling voorstelling;

    private String inschrijvingStatus;

    public MedewerkerInschrijvingVoorstelling() {
    }

    public MedewerkerInschrijvingVoorstelling(Medewerker ingelogdeMedewerker, Voorstelling voorstelling, String inschrijvingStatus) {
        this.medewerker = ingelogdeMedewerker;
        this.voorstelling = voorstelling;
        this.inschrijvingStatus = inschrijvingStatus;
    }

    public Integer getMedewerkerInschrijvingTaakId() {
        return medewerkerInschrijvingTaakId;
    }

    public void setMedewerkerInschrijvingTaakId(Integer medewerkerInschrijvingTaakId) {
        this.medewerkerInschrijvingTaakId = medewerkerInschrijvingTaakId;
    }

    public String getInschrijvingStatus() {
        return inschrijvingStatus;
    }

    public void setInschrijvingStatus(String inschrijvingStatus) {
        this.inschrijvingStatus = inschrijvingStatus;
    }

    public Medewerker getMedewerker() {
        return medewerker;
    }

    public void setMedewerker(Medewerker medewerker) {
        this.medewerker = medewerker;
    }

    public Voorstelling getVoorstelling() {
        return voorstelling;
    }

    public void setVoorstelling(Voorstelling voorstelling) {
        this.voorstelling = voorstelling;
    }

    @Override
    public String toString() {
        return "MedewerkerInschrijvingVoorstelling{" +
                "medewerkerInschrijvingTaakId=" + medewerkerInschrijvingTaakId +
                ", medewerker=" + medewerker +
                ", voorstelling=" + voorstelling +
                '}';
    }
}