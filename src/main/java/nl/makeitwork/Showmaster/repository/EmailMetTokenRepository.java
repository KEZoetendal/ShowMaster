package nl.makeitwork.Showmaster.repository;

import nl.makeitwork.Showmaster.model.EmailMetToken;
import nl.makeitwork.Showmaster.model.VerificatieToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ******
 */

public interface EmailMetTokenRepository extends JpaRepository<EmailMetToken, Integer> {

    EmailMetToken findByVerificatieToken(VerificatieToken verificatieToken);

}