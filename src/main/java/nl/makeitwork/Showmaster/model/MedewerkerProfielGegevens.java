package nl.makeitwork.Showmaster.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author ****
 * in deze klasse staan alle NAW gegevens, geboortedatum en voorkeurstaak
 */

@Entity
public class MedewerkerProfielGegevens {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer profielId;

    private String voornaam;

    private String tussenvoegsel;

    private String achternaam;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate localDate;

    private String geboortedatum;

    private String straatnaam;

    private Integer huisnummer;

    private String toevoeging;

    private String postcode;

    private String woonplaats;

    private String emailadres;

    private String telefoonnummer;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medewerker_id")
    private Medewerker medewerker;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "voorkeurstaakId", referencedColumnName = "taakId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Taak voorkeurstaak;

    public void localDateFormatterenNaarString() {
        DateTimeFormatter aFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        LocalDate localDate = getLocalDate();
        String formattedString = localDate.format(aFormatter);

        setGeboortedatum(formattedString);
    }

    public Integer getProfielId() {
        return profielId;
    }

    public void setProfielId(Integer profielId) {
        this.profielId = profielId;
    }

    public Medewerker getMedewerker() {
        return medewerker;
    }

    public void setMedewerker(Medewerker medewerker) {
        this.medewerker = medewerker;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(String geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public String getStraatnaam() {
        return straatnaam;
    }

    public void setStraatnaam(String straatnaam) {
        this.straatnaam = straatnaam;
    }

    public Integer getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(Integer huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getToevoeging() {
        return toevoeging;
    }

    public void setToevoeging(String toevoeging) {
        this.toevoeging = toevoeging;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public String getEmailadres() {
        return emailadres;
    }

    public void setEmailadres(String emailadres) {
        this.emailadres = emailadres;
    }

    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public Taak getVoorkeurstaak() {
        return voorkeurstaak;
    }

    public void setVoorkeurstaak(Taak voorkeurstaak) {
        this.voorkeurstaak = voorkeurstaak;
    }
}