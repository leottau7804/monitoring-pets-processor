package monitoring.pets.processor;

import javax.annotation.PostConstruct;
import monitoring.pets.processor.service.IConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

/**
 *
 * @author sergioleottau
 */
@Controller
public class AppController {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);
    /**
     *
     */
    @Autowired
    private IConsumerService consumerService;

    /**
     *
     */
    @PostConstruct
    public void init() {
        consumerService.begin();
    }

}
