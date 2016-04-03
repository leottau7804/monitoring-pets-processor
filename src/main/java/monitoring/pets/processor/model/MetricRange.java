package monitoring.pets.processor.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entidad rango de metrica
 *
 * @author sergioleottau
 */
@Entity
@Table(name = "RANGO")
public class MetricRange implements Serializable {

    /**
     * Identificador
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RANGO_ID")
    private Long id;
    /**
     * Tipo de range metrica
     */
    @Column(name = "TIPO")
    @Enumerated(EnumType.STRING)
    private MetricRangeTypeEnum metricRangeType;
    /**
     * Limite inferior
     */
    @Column(name = "MINIMO")
    private Double minimum;
    /**
     * Limite superior
     */
    @Column(name = "MAXIMO")
    private Double maximum;
    /**
     * Mascota asociada
     */
    @ManyToOne
    @JoinColumn(name = "MASCOTA_ID")
    private Pet pet;

    /**
     * Constructor por defecto
     */
    public MetricRange() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MetricRangeTypeEnum getMetricRangeType() {
        return metricRangeType;
    }

    public void setMetricRangeType(MetricRangeTypeEnum metricRangeType) {
        this.metricRangeType = metricRangeType;
    }

    public Double getMinimum() {
        return minimum;
    }

    public void setMinimum(Double minimum) {
        this.minimum = minimum;
    }

    public Double getMaximum() {
        return maximum;
    }

    public void setMaximum(Double maximum) {
        this.maximum = maximum;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MetricRange other = (MetricRange) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MetricRange{" + "id=" + id + ", metricRangeType=" + metricRangeType + ", minimum=" + minimum + ", maximum=" + maximum + '}';
    }

}
