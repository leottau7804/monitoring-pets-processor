package monitoring.pets.processor.service.impl;

import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import monitoring.pets.processor.model.communication.TopicEnum;
import monitoring.pets.processor.service.IConsumerService;
import monitoring.pets.processor.service.IGrouper;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Servicio para el consumo de mensajes
 *
 * @author sergioleottau
 */
@Service
public class ConsumerService implements IConsumerService {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerService.class);
    /**
     * Consumidor de mensajes
     */
    private KafkaConsumer<String, String> kafkaConsumer;
    /**
     * Alimentador
     */
    @Autowired
    private IGrouper feeder;
    /**
     * Direccion del servidor kafka
     */
    @Value("${kafka.server}")
    private String kafkaServer;
    /**
     * Flag to run the consumer process
     */
    private volatile boolean alive = true;

    /**
     * Metodo encargado de inicializar el cliente de kafka
     */
    private void init() {
        alive = true;

        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaServer);
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(TopicEnum.getIdArray());
    }

    /**
     * Inicia el consumo de mensajes de kafka
     */
    @Async
    @Override
    public void begin() {
        init();
        try {
            while (alive) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(1000);
                if (!records.isEmpty()) {
                    feeder.group(records);
                }
            }
        } catch (WakeupException e) {
            LOGGER.info("Ignoring wakeupException for kafka");
        } finally {
            kafkaConsumer.close();
        }
    }

    /**
     * tiene el proceso de consumo de kafka
     */
    @PreDestroy
    public void destroy() {
        LOGGER.info("Destroying consumer service");
        kafkaConsumer.wakeup();
        alive = false;
    }

}
