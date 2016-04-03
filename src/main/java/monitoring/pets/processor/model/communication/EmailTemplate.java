package monitoring.pets.processor.model.communication;

import java.text.MessageFormat;

/**
 * Plantillas de correo
 *
 * @author sergioleottau
 */
public enum EmailTemplate {

    HEALTH_EMAIL("Se ha detectado el ritmo cardiaco actual {0} por fuera del rango permitido. Rango: minimo [{1}] - maximo[{2}]"),
    BREATHING_EMAIL("Se ha detectado el ritmo respiratorio actual {0} por fuera del rango permitido. Rango: minimo [{1}] - maximo[{2}]"),
    POSITION_EMAIL("Se ha detectado la ubicación de {0} por fuera de las zonas seguras.");

    /**
     * Plantilla del mensaje
     */
    private String emailMessage;

    private EmailTemplate(String emailMessage) {
        this.emailMessage = emailMessage;
    }

    public String getEmailMessage(String... args) {
        MessageFormat messageFormat = new MessageFormat(emailMessage);
        return messageFormat.format(args);
    }

}
