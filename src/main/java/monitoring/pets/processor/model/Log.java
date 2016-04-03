package monitoring.pets.processor.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import monitoring.pets.processor.model.communication.RequestType;

/**
 * Entidad Log
 *
 * @author sergioleottau
 */
@Entity
@Table(name = "LOG")
public class Log implements Serializable {

    /**
     * Identificador
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOG_ID")
    private Long id;

    @Column(name = "TIPO")
    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_INICIO")
    private Date beginDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "FECHA_FIN")
    private Date endDate;

    public Log() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    @Override
    public String toString() {
        return "Log{" + "id=" + id + ", requestType=" + requestType + ", beginDate=" + beginDate + ", endDate=" + endDate + '}';
    }

}
