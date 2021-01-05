package nl.makeitwork.Showmaster.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

/**
 * @author ******
 * in voorstellingsTaken komen de andere entiteiten samen
 */

@Entity(name = "voorstellingsTaak")
@Table(name = "voorstelling_heeft_taak")
public class VoorstellingsTaak {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer voorstellingsTaakId;

    @ManyToOne
    @JoinColumn(name = "voorstellingId", referencedColumnName = "voorstellingId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Voorstelling voorstelling;

    @ManyToOne
    @JoinColumn(name = "taakId", referencedColumnName = "taakId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Taak taak;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medewerkerId", referencedColumnName = "medewerkerId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Medewerker medewerker;

    public Voorstelling getVoorstelling() {
        return voorstelling;
    }

    public void setVoorstelling(Voorstelling voorstelling) {
        this.voorstelling = voorstelling;
    }

    public Taak getTaak() {
        return taak;
    }

    public void setTaak(Taak taak) {
        this.taak = taak;
    }

    public Integer getVoorstellingsTaakId() {
        return voorstellingsTaakId;
    }

    public void setVoorstellingsTaakId(Integer voorstellingsTaakId) {
        this.voorstellingsTaakId = voorstellingsTaakId;
    }

    public Medewerker getMedewerker() {
        return medewerker;
    }

    public void setMedewerker(Medewerker medewerker) {
        this.medewerker = medewerker;
    }

    @Override
    public String toString() {
        return "VoorstellingsTaak{" +
                "voorstellingsTaakId=" + voorstellingsTaakId +
                ", voorstelling=" + voorstelling +
                ", taak=" + taak +
                ", medewerker=" + medewerker +
                '}';
    }
}