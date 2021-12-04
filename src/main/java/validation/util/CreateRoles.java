package validation.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import validation._enum.RoleNombre;
import validation.entity.Role;
import validation.service.RoleService;

@Component
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        Role roleAdmin = new Role(RoleNombre.ROLE_ADMIN);
        Role roleUser = new Role(RoleNombre.ROLE_USER);
        Role roleValidate = new Role(RoleNombre.ROLE_VALIDATE);
        roleService.save(roleAdmin);
        roleService.save(roleUser);
        roleService.save(roleValidate);
    }
}
