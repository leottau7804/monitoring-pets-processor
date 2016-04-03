package monitoring.pets.processor.model;

import java.io.Serializable;
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

/**
 * Entidad de zonas seguras
 *
 * @author sergioleottau
 */
@Entity
@Table(name = "ZONA")
public class SafeZone implements Serializable {

    /**
     * Identificador
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ZONA_ID")
    private Long id;
    /**
     * Nombre de la zona segura
     */
    @Column(name = "NOMBRE")
    private String name;
    /**
     * Estado de la zona segura
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO")
    private SafeZoneStateEnum safeZoneState;
    /**
     * Ubicaciones asociadas a la zona segura.
     */
    @OneToMany(fetch = FetchType.EAGER)
    private List<Location> locations;

    /**
     * Mascota asociada
     */
    @ManyToOne
    @JoinColumn(name = "MASCOTA_ID")
    private Pet pet;

    public SafeZone() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SafeZoneStateEnum getSafeZoneState() {
        return safeZoneState;
    }

    public void setSafeZoneState(SafeZoneStateEnum safeZoneState) {
        this.safeZoneState = safeZoneState;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
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
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final SafeZone other = (SafeZone) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
