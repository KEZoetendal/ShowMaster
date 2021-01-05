package nl.makeitwork.Showmaster.controller;

import nl.makeitwork.Showmaster.model.EmailMetToken;
import nl.makeitwork.Showmaster.model.Medewerker;
import nl.makeitwork.Showmaster.model.MedewerkerProfielGegevens;
import nl.makeitwork.Showmaster.model.VerificatieToken;
import nl.makeitwork.Showmaster.repository.EmailMetTokenRepository;
import nl.makeitwork.Showmaster.repository.MedewerkerProfielGegevensRepository;
import nl.makeitwork.Showmaster.repository.MedewerkerRepository;
import nl.makeitwork.Showmaster.repository.VerificatieTokenRepository;
import nl.makeitwork.Showmaster.service.MedewerkerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author ****
 */

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
class MedewerkerControllerTest {

    @Autowired
    private MedewerkerService medewerkerService;

    @Autowired
    private MedewerkerController medewerkerController;

    @Autowired
    private MedewerkerRepository medewerkerRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MedewerkerProfielGegevensRepository medewerkerProfielGegevensRepository;

    @Autowired
    VerificatieTokenRepository verificatieTokenRepository;

    @Autowired
    EmailMetTokenRepository emailMetTokenRepository;


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

    }

    @Test
    public void beanTestmedewerkerController() {
        //Arrange
        //Activate
        ServletContext servletContext = webApplicationContext.getServletContext();


        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(webApplicationContext.getBean("medewerkerController"));
    }

    @Test
    public void saveGebruikerTest() {

        //Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        Medewerker medewerker1 = new Medewerker();

        medewerker1.setGebruikersnaam("test1234@test.com");
        medewerker1.setWachtwoord("test1234");
        medewerker1.setWachtwoordBevestigen("test1234");
        medewerker1.setPlanner(false);
        VerificatieToken verificatieToken = new VerificatieToken();
        verificatieTokenRepository.save(verificatieToken);

        EmailMetToken emailMetToken = new EmailMetToken();
        emailMetToken.setEmailadres("test1234@test.com");
        emailMetToken.setVerificatieToken(verificatieToken);
        emailMetTokenRepository.save(emailMetToken);


        //Activate
        medewerkerController.saveGebruiker(verificatieToken.getToken(), medewerker1, bindingResult, null);

        //Assert
        Assert.assertNotNull(medewerkerRepository.findByGebruikersnaam("test1234@test.com"));
    }

    @Test
    void testUpdateMedewerker() {

        // Arrange
        Medewerker testMedewerker1 = new Medewerker();
        setGebruikersgegevensTestMedewerker1(testMedewerker1);
        medewerkerRepository.save(testMedewerker1);

        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);
        String verwachteAchternaam = "Vries";

        MedewerkerProfielGegevens profielGegevensTestMedewerker1 = (medewerkerProfielGegevensRepository.findByMedewerker(testMedewerker1));

        vulProfielgegevens(profielGegevensTestMedewerker1);

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ASPIRANT"));
        Authentication authToken = new UsernamePasswordAuthenticationToken(testMedewerker1.getGebruikersnaam(), testMedewerker1.getWachtwoord(), authorities);
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // Activate
        medewerkerController.updateMedewerker(profielGegevensTestMedewerker1, result);

        // Assert
        Assert.assertEquals(profielGegevensTestMedewerker1.getAchternaam(), verwachteAchternaam);
    }


    @Test
    public void verwijderGebruikerTest() {
        //Arrange
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(false);
        Medewerker medewerker1 = new Medewerker();

        medewerker1.setGebruikersnaam("test1234@test.com");
        medewerker1.setWachtwoord("test12345");
        medewerker1.setWachtwoordBevestigen("test12345");
        medewerker1.setPlanner(false);
        VerificatieToken verificatieToken = new VerificatieToken();
        verificatieTokenRepository.save(verificatieToken);

        EmailMetToken emailMetToken = new EmailMetToken();
        emailMetToken.setEmailadres("test1234@test.com");
        emailMetToken.setVerificatieToken(verificatieToken);
        emailMetTokenRepository.save(emailMetToken);

        //Activate
        medewerkerController.saveGebruiker(verificatieToken.getToken(), medewerker1, bindingResult, null);

        medewerker1 = medewerkerRepository.findByGebruikersnaam("test1234@test.com");

        medewerkerController.verwijderGebruiker(medewerker1.getMedewerkerId());

        //Assert
        Assert.assertNull(medewerkerRepository.findByGebruikersnaam("test1234@test.com"));

    }

    public void setGebruikersgegevensTestMedewerker1(Medewerker testMedewerker) {
        testMedewerker.setGebruikersnaam("test4567");
        testMedewerker.setWachtwoord("test4567");
        testMedewerker.setWachtwoordBevestigen("test4567");
        testMedewerker.setPlanner(false);
    }

    public void vulProfielgegevens(MedewerkerProfielGegevens opgehaaldeMedewerker) {
        opgehaaldeMedewerker.setVoornaam("Piet");
        opgehaaldeMedewerker.setTussenvoegsel("de");
        opgehaaldeMedewerker.setAchternaam("Vries");
        opgehaaldeMedewerker.setEmailadres("pdevries@blabla.com");
        //updateMedewerker.setGeboortedatum(LocalDate.parse("1956-8-1"));
        opgehaaldeMedewerker.setStraatnaam("Rondweg");
        opgehaaldeMedewerker.setHuisnummer(2);
        opgehaaldeMedewerker.setPostcode("8607HH");
        opgehaaldeMedewerker.setWoonplaats("Putten");
        opgehaaldeMedewerker.setTelefoonnummer("06-84431841");
    }

}