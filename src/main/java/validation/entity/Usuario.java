package validation.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Esta clase representa a la tabla de la BD llamada <b>usuarios</b>
 * en la cual las propiedades definidas aqui seran mapeadas a la tabla.
 * Ya que existe una relacion de Muchos a Muchos con la Tabla roles se genera la relacion
 * @ManyToMany con la tabla {@link Role}.
 */

@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;

    /* ~ Propiedades
    ==================================== */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @NotNull
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private boolean activo;

    @NotNull
    @ManyToMany
    @JoinTable(name = "usuarios_roles", joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Role> roles = new HashSet<>();

    /* ~ Metodos
    ==================================== */

    public Usuario() {
    }

    //Constuctor sin Id ni Roles
    public Usuario(@NotNull String username,
                   @NotNull String email,
                   @NotNull String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Set<Role> getRole() {
        return roles;
    }

    public void setRole(Set<Role> role) {
        this.roles = role;
    }
} // fin de la clase entidad
