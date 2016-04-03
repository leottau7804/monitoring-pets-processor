package monitoring.pets.processor.service;

import org.apache.kafka.clients.consumer.ConsumerRecords;

/**
 *
 * @author sergioleottau
 */
public interface IGrouper {

    /**
     * 
     * @param records 
     */
    void group(ConsumerRecords<String, String> records);

}
