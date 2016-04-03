package monitoring.pets.processor.processors.impl;

import java.util.List;
import monitoring.pets.processor.model.MetricRange;
import monitoring.pets.processor.model.MetricRangeTypeEnum;
import monitoring.pets.processor.model.communication.EmailTemplate;
import monitoring.pets.processor.model.communication.HealthMetricRequest;
import monitoring.pets.processor.model.communication.RequestType;
import monitoring.pets.processor.processors.AbstractProcessor;
import monitoring.pets.processor.repositories.MetricRangeRepository;
import monitoring.pets.processor.service.INotificationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Procesador de metricas de salud
 *
 * @author sergioleottau
 */
@Scope("prototype")
@Service
public class HealthProcessor extends AbstractProcessor<HealthMetricRequest> {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HealthProcessor.class);
    /**
     * Solicitud de procesamiento de metricas de salud.
     */
    private HealthMetricRequest healthMetricRequest;
    /**
     * Repositorio de metricas
     */
    @Autowired
    private MetricRangeRepository metricRangeRepository;
    /**
     * Servicio de notificacion
     */
    @Autowired
    private INotificationService notificationService;

    @Override
    public void setRequest(HealthMetricRequest request) {
        healthMetricRequest = request;
    }

    @Override
    public Boolean call() {

        boolean successful = false;

        try {
            LOGGER.info("Trying to process a health metric [{}]", healthMetricRequest);
            Validate.notNull(healthMetricRequest, "Invalid healt metric request");
            Validate.notBlank(healthMetricRequest.getIdCollar(), "Invalid id collar");

            List<MetricRange> metricRanges
                    = metricRangeRepository.findByIdCollar(healthMetricRequest.getIdCollar());

            if (validateMetric(metricRanges)) {
                LOGGER.info("Valid metric [{}]", healthMetricRequest);
            }

            successful = true;
        } catch (Exception e) {
            LOGGER.error("Error processing health metric [{}]", healthMetricRequest, e);
        } finally {
            resultsManagement(successful, healthMetricRequest, RequestType.HEALTH);
        }

        return successful;

    }

    /**
     * Valida los rangos de metricas
     *
     * @param metricRanges rangos metricas asociados a una mascota
     * @return
     */
    private boolean validateMetric(List<MetricRange> metricRanges) {
        boolean isValid = true;

        LOGGER.info("Validating [{}] ranges metrics", metricRanges.size());

        Double heartRate = Double.valueOf(StringUtils.trimToEmpty(healthMetricRequest.getHeartRate()));
        Double breathingRate = Double.valueOf(StringUtils.trimToEmpty(healthMetricRequest.getBreathingRate()));

        for (MetricRange metricRange : metricRanges) {
            LOGGER.info("Validating metrics: range[{}], heartRate [{}], breathingRate [{}]",
                    metricRange, heartRate, breathingRate);

            if (metricRange.getMetricRangeType().equals(MetricRangeTypeEnum.HEART_RATE)) {
                isValid = validate(metricRange, heartRate, EmailTemplate.HEALTH_EMAIL);
            } else if (metricRange.getMetricRangeType().equals(MetricRangeTypeEnum.BREATHING_RATE)) {
                isValid = validate(metricRange, breathingRate, EmailTemplate.BREATHING_EMAIL);
            }

            if (!isValid) {
                LOGGER.info("Invalidating metrics: range[{}], heartRate [{}], breathingRate [{}]",
                        metricRange, heartRate, breathingRate);
                break;
            }
        }

        return isValid;
    }

    /**
     * Verifica que el valor se encuentre dentro del rango
     *
     * @param metricRange rango valido para la metrica
     * @param value valor
     * @return <b>true</b> si se encuentra dentro del rango, <b>false</b> de
     * otra manera
     */
    private boolean validate(MetricRange metricRange, Double value, EmailTemplate emailTemplate) {
        boolean isValid = value > metricRange.getMinimum() && value < metricRange.getMaximum();

        if (!isValid) {
            notificationService.notify(metricRange.getPet().getUser().getEmail(),
                    emailTemplate.getEmailMessage(value.toString(), metricRange.getMinimum().toString(),
                            metricRange.getMaximum().toString()), 
                    healthMetricRequest.getBeginDate(), RequestType.HEALTH);
        }

        return isValid;
    }

}
