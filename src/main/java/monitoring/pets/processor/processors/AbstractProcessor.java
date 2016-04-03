package monitoring.pets.processor.processors;

import java.util.concurrent.Callable;
import monitoring.pets.processor.model.communication.MetricRequest;
import monitoring.pets.processor.model.communication.RequestType;
import monitoring.pets.processor.service.ICacheService;
import monitoring.pets.processor.service.IProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Interfaz de procesamiento
 *
 * @author sergioleottau
 * @param <T> tipo de solicitud de procesamiento
 */
public abstract class AbstractProcessor<T extends MetricRequest> implements Callable<Boolean> {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractProcessor.class);
    /**
     * Cantidad maxima de intentos
     */
    private static final int MAX_ATTEMPT_AMOUNT = 5;
    /**
     * Servicio para la gestion del cache
     */
    @Autowired
    private ICacheService cacheService;
    /**
     * Productor de mensajes.
     */
    @Autowired
    private IProducerService producerService;

    /**
     * Indica la solicitud a procesar.
     *
     * @param request solicitud de procesamiento
     */
    public abstract void setRequest(T request);

    /**
     * Realiza el manejo de los resultados
     *
     * @param successful
     * @param request
     * @param requestType
     */
    public void resultsManagement(boolean successful, T request, RequestType requestType) {
        if (!successful) {

            if (request.getAttempts() != null && request.getAttempts() >= MAX_ATTEMPT_AMOUNT) {
                LOGGER.error("Max processing attempt for the metric [{}]...", request);
            } else {
                LOGGER.error("Fail process for [{}] metric queue to retry later", request);
                request.incrementAttempt();
                producerService.send(requestType.getTopicEnum(), request);
            }
        } else {
            cacheService.saveCache(requestType, request);
        }
    }

}
