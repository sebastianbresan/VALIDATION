package validation.repository;


import validation._enum.RoleNombre;
import validation.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Este repositorio extiende de {@link JpaRepository} que permite usar los metodos
 * para las operaciones basicas de un CRUD que se haran hacia la tabla de <b>roles</b>.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleNombre(RoleNombre rolNombre);
}

