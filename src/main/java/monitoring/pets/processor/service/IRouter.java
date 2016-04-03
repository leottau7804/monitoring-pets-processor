package monitoring.pets.processor.service;

import java.util.List;
import monitoring.pets.processor.model.communication.MetricRequest;
import monitoring.pets.processor.model.communication.RequestType;

/**
 * Enrutador de mensajes
 *
 * @author sergioleottau
 */
public interface IRouter {

    /**
     * Enrutamiento de las metricas
     *
     * @param requestType tipo de metrica
     * @param metrics metricas a procesar
     */
    void route(RequestType requestType, List<MetricRequest> metrics);

}
