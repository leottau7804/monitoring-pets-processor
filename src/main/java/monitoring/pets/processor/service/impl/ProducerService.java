package monitoring.pets.processor.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Properties;
import javax.annotation.PostConstruct;
import monitoring.pets.processor.model.communication.MetricRequest;
import monitoring.pets.processor.model.communication.TopicEnum;
import monitoring.pets.processor.service.IProducerService;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Servicio publicador de mensajes en kafka
 * @author sergioleottau
 */
@Service
public class ProducerService implements IProducerService {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerService.class);
    /**
     * Propiedades del productor
     */
    private Properties props;
    /**
     * Direccion del servidor kafka
     */
    @Value("${kafka.server}")
    private String kafkaServer;

    @PostConstruct
    public void init() {

        props = new Properties();
        props.put("bootstrap.servers", kafkaServer);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    }

    @Override
    public void send(TopicEnum topicEnum, MetricRequest metricRequest) {

        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(metricRequest);
            producer.send(new ProducerRecord<>(topicEnum.getId(),
                    metricRequest.getIdCollar(),
                    json));
        } catch (Exception e) {
            LOGGER.error("Error trying to send the message [{}] to kafka",
                    metricRequest, e);
        }

    }

}
