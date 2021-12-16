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
        Role Admin = new Role(RoleNombre.ADMIN);
        Role User = new Role(RoleNombre.USER);
        Role Validate = new Role(RoleNombre.VALIDATE);
        roleService.save(Admin);
        roleService.save(User);
        roleService.save(Validate);

        // CREAMOS EL ADMIN
        Usuario usuario = new Usuario("admin","admin@admin.com", passwordEncoder.encode("admin") );
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getByRolNombre(RoleNombre.ADMIN).get());
        usuario.setRole(roles);
        usuarioService.guardarUsuario(usuario);
    }
}
