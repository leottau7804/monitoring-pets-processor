package monitoring.pets.processor.model.communication;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;

/**
 * Solicitud de procesamiento de metrica
 *
 * @author sergioleottau
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetricRequest {

    /**
     * Identificacion del collar
     */
    private String idCollar;
    /**
     * Cantidad de intentos de procesamiento
     */
    private Integer attempts;
    /**
     * Fecha de inicio
     */
    private Date beginDate;

    public MetricRequest() {
    }

    public String getIdCollar() {
        return idCollar;
    }

    public void setIdCollar(String idCollar) {
        this.idCollar = idCollar;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public void incrementAttempt() {
        if (attempts == null) {
            attempts = 0;
        }
        attempts++;
    }

    @Override
    public String toString() {
        return "RequestMessage{" + "idCollar=" + idCollar + ", attempts=" + attempts + '}';
    }

}
