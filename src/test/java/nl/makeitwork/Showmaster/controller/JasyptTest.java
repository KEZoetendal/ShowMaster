package nl.makeitwork.Showmaster.controller;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JasyptTest {

    @Autowired
    StringEncryptor jasyptStringEncryptor;

    @Value("${showmaster.sleutel}")
    private String sleutel;

    @Test
    public void jasyptWachtwoordTest(){
        // Stap 1
        // Geef deze variabele een waarde mee die encrypted moet worden.
        String password = "miwShowmaster2020!";
        // Stap 2
        // Run deze test, 'encryptieMetJasypt'.
        String encryptedPassword = jasyptStringEncryptor.encrypt(password);
        // Stap 3
        System.out.println("Kopieer de onderstaande regel vanuit de terminal.");
        System.out.println(encryptedPassword);
        // Stap 4
        // Haal de opgegeven waarde weer weg, voordat je commtit.
    }

    @Test
    public void decryptWachtwoord(){
        String verwachtResultaat = "Sleutel";
        Assert.assertEquals(verwachtResultaat,sleutel);
    }

}
