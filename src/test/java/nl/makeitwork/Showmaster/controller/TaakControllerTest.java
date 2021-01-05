package nl.makeitwork.Showmaster.controller;

import nl.makeitwork.Showmaster.model.Taak;
import nl.makeitwork.Showmaster.repository.TaakRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindingResult;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * @author Karin Zoetendal
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class TaakControllerTest {


    @Autowired
    TaakController taakController;

    @Autowired
    TaakRepository taakRepository;

    @Test
    public void testShowTaakAanmaken() {
        Assert.assertNotNull(taakController);
    }

    @Test
    public void testSaveOrUpdateTaak() {
        //Arrange
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);
        Taak testTaak = new Taak();
        testTaak.setTaakNaam("Bar");
        testTaak.setStandaardBezetting(2);

        //activate
        taakController.saveOrUpdateTaak(testTaak, result);

        Optional<Taak> opgehaaldeTaak = taakRepository.findById(testTaak.getTaakId());

        //assert
        Assert.assertNotNull(opgehaaldeTaak);

    }

    @Test
    void testVerwijderStandaardTaak() {

        //arrange
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);
        Taak testTaak2 = new Taak();
        testTaak2.setTaakNaam("Bar");
        testTaak2.setStandaardBezetting(2);

        //activate
        taakController.saveOrUpdateTaak(testTaak2, result);
        taakController.verwijderStandaardTaak(testTaak2.getTaakId());

        //assert
        Assert.assertFalse(taakRepository.existsById(testTaak2.getTaakId()));

    }
}