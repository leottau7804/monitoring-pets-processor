package monitoring.pets.processor.model.communication;

import monitoring.pets.processor.processors.AbstractProcessor;
import monitoring.pets.processor.processors.impl.HealthProcessor;
import monitoring.pets.processor.processors.impl.PositionProcessor;

/**
 * Tipo de solicitud de procesamiento
 *
 * @author sergioleottau
 */
public enum RequestType {

    HEALTH(HealthProcessor.class, HealthMetricRequest.class, TopicEnum.RETRY_HEALTH_TOPIC),
    POSITION(PositionProcessor.class, PositionMetricRequest.class, TopicEnum.RETRY_POSITION_TOPIC);
    /**
     * Procesador
     */
    private final Class<? extends AbstractProcessor<? extends MetricRequest>> processor;
    /**
     * Tipo de solicitud
     */
    private final Class<? extends MetricRequest> metricRequest;
    /**
     * Topico asociado al tipo de solicitud
     */
    private final TopicEnum topicEnum;

    private RequestType(Class<? extends AbstractProcessor<? extends MetricRequest>> processor, Class<? extends MetricRequest> metricRequest, TopicEnum topicEnum) {
        this.processor = processor;
        this.metricRequest = metricRequest;
        this.topicEnum = topicEnum;
    }

    public Class<? extends AbstractProcessor<? extends MetricRequest>> getProcessor() {
        return processor;
    }

    public Class<? extends MetricRequest> getMetricRequest() {
        return metricRequest;
    }

    public TopicEnum getTopicEnum() {
        return topicEnum;
    }

}
