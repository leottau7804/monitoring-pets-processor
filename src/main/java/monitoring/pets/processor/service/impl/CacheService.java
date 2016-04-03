package monitoring.pets.processor.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.validation.Valid;
import monitoring.pets.processor.model.communication.MetricRequest;
import monitoring.pets.processor.model.communication.RequestType;
import monitoring.pets.processor.service.ICacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * Servicio para la gestion del cache
 *
 * @author sergioleottau
 */
@Service
public class CacheService implements ICacheService {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheService.class);
    /**
     * Separador de la llave
     */
    private static final String SEPARATOR = "-";
    /**
     * Url de redis
     */
    @Value("${redis.server}")
    private String redisServer;

    @Async
    @Override
    public void saveCache(RequestType requestType, MetricRequest metricRequest) {

        try {
            StringBuilder key = new StringBuilder();
            key.append(metricRequest.getIdCollar());
            key.append(SEPARATOR);
            key.append(requestType.name());

            metricRequest.setAttempts(null);
            metricRequest.setBeginDate(null);

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(metricRequest);

            Jedis jedis = new Jedis(redisServer);
            jedis.set(key.toString(), json);
        } catch (Exception ex) {
            LOGGER.error("Unexpected error trying to save cache [{}], [{}]",
                    requestType, metricRequest, ex);
        }

    }

}
