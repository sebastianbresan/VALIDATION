package validation.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import validation._enum.RoleNombre;
import validation.entity.Role;
import validation.entity.Usuario;
import validation.service.RoleService;
import validation.service.UsuarioService;

import java.util.HashSet;
import java.util.Set;

@Component
public class CreateRolesAndAdmin implements CommandLineRunner {

    @Autowired
    private RoleService roleService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // CREAMOS LOS ROLES
        Role roleAdmin = new Role(RoleNombre.ROLE_ADMIN);
        Role roleUser = new Role(RoleNombre.ROLE_USER);
        Role roleValidate = new Role(RoleNombre.ROLE_VALIDATE);
        roleService.save(roleAdmin);
        roleService.save(roleUser);
        roleService.save(roleValidate);

        // CREAMOS EL ADMIN
        Usuario usuario = new Usuario("admin","admin@admin.com", passwordEncoder.encode("admin") );
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getByRolNombre(RoleNombre.ROLE_USER).get());
        roles.add(roleService.getByRolNombre(RoleNombre.ROLE_ADMIN).get());
        usuario.setRole(roles);
        usuarioService.guardarUsuario(usuario);
    }
}
