package validation.service;

import validation._enum.RoleNombre;
import validation.entity.Role;
import validation.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Clase que implementa los metodos de la interfaz {@link IRoleService} del servicio para los
 * roles.
 */

@Service
@Transactional
public class RoleService implements IRoleService{

    @Autowired
    RoleRepository roleRepository;

    public Optional<Role> getByRolNombre(RoleNombre roleNombre){
        return  roleRepository.findByRoleNombre(roleNombre);
    }

    public void save(Role role){
        roleRepository.save(role);
    }

    @Override
    @Transactional
    public List<Role> obtenerTodosRoles() {
        return null;
    }

    @Override
    @Transactional
    public Role buscarRolePorId(Long idRole) {
        return null;
    }
}
 // fin de la implementacion de la interfaz de servicios

