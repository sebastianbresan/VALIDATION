package validation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import validation._enum.RoleNombre;
import validation.entity.Role;
import validation.entity.Usuario;
import validation.payload.AutenticacionLogin;
import validation.payload.AutenticacionResponse;
import validation.repository.UsuarioRepository;
import validation.security.service.MiUserDetailsService;
import validation.security.utils.JwtUtil;
import validation.service.RoleService;
import validation.service.UsuarioService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
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
    @Autowired
    private final UsuarioRepository usuarioRepository;

    public UserController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }


    /* ~ Rutas publicas
    ------------------------------------------------------------------------------- */
    @GetMapping("/api/public")
    public String homePublic() {
        return "qr valido";
    } // fin de la peticion

    @GetMapping("/api/home")
    public String userAuthenticated() {
        return "Welcome";
    }

    @GetMapping("/api/admin")
    public ResponseEntity<?> administrate(@RequestBody Usuario usuario) {
        return ResponseEntity.ok("Eres Admin");
    }

    @GetMapping("/api/users/find/all")
    public List<Usuario> getUsuarios() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/api/users/find/findbyusername/{username}")
    public ResponseEntity<?> findByUsername(@PathVariable() String username) {
        return ResponseEntity.ok(usuarioRepository.findUsuarioPorUsername(username));
    }

    @PutMapping("/api/users/update/{username}")
    public ResponseEntity<?> updateUsuario(@PathVariable String username, @RequestBody Usuario usuario) {
        Usuario usuario1 = usuarioRepository.findUsuarioPorUsername(username).orElseThrow(RuntimeException::new);
        usuario1.setUsername(usuario.getUsername());
        usuario1.setEmail(usuario.getEmail());
        usuario1 = usuarioRepository.save(usuario);
        return ResponseEntity.ok(usuario1);
    }

    @PostMapping("/api/register")
    public ResponseEntity<?> nuevoUsuario(@Valid @RequestBody Usuario nuevoUsuario,
                                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Error al compilar el nuevo usuario, verifique los campos");
        }
        if (usuarioService.existsByUsuario(nuevoUsuario.getUsername())) {
            return ResponseEntity.badRequest().body("El nombre de usuario ya existe en nuestra base de datos");
        }
        if (usuarioService.existsByEmail(nuevoUsuario.getEmail())) {
            return ResponseEntity.badRequest().body("El email ya existe en nuestra base de datos");
        }

      /*   CREAMOS EL USUARIO Y ASIGNAMOS QUE ROLES VA A TENER
         AL SER UN NUEVO USUARIO SOLO LE VAMOS A ASIGNAR EL ROL DE VALIDATE
         LUEGO CUANDO SE VALIDEN LOS DATOS PASARA A TENER ROL DE USER*/

        Usuario usuario = new Usuario(nuevoUsuario.getUsername(), nuevoUsuario.getEmail(), passwordEncoder.encode(nuevoUsuario.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getByRolNombre(RoleNombre.VALIDATE).orElse(null));
        usuario.setRole(roles);
        usuarioService.guardarUsuario(usuario);
        return ResponseEntity.ok("Usuario creado correctamente");
    }

    @PostMapping("/api/login")
    public ResponseEntity<?> iniciarSesion(@RequestBody AutenticacionLogin autLogin) throws Exception {

        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(autLogin.getUsername(), autLogin.getPassword())
            );
        } catch (BadCredentialsException ex) {
            throw new Exception("Error en el username o contraseña " + ex.getMessage());
        }
        final UserDetails userDetails = miUserDetailsService.loadUserByUsername(autLogin.getUsername());
        final String token = jwtUtil.creatToken(userDetails);

        return ResponseEntity.ok(new AutenticacionResponse(token));

    }

    @DeleteMapping("/api/users/delete/{username}")
    public ResponseEntity<?> deleteByUsername(@PathVariable String username) {
        usuarioService.eliminarUsuarioPorUsername(username);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/users/delete/all")
    public ResponseEntity<?> deleteAll() {
        usuarioRepository.deleteAll();
        return ResponseEntity.accepted().build();
    }
} // fin para iniciar sesion
