package monitoring.pets.processor.service;

import monitoring.pets.processor.model.Log;

/**
 *
 * @author sergioleottau
 */
public interface ILogService {

    /**
     * 
     * @param log 
     */
    void saveLog(Log log);

}
