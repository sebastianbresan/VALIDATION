package validation.service;

import validation.entity.Usuario;
import validation.repository.UsuarioRepository;

import java.util.List;

/**
 * Permite envolver los metodos del la interfaz del repositorio que extiende
 * de {@link org.springframework.data.jpa.repository.JpaRepository} renombrando
 * estos metodos a unos mas comodos, ademas de no trabajar directamente sobre el
 * repositorio.
 */
public interface IUsuarioService {

    /**
     * Envuelve al metodo <b>findAll</b> de <b>JpaRepository</b>
     * el cual devuelve todos los registros de la tabla <b>usuarios</b>
     * @return List(Usuario)
     */
    List<Usuario> buscarTodosUsuarios();

    /**
     * Envuelve al metodo <b>findById</b> de <b>JpaRepository</b>
     * el cual retorna el registro buscado por su id.
     * @param idUsuario
     * @return Usuario
     */
    Usuario buscarUsuarioPorId(Long idUsuario);

    /**
     * Envuelve el metodo de la consulta personalizada en el repositorio {@link UsuarioRepository}
     * <b>buscarUsuarioPorUsername</b> que retorna un registro de la BD por su username.
     * @param username
     * @return Usuario
     */
    Usuario buscarUsuarioPorUsername(String username);

    /**
     * Envuelve el metodo de la consulta personalizada en el repositorio {@link UsuarioRepository}
     * <b>buscarUsuarioPorEmail</b> que retorna un registro de la BD por su email.
     * @param email
     * @return Usuario
     */
    Usuario buscarUsuarioPorEmail(String email);

    /**
     * Envuelve al metodo <b>save</b> de <b>JpaRepository</b>
     * que guarda en la BD al usuario que se pasa atraves del su parametro
     * @param usuario
     */
    void guardarUsuario(Usuario usuario);

    /**
     * Envuelve al metodo <b>deleteById</b> de <b>JpaRepository</b>
     * este elimina un registro de la BD por medio de su id.
     * @param idUsuario
     */
    void eliminarUsuarioPorId(Long idUsuario);

} // fin de la interface de servicio
