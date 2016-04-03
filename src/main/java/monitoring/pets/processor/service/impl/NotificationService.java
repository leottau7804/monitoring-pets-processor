package monitoring.pets.processor.service.impl;

import java.util.Date;
import monitoring.pets.processor.model.Log;
import monitoring.pets.processor.model.communication.RequestType;
import monitoring.pets.processor.service.ILogService;
import monitoring.pets.processor.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Servicio de notificacion
 *
 * @author sergioleottau
 */
@Service
public class NotificationService implements INotificationService {

    /**
     * Asunto del correo electronico
     */
    private static final String SUBJECT = "Alerta !!!";
    /**
     * Notificador de emails
     */
    @Autowired
    private JavaMailSenderImpl mailSender;
        /**
     * Servicio para la gestion del cache
     */
    @Autowired
    private ILogService logService;

    @Async
    @Override
    public void notify(String email, String message, Date beginDate,
            RequestType requestType) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject(SUBJECT);
            simpleMailMessage.setText(message);
            mailSender.send(simpleMailMessage);
        } finally {
            Log log = new Log();
            log.setBeginDate(beginDate);
            log.setEndDate(new Date());
            log.setRequestType(requestType);
            logService.saveLog(log);
        }

    }

}
