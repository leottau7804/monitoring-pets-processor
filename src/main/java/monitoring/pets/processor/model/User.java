package monitoring.pets.processor.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidad mascota
 *
 * @author sergioleottau
 */
@Entity
@Table(name = "USUARIO")
public class User implements Serializable {

    /**
     * Identificador
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USUARIO_ID")
    private Long id;
    /**
     * Nombre del usuario
     */
    @Column(name = "NOMBRE")
    private String name;
    /**
     * Correo electronico
     */
    @Column(name = "EMAIL")
    private String email;
    /**
     * Mascotas asociadas
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Pet> pets;

    public User() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + ", email=" + email + '}';
    }

}
