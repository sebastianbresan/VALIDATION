package validation.controller;

import validation.entity.Role;
import validation.entity.Usuario;
import validation.payload.AutenticacionLogin;
import validation.payload.AutenticacionResponse;
import validation.security.service.MiUserDetailsService;
import validation.security.utils.JwtUtil;
import validation.service.IRoleService;
import validation.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class UserController {

    /* ~ Autowired
    ------------------------------------------------------------------------------- */
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUsuarioService usuarioService;
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
    public String homePublic(){
        return "Pagina de inicio al publico";
    } // fin de la peticion

    @PostMapping("/register")
    public ResponseEntity<?> registrarse(@RequestBody Usuario usuario){
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Asignar role de user
        Role role = roleService.buscarRolePorId(3L);
        usuario.agregarRoleALista(role);
        usuario.setActivo(true);
        usuarioService.guardarUsuario(usuario);

        return ResponseEntity.ok("Usuario registrado correctamente");
    } // fin de la pagina de registro

    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@RequestBody AutenticacionLogin autLogin) throws Exception{
        //autLogin.getPassword();
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(autLogin.getUsername(), autLogin.getPassword())
            );

        }catch (BadCredentialsException ex){
            throw new Exception("Error en el username o contrase??a " + ex.getMessage());
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
    public String userAuthenticated(){
        return "Welcome";
    }

} // fin del controlador home
