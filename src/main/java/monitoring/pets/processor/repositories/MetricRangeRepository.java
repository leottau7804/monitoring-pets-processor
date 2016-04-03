package monitoring.pets.processor.repositories;

import java.util.List;
import monitoring.pets.processor.model.MetricRange;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio de rangos de metricas
 *
 * @author sleottau
 */
public interface MetricRangeRepository extends CrudRepository<MetricRange, Long> {
    
    /**
     * Busqueda de los rangos de metricas a partir de un collar id
     * @param idCollar
     * @return 
     */
    @Query(value = "SELECT m FROM MetricRange m WHERE m.pet.idCollar= ?1")
    List<MetricRange> findByIdCollar(String idCollar);

}
