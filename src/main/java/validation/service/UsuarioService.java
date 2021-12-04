package validation.service;

import validation.entity.Usuario;
import validation.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Clase que implementa los metodos de la interfaz {@link IUsuarioService} del servicio para
 * usuarios.
 */
@Service
public class UsuarioService implements IUsuarioService {

    /**
     * Inyeccion para acceder a los metodos del repositorio
     */
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Boolean existsByUsuario(String username){
        return usuarioRepository.existsByUsername(username);
    }

    public Boolean existsByEmail(String email){
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> buscarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscarUsuarioPorId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscarUsuarioPorUsername(String username) {
        return usuarioRepository.buscarUsuarioPorUsername(username).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.buscarUsuarioPorEmail(email).orElse(null);
    }

    @Override
    @Transactional()
    public void guardarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional()
    public void eliminarUsuarioPorId(Long idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }

} // fin de la implementacion de los servicios
