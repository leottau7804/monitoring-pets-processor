package monitoring.pets.processor.service;

import java.util.Date;
import monitoring.pets.processor.model.communication.RequestType;

/**
 * Servicio de notificacion
 *
 * @author sergioleottau
 */
public interface INotificationService {

    /**
     * Notificacion del mensaje dado
     *
     * @param message mensaje a notificar
     * @param email correo electronico
     * @param beginDate 
     * @param requestType 
     */
    void notify(String email, String message, Date beginDate,
            RequestType requestType);

}
