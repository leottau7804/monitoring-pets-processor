package monitoring.pets.processor.service;

import monitoring.pets.processor.model.communication.MetricRequest;
import monitoring.pets.processor.model.communication.TopicEnum;

/**
 * Productor de metricas
 *
 * @author sergioleottau
 */
public interface IProducerService {

    /**
     * Envia una metrica a kafka
     *
     * @param topicEnum
     * @param metricRequest
     */
    void send(TopicEnum topicEnum, MetricRequest metricRequest);

}
