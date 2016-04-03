package monitoring.pets.processor.repositories;

import monitoring.pets.processor.model.Log;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio de log
 *
 * @author sleottau
 */
public interface LogRepository extends CrudRepository<Log, Long> {

}
