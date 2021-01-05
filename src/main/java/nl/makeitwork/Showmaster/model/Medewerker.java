package nl.makeitwork.Showmaster.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author ****
 */
@Entity
public class Medewerker implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer medewerkerId;

    @Column(unique = true)
    private String gebruikersnaam;

    private String wachtwoord;

    @Transient
    private String wachtwoordBevestigen;

    private Boolean planner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vasteTaakId", referencedColumnName = "taakId")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Taak vasteTaak;

    @OneToOne(mappedBy = "medewerker", cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private MedewerkerProfielGegevens medewerkerProfielGegevens;

    public Medewerker() {
        this.medewerkerProfielGegevens = new MedewerkerProfielGegevens();
        this.medewerkerProfielGegevens.setMedewerker(this);
    }


    protected boolean verplichtProfielCheck() {

        boolean verplichtProfiel = false;

        if (this.getMedewerkerProfielGegevens().getVoornaam() != null
                && this.getMedewerkerProfielGegevens().getAchternaam() != null
                && this.getMedewerkerProfielGegevens().getEmailadres() != null
                && this.getMedewerkerProfielGegevens().getGeboortedatum() != null
                && this.getMedewerkerProfielGegevens().getTelefoonnummer() != null) {
            verplichtProfiel = true;

        }
        return verplichtProfiel;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (!verplichtProfielCheck()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ASPIRANT"));
        } else if (this.planner) {
            authorities.add(new SimpleGrantedAuthority("ROLE_MEDEWERKER"));
            authorities.add(new SimpleGrantedAuthority("ROLE_PLANNER"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_MEDEWERKER"));
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.wachtwoord;
    }

    @Override
    public String getUsername() {
        return this.gebruikersnaam;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Boolean getPlanner() {
        return planner;
    }

    public MedewerkerProfielGegevens getMedewerkerProfielGegevens() {
        return medewerkerProfielGegevens;
    }

    public void setMedewerkerProfielGegevens(MedewerkerProfielGegevens medewerkerProfielGegevens) {
        this.medewerkerProfielGegevens = medewerkerProfielGegevens;
    }

    public void setPlanner(Boolean planner) {
        this.planner = planner;
    }

    public String getWachtwoordBevestigen() {
        return wachtwoordBevestigen;
    }

    public void setWachtwoordBevestigen(String wachtwoordBevestigen) {
        this.wachtwoordBevestigen = wachtwoordBevestigen;
    }

    public Integer getMedewerkerId() {
        return medewerkerId;
    }

    public void setMedewerkerId(Integer medewerkerId) {
        this.medewerkerId = medewerkerId;
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public Taak getVasteTaak() {
        return vasteTaak;
    }

    public void setVasteTaak(Taak vasteTaak) {
        this.vasteTaak = vasteTaak;
    }
}