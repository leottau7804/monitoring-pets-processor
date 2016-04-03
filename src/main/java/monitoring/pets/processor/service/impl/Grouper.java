package monitoring.pets.processor.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import monitoring.pets.processor.model.communication.HealthMetricRequest;
import monitoring.pets.processor.model.communication.MetricRequest;
import monitoring.pets.processor.model.communication.PositionMetricRequest;
import monitoring.pets.processor.model.communication.RequestType;
import monitoring.pets.processor.model.communication.TopicEnum;
import monitoring.pets.processor.service.IGrouper;
import monitoring.pets.processor.service.IRouter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio encargado del agrupamiento de mensajes
 *
 * @author sergioleottau
 */
@Service
public class Grouper implements IGrouper {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Grouper.class);
    /**
     * Router service
     */
    @Autowired
    private IRouter router;

    @Override
    public void group(ConsumerRecords<String, String> records) {
        try {
            List<MetricRequest> healthMetricRequests = new ArrayList<>();
            List<MetricRequest> positionMetricRequests = new ArrayList<>();

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setDateFormat(df);

            for (ConsumerRecord<String, String> record : records) {
                if (record.topic().equals(TopicEnum.HEALTH_TOPIC.getId())
                        || record.topic().equals(TopicEnum.RETRY_HEALTH_TOPIC.getId())) {

                    healthMetricRequests.add(objectMapper.readValue(record.value(),
                            HealthMetricRequest.class));
                } else if (record.topic().equals(TopicEnum.POSITION_TOPIC.getId())
                        || record.topic().equals(TopicEnum.RETRY_POSITION_TOPIC.getId())) {

                    positionMetricRequests.add(objectMapper.readValue(record.value(),
                            PositionMetricRequest.class));
                }
            }

            route(RequestType.HEALTH, healthMetricRequests);
            route(RequestType.POSITION, positionMetricRequests);

        } catch (Exception e) {
            LOGGER.error("Unexpected error trying to process records", e);
        }

    }

    /**
     * Route
     *
     * @param requestType
     * @param metrics
     */
    private void route(RequestType requestType, List<MetricRequest> metrics) {
        try {
            router.route(requestType, metrics);
        } catch (Exception e) {
            LOGGER.error("Unexpected error trying to process [{}] records", e);
        }

    }

}
