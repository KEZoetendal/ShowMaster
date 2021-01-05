package nl.makeitwork.Showmaster.repository;

import nl.makeitwork.Showmaster.model.Voorstelling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ******
 * repo voor voorstellingenManagement
 */

@Repository
public interface VoorstellingRepository extends JpaRepository<Voorstelling, Integer> {

    List<Voorstelling> findAllByOrderByLocalDateTimeAsc();

    Voorstelling findByVoorstellingId(Integer voorstellingId);

    Voorstelling findByNaam(String naam);

    Voorstelling findByLocalDateTime(LocalDateTime localDateTime);

    List<Voorstelling> findAllByStatus(String status);
}
