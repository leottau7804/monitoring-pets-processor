package monitoring.pets.processor.processors.impl;

import java.util.List;
import monitoring.pets.processor.model.Location;
import monitoring.pets.processor.model.SafeZone;
import monitoring.pets.processor.model.communication.EmailTemplate;
import monitoring.pets.processor.model.communication.PositionMetricRequest;
import monitoring.pets.processor.model.communication.RequestType;
import monitoring.pets.processor.processors.AbstractProcessor;
import monitoring.pets.processor.repositories.SafeZoneRepository;
import monitoring.pets.processor.service.INotificationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Procesador de metricas de localizacion
 *
 * @author sergioleottau
 */
@Scope("prototype")
@Service
public class PositionProcessor extends AbstractProcessor<PositionMetricRequest> {

    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(PositionProcessor.class);
    /**
     * Solicitud de procesamiento de metrica de localicacion
     */
    private PositionMetricRequest positionMetricRequest;
    /**
     * Reposotorio de zonas seguras
     */
    @Autowired
    private SafeZoneRepository safeZoneRepository;
    /**
     * Servicio de notificacion
     */
    @Autowired
    private INotificationService notificationService;

    @Override
    public void setRequest(PositionMetricRequest request) {
        positionMetricRequest = request;
    }

    @Override
    public Boolean call() {
        boolean succesful = false;

        try {

            LOGGER.info("Trying to process a position metric [{}]", positionMetricRequest);
            Validate.notNull(positionMetricRequest, "Invalid healt metric request");
            Validate.notBlank(positionMetricRequest.getIdCollar(), "Invalid id collar");

            List<SafeZone> safeZones
                    = safeZoneRepository.findByIdCollar(positionMetricRequest.getIdCollar());
            LOGGER.info("There is [{}] safe zones", safeZones.size());

            if (validateZones(safeZones)) {
                LOGGER.info("Valid metric [{}]", positionMetricRequest);
            } else {
                LOGGER.error("The pet [{}] is not in any safe zone", positionMetricRequest);

                if (!safeZones.isEmpty()) {
                    notificationService.notify(safeZones.get(0).getPet().getUser().getEmail(),
                            EmailTemplate.POSITION_EMAIL.getEmailMessage(safeZones.get(0).getPet().getName()),
                            positionMetricRequest.getBeginDate(), RequestType.POSITION);
                }
            }

            succesful = true;
        } catch (Exception e) {
            LOGGER.error("Error processing position metric [{}]", positionMetricRequest, e);
        } finally {
            resultsManagement(succesful, positionMetricRequest, RequestType.POSITION);
        }

        return succesful;
    }

    /**
     * Valida que la mascota se encuentre en por lo menos una de las zonas
     * seguras
     *
     * @param safeZones zonas seguras asociadas a la mascota
     * @return true si se encuentra en por lo menos una zona segura
     */
    private boolean validateZones(List<SafeZone> safeZones) {
        boolean isValid = false;

        if (safeZones != null && !safeZones.isEmpty()) {
            for (SafeZone safeZone : safeZones) {
                if (validateOneZone(safeZone)) {
                    isValid = true;
                    break;
                } else {
                    LOGGER.info("The pet [{}] is not in the safe zone[{}]",
                            positionMetricRequest.getIdCollar(), safeZone.getName());
                }
            }
        } else {
            isValid = true;
        }

        return isValid;
    }

    /**
     * Valida si la mascota se encutra en la zona segura dada
     *
     * @param safeZone zona segura
     * @return true si se encuentra dentro de la zona segura
     */
    private boolean validateOneZone(SafeZone safeZone) {

        Double latitude = Double.valueOf(StringUtils.trimToEmpty(positionMetricRequest.getLatitude()));
        Double longitude = Double.valueOf(StringUtils.trimToEmpty(positionMetricRequest.getLongitude()));

        Double minLatitude = Double.MAX_VALUE;
        Double maxLatitude = Double.MIN_VALUE;

        for (Location location : safeZone.getLocations()) {
            if (location.getLatitude() < minLatitude) {
                minLatitude = location.getLatitude();
            }

            if (location.getLatitude() > maxLatitude) {
                maxLatitude = location.getLatitude();
            }
        }

        Double minLongitude = Double.MAX_VALUE;
        Double maxLongitude = Double.MIN_VALUE;

        for (Location location : safeZone.getLocations()) {
            if (location.getLongitude() < minLongitude) {
                minLongitude = location.getLongitude();
            }

            if (location.getLongitude() > maxLongitude) {
                maxLongitude = location.getLongitude();
            }
        }

        return latitude >= minLatitude && latitude <= maxLatitude
                && longitude >= minLongitude && longitude <= maxLatitude;
    }

}
