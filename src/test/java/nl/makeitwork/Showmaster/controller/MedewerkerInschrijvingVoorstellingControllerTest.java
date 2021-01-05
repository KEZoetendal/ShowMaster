package nl.makeitwork.Showmaster.controller;

import nl.makeitwork.Showmaster.model.Medewerker;
import nl.makeitwork.Showmaster.model.MedewerkerInschrijvingVoorstelling;
import nl.makeitwork.Showmaster.model.Voorstelling;
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
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.Mockito.mock;

/**
 * @author ****
 */

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
class MedewerkerInschrijvingVoorstellingControllerTest {

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


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void beanTestMedewerkerInschrijvingVoorstellingController() {
        //Arrange
        //Activate
        ServletContext servletContext = webApplicationContext.getServletContext();


        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(webApplicationContext.getBean("medewerkerInschrijvingVoorstellingController"));
    }

    @Test
    void openVoorstellingenOphalen() {

        //Arrange
        Model model = mock(Model.class);
        Medewerker medewerker = new Medewerker();

        //Assert
        Assert.assertEquals(medewerkerInschrijvingVoorstellingController.openVoorstellingenOphalen(model, medewerker), "openVoorstellingen");


    }

    @Test
    void inschrijvenVoorstelling() {
        // Arrange
        Voorstelling voorstelling1 = new Voorstelling();
        voorstelling1.setNaam("Lion King");
        voorstelling1.setLocalDateTime(LocalDateTime.of(2020, Month.JANUARY, 18, 20, 30));
        voorstellingRepository.save(voorstelling1);

        Voorstelling voorstelling2 = new Voorstelling();
        voorstelling1.setNaam("Lion King2");
        voorstelling1.setLocalDateTime(LocalDateTime.of(2020, Month.JANUARY, 18, 20, 30));
        voorstellingRepository.save(voorstelling1);

        Medewerker medewerker = new Medewerker();
        medewerker.setGebruikersnaam("Pieter");
        medewerkerRepository.save(medewerker);


        MedewerkerInschrijvingVoorstelling medewerkerInschrijvingVoorstelling = new MedewerkerInschrijvingVoorstelling();
        medewerkerInschrijvingVoorstelling.setMedewerker(medewerker);
        medewerkerInschrijvingVoorstelling.setVoorstelling(voorstelling1);


        //Activate
        //Er wordt met dezelfde voorstellingId en medewerker een inschrijving gedaan, dit zou maar 1x mogen gebeuren.
        medewerkerInschrijvingVoorstellingController.inschrijvenVoorstelling(voorstelling1.getVoorstellingId(), "Beschikbaar", medewerker);
        medewerkerInschrijvingVoorstellingController.inschrijvenVoorstelling(voorstelling1.getVoorstellingId(), "Misschien", medewerker);


        //Assert
        Assert.assertEquals(medewerkerInschrijvingVoorstellingController.inschrijvenVoorstelling(voorstelling2.getVoorstellingId(), "Beschikbaar", medewerker),
                "redirect:/medewerker/rooster/openvoorstelling");


        Assert.assertEquals(medewerkerInschrijvingVoorstellingRepository.findAll().size(), 2);

    }
}