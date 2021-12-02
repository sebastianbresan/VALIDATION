package validation.service;

import validation.entity.Role;
import validation.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Clase que implementa los metodos de la interfaz {@link IRoleService} del servicio para los
 * roles.
 */
@Service
public class RoleService implements IRoleService{

    /**
     * Inyeccion para acceder a los metodos del repositorio
     */
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> obtenerTodosRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role buscarRolePorId(Long idRole) {
        return roleRepository.findById(idRole).orElse(null);
    }
} // fin de la implementacion de la interfaz de servicios
