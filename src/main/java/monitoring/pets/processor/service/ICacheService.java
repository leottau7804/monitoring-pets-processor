package monitoring.pets.processor.service;

import monitoring.pets.processor.model.communication.MetricRequest;
import monitoring.pets.processor.model.communication.RequestType;

/**
 *
 * @author sergioleottau
 */
public interface ICacheService {

    /**
     * 
     * @param requestType
     * @param metricRequest
     */    
    void saveCache(RequestType requestType, MetricRequest metricRequest);

}
