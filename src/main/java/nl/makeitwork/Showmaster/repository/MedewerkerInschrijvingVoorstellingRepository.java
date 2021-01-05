package nl.makeitwork.Showmaster.repository;

import nl.makeitwork.Showmaster.model.MedewerkerInschrijvingVoorstelling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ****
 */

@Repository
public interface MedewerkerInschrijvingVoorstellingRepository extends JpaRepository<MedewerkerInschrijvingVoorstelling, Integer> {

    MedewerkerInschrijvingVoorstelling findByVoorstellingVoorstellingIdAndMedewerkerMedewerkerId(Integer voorstellingId, Integer medewerkerId);

    List<MedewerkerInschrijvingVoorstelling> findByVoorstellingVoorstellingIdAndInschrijvingStatus(Integer voorstellingId, String status);

    List<MedewerkerInschrijvingVoorstelling> findAllByMedewerkerMedewerkerId(Integer medewerkerId);

    List<MedewerkerInschrijvingVoorstelling> findByVoorstellingVoorstellingId(Integer voorstellingId);
}
