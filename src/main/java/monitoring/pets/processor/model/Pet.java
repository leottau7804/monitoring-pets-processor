package monitoring.pets.processor.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entidad mascota
 *
 * @author sergioleottau
 */
@Entity
@Table(name = "MASCOTA")
public class Pet implements Serializable {

    /**
     * Identificador
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MASCOTA_ID")
    private Long id;
    /**
     * Raza
     */
    @Column(name = "RAZA")
    private String breed;
    /**
     * Identificacion del collar
     */
    @Column(name = "COLLAR_ID")
    private String idCollar;
    /**
     * Nombre de la mascota
     */
    @Column(name = "NOMBRE")
    private String name;
    /**
     * Tipo de mascota
     */
    @Column(name = "TIPO")
    @Enumerated(EnumType.STRING)
    private PetTypeEnum petType;
    /**
     * Fecha de nacimiento de la mascota
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "FECHA_NACIMIENTO")
    private Date birthDate;
    /**
     * Usuario dueño de la mascota
     */
    @ManyToOne
    @JoinColumn(name = "USUARIO_ID")
    private User user;
    /**
     * Rangos asociados
     */
    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY)
    private List<MetricRange> metricRanges;
    
    /**
     * Zonas seguras asociadas a la mascota
     */
    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY)
    private List<SafeZone> safeZones;

    /**
     * Constructor por defecto
     */
    public Pet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PetTypeEnum getPetType() {
        return petType;
    }

    public void setPetType(PetTypeEnum petType) {
        this.petType = petType;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getIdCollar() {
        return idCollar;
    }

    public void setIdCollar(String idCollar) {
        this.idCollar = idCollar;
    }

    public List<MetricRange> getMetricRanges() {
        return metricRanges;
    }

    public void setMetricRanges(List<MetricRange> metricRanges) {
        this.metricRanges = metricRanges;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.id);
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
        final Pet other = (Pet) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pet{" + "id=" + id + ", breed=" + breed + ", name=" + name + ", petType=" + petType + ", birthDate=" + birthDate + '}';
    }

}
