package nl.makeitwork.Showmaster.controller;

import nl.makeitwork.Showmaster.model.Voorstelling;
import nl.makeitwork.Showmaster.repository.VoorstellingRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.time.Month;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * @author ******
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class VoorstellingControllerTest {

    @Autowired
    private VoorstellingController voorstellingController;

    @Autowired
    private VoorstellingRepository voorstellingRepository;

    @Test
    void saveorupdateVoorstelling() {

        //arrange
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);
        Voorstelling voorstelling = new Voorstelling();
        LocalDateTime datum = LocalDateTime.of(2020, Month.OCTOBER, 10, 20, 30);
        voorstelling.setNaam("Lion King");
        voorstelling.setLocalDateTime(datum);

        //activate
        voorstellingController.saveVoorstelling(voorstelling, result);

        //assert
        Assert.assertNotNull(voorstellingRepository.findById(voorstelling.getVoorstellingId()));
    }

    @Test
    void alleVoorstellingen() {
        Assert.assertNotNull(voorstellingController);

    }


    @Test
    void verwijderVoorstelling() {

        //arrange
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);
        Voorstelling voorstelling = new Voorstelling();
        LocalDateTime datum = LocalDateTime.of(2020, Month.JANUARY, 18, 20, 30);
        voorstelling.setNaam("Soldaat van Oranje");
        voorstelling.setLocalDateTime(datum);

        //activate
        voorstellingController.saveVoorstelling(voorstelling, result);
        voorstellingController.verwijderVoorstelling(voorstelling.getVoorstellingId());

        //assert
        Assert.assertFalse(voorstellingRepository.existsById(voorstelling.getVoorstellingId()));

    }
}