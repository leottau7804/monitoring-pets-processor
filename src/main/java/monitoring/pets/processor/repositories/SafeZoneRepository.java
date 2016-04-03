package monitoring.pets.processor.repositories;

import java.util.List;
import monitoring.pets.processor.model.MetricRange;
import monitoring.pets.processor.model.SafeZone;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio de zonas seguras
 *
 * @author sleottau
 */
public interface SafeZoneRepository extends CrudRepository<SafeZone, Long> {

    /**
     * Busqueda de zonas seguras
     *
     * @param idCollar identificacion del collar
     * @return
     */
    @Query(value = "SELECT s FROM SafeZone s WHERE s.pet.idCollar= ?1")
    List<SafeZone> findByIdCollar(String idCollar);
}
