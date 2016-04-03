package monitoring.pets.processor.service.impl;

import monitoring.pets.processor.model.Log;
import monitoring.pets.processor.repositories.LogRepository;
import monitoring.pets.processor.service.ILogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Servicio para la gestion de logs.
 *
 * @author sergioleottau
 */
@Service
public class LogService implements ILogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogService.class);

    /**
     * Repositorio de logs.
     */
    @Autowired
    private LogRepository logRepository;

    /**
     * Método encargado del almacemiento de logs.
     *
     * @param log informacion que se desea almacenar.
     */
    @Async
    @Override
    public void saveLog(Log log) {

        try {
            logRepository.save(log);
        } catch (Exception e) {
            LOGGER.error("Error trying to save a log [{}]", log, e);
        }

    }

}
