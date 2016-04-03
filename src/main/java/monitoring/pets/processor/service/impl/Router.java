package monitoring.pets.processor.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.PostConstruct;
import monitoring.pets.processor.model.communication.MetricRequest;
import monitoring.pets.processor.model.communication.RequestType;
import monitoring.pets.processor.processors.AbstractProcessor;
import monitoring.pets.processor.service.IRouter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Enrutador de las metricas
 *
 * @author sergioleottau
 */
@Service
public class Router implements IRouter {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Router.class);
    /**
     * Contexto.
     */
    @Autowired
    private ApplicationContext context;
    /**
     * Ejecutor de procesadores
     */
    private ExecutorService executorService;

    @PostConstruct
    public void init() {
        executorService = Executors.newCachedThreadPool();
    }

    @Async
    @Override
    public void route(RequestType requestType, List<MetricRequest> metrics) {

        LOGGER.info("Begins the metric routing for [{}]...", requestType);

        if (metrics != null && !metrics.isEmpty()) {

            List<AbstractProcessor<? extends MetricRequest>> tasks = new ArrayList<>();
            for (MetricRequest metric : metrics) {
                AbstractProcessor processor = context.getBean(requestType.getProcessor());
                processor.setRequest(metric);
                tasks.add(processor);
            }

            try {
                executorService.invokeAll(tasks);                
            } catch (Exception ex) {
                LOGGER.error("Unexpected error invoking processors", ex);
            }

        }
    }

}
