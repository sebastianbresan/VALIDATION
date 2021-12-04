package validation.entity;

import com.sun.istack.NotNull;
import validation._enum.RoleNombre;

import javax.persistence.*;

/**
 * Esta clase representa a la tabla de la BD llamada <b>roles</b>
 * en la cual las propiedades definidas aqui seran mapeadas a la tabla.
 * y en dicha tabla se encuentran los tipos de acceso al siste <b>(ADMIN, USER, PUBLIC)</b>.
 */

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @NotNull
    //Se indica que va a ser un Enum de tipo String
    @Enumerated(EnumType.STRING)
    private RoleNombre roleNombre;

    public Role() {
    }

    public Role(@NotNull RoleNombre roleNombre) {
        this.roleNombre = roleNombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleNombre getRoleNombre() {
        return roleNombre;
    }

    public void setRoleNombre(RoleNombre roleNombre) {
        this.roleNombre = roleNombre;
    }
}