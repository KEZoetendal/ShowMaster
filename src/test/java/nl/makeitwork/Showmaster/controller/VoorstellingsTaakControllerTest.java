package nl.makeitwork.Showmaster.controller;

import nl.makeitwork.Showmaster.model.*;
import nl.makeitwork.Showmaster.repository.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import javax.servlet.ServletContext;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;


/**
 * @author ******
 */

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
class VoorstellingsTaakControllerTest {

    @Autowired
    private VoorstellingRepository voorstellingRepository;

    @Autowired
    private TaakRepository taakRepository;

    @Autowired
    private VoorstellingsTaakRepository voorstellingsTaakRepository;

    @Autowired
    private MedewerkerInschrijvingVoorstellingRepository medewerkerInschrijvingVoorstellingRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MedewerkerInschrijvingVoorstellingController medewerkerInschrijvingVoorstellingController;

    @Autowired
    private MedewerkerRepository medewerkerRepository;

    @Autowired
    private VoorstellingsTaakController voorstellingsTaakController;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void beanTestVoorstellingsTaakController() {

        ServletContext servletContext = webApplicationContext.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(webApplicationContext.getBean("voorstellingsTaakController"));
    }

    @Test
    void toevoegenTaakAanVoorstelling() {

        //arrange
        Taak testTaak = new Taak();
        testTaak.setTaakNaam("Bar");
        testTaak.setStandaardBezetting(2);

        Voorstelling testVoorstelling = new Voorstelling();
        LocalDateTime datum = LocalDateTime.of(2020, Month.JANUARY, 18, 20, 30);
        testVoorstelling.setNaam("Soldaat van Oranje");
        testVoorstelling.setLocalDateTime(datum);

        //activate
        taakRepository.save(testTaak);
        voorstellingRepository.save(testVoorstelling);

        voorstellingsTaakController.toevoegenTaakAanVoorstelling(testTaak.getTaakId(), testVoorstelling.getVoorstellingId());

        List<VoorstellingsTaak> voorstellingsTaken = voorstellingsTaakRepository.
                findByVoorstellingVoorstellingIdOrderByTaakTaakNaam(testVoorstelling.getVoorstellingId());

        //assert
        Assert.assertNotNull(voorstellingsTaken);
    }

    @Test
    void verwijderenTaakBijVoorstelling() {

        //arrange
        Taak testTaak = new Taak();
        testTaak.setTaakNaam("Bar");
        testTaak.setStandaardBezetting(2);

        Voorstelling testVoorstelling = new Voorstelling();
        LocalDateTime datum = LocalDateTime.of(2020, Month.JANUARY, 18, 20, 30);
        testVoorstelling.setNaam("Soldaat van Oranje");
        testVoorstelling.setLocalDateTime(datum);

        //activate
        taakRepository.save(testTaak);
        voorstellingRepository.save(testVoorstelling);
        voorstellingsTaakController.toevoegenTaakAanVoorstelling(testTaak.getTaakId(), testVoorstelling.getVoorstellingId());

        List<VoorstellingsTaak> voorstellingsTaken = voorstellingsTaakRepository.
                findByVoorstellingVoorstellingIdOrderByTaakTaakNaam(testVoorstelling.getVoorstellingId());

        Assert.assertNotNull(voorstellingsTaken);
        VoorstellingsTaak testVoorstellingstaak = voorstellingsTaken.get(0);

        voorstellingsTaakController.verwijderenTaakBijVoorstelling(testVoorstellingstaak.getVoorstellingsTaakId(),
                testVoorstelling.getVoorstellingId());

        List<VoorstellingsTaak> voorstellingsTakenNaVerwijderen = voorstellingsTaakRepository.
                findByVoorstellingVoorstellingIdOrderByTaakTaakNaam(testVoorstelling.getVoorstellingId());
        //assert
        Assert.assertNotEquals(voorstellingsTaken.size(), voorstellingsTakenNaVerwijderen.size());
    }

    @Test
    void opslaanMedewerkerBijVoorstellingstaak() {

        //arrange
        VoorstellingsTaak testVoorstellingsTaak = new VoorstellingsTaak();

        Voorstelling testVoorstelling = new Voorstelling();
        LocalDateTime datum = LocalDateTime.of(2020, Month.FEBRUARY, 18, 20, 30);
        testVoorstelling.setNaam("Soldaat van Blauw");
        testVoorstelling.setLocalDateTime(datum);

        Medewerker testMedewerker = new Medewerker();
        testMedewerker.setGebruikersnaam("testMedewerker");
        testMedewerker.setWachtwoord("test12345");
        testMedewerker.setWachtwoordBevestigen("test12346");
        testMedewerker.setPlanner(false);

        //activate
        voorstellingsTaakRepository.save(testVoorstellingsTaak);
        voorstellingRepository.save(testVoorstelling);
        medewerkerRepository.save(testMedewerker);

        voorstellingsTaakController.opslaanMedewerkerBijVoorstellingstaak(testVoorstelling.getVoorstellingId(),
                testVoorstellingsTaak.getVoorstellingsTaakId(), testMedewerker.getMedewerkerId());

        List<VoorstellingsTaak> testVoorstellingsTaken = voorstellingsTaakRepository.
                findByVoorstellingVoorstellingIdOrderByTaakTaakNaam(testVoorstelling.getVoorstellingId());

        //assert
        for (VoorstellingsTaak v: testVoorstellingsTaken) {
            Assert.assertNotNull(v.getMedewerker());
        }
    }
}