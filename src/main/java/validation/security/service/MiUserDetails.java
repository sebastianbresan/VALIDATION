package validation.security.service;

import validation.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Esta clase proporciona la implementacion para establecer informacion del usuario.
 * Almacenando información del usuario que más tarde se encapsula en objetos de autenticación
 */
public class MiUserDetails implements UserDetails {

    private final String username;
    private final String email;
    private final String password;
    // Variable que nos da la autorización (no confundir con autenticación)
    // Coleccion de tipo generico que extendiende
    // de GranthedAuthority de Spring security
    private final Collection<? extends GrantedAuthority> authorities;

    //Constructor
    public MiUserDetails(String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    //Metodo que asigna los privilegios (autorización)
    public static MiUserDetails build(Usuario usuario){
        //Convertimos la clase Rol a la clase GrantedAuthority
        List<GrantedAuthority> authorities =
                usuario.getRole()
                        .stream()
                        .map(rol -> new SimpleGrantedAuthority(rol.getRoleNombre().name()))
                        .collect(Collectors.toList());
        return new MiUserDetails(usuario.getUsername(), usuario.getEmail(),
                usuario.getPassword(), authorities);
    }

    //@Override los que tengan esta anotación
    // significa que son metodos de UserDetails de SpringSecurity

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getEmail() {
        return email;
    }
}
// fin de la clase details
