package validation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import validation._enum.RoleNombre;
import validation.entity.Role;
import validation.entity.Usuario;
import validation.payload.AutenticacionLogin;
import validation.payload.AutenticacionResponse;
import validation.security.service.MiUserDetailsService;
import validation.security.utils.JwtUtil;
import validation.service.RoleService;
import validation.service.UsuarioService;
import validation.util.Mensaje;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin("*")
public class UserController {

    /* ~ Autowired
    ------------------------------------------------------------------------------- */
    @Autowired
    private RoleService roleService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private MiUserDetailsService miUserDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    /* ~ Rutas publicas
    ------------------------------------------------------------------------------- */
    @GetMapping("/public")
    public String homePublic() {
        return "Pagina de inicio al publico";
    } // fin de la peticion

    @PostMapping("/register")
    public ResponseEntity<?> nuevoUsuario(@Valid @RequestBody Usuario nuevoUsuario,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Mensaje("Campos mal o email invalido"), HttpStatus.BAD_REQUEST);
        }
        if (usuarioService.existsByUsuario(nuevoUsuario.getUsername())) {
            return new ResponseEntity<>(new Mensaje("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        }
        if (usuarioService.existsByEmail(nuevoUsuario.getEmail())) {
            return new ResponseEntity<>(new Mensaje("Ese email ya existe"), HttpStatus.BAD_REQUEST);
        }

      /*   CREAMOS EL USUARIO Y ASIGAMOS QUE ROLES VA A TENER
         AL SER UN NUEVO USUARIO SOLO LE VAMOS A ASIGAR EL ROL DE VALIDATE
         LUEGO CUANDO SE VALIDEN LOS DATOS PASARA A TENER ROL DE USER*/

        Usuario usuario = new Usuario(nuevoUsuario.getUsername(), nuevoUsuario.getEmail(), passwordEncoder.encode(nuevoUsuario.getPassword()));
        Set<Role> roles = new HashSet<>();
        // roles.add(roleService.getByRolNombre(RoleNombre.ROLE_USER).get());
        // roles.add(roleService.getByRolNombre(RoleNombre.ROLE_ADMIN).get());
        roles.add(roleService.getByRolNombre(RoleNombre.ROLE_VALIDATE).get());
        usuario.setRole(roles);

        usuarioService.guardarUsuario(usuario);

        return new ResponseEntity<>(new Mensaje("Usuario creado correctamente"), HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@RequestBody AutenticacionLogin autLogin) throws Exception {
        //autLogin.getPassword();
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(autLogin.getUsername(), autLogin.getPassword())
            );

        } catch (BadCredentialsException ex) {
            throw new Exception("Error en el username o contraseña " + ex.getMessage());
        } // fin de try~catch

        // Obtenemos los datos del usuario de la BD para construir el token
        final UserDetails userDetails = miUserDetailsService.loadUserByUsername(autLogin.getUsername());
        final String token = jwtUtil.creatToken(userDetails);

        // Regresamos el token
        return ResponseEntity.ok(new AutenticacionResponse(token));
    } // fin para iniciar sesion

    /* ~ Rutas privadas (requieren token)
    ------------------------------------------------------------------------------- */
    @GetMapping("/home")
    public String userAuthenticated() {
        return "Welcome";
    }

} // fin del controlador home
