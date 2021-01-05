package nl.makeitwork.Showmaster.repository;

import nl.makeitwork.Showmaster.model.Taak;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Karin Zoetendal
 * 11-12-19: communiceert met de database over de klasse Taak
 */

@Repository
public interface TaakRepository extends JpaRepository<Taak, Integer> {

    Taak findByTaakNaam(String taakNaam);
}