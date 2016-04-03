package monitoring.pets.processor.repositories;

import monitoring.pets.processor.model.Pet;
import org.springframework.data.repository.CrudRepository;

/**
 * Repositorio de mascotas
 *
 * @author sleottau
 */
public interface PetRepository extends CrudRepository<Pet, Long> {

}
