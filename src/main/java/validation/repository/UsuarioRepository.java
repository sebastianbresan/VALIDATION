package validation.repository;

import org.springframework.stereotype.Repository;
import validation.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Este repositorio extiende de {@link JpaRepository} que permite usar los metodos
 * para las operaciones basicas de un CRUD que se haran hacia la tabla de <b>usuarios</b>.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername (String username);
    boolean existsByEmail (String email);

    /**
     * Genera una consulta personalizada que permita obtener el registro de un
     * <b>usuario</b> atraves de su username.
     * @param username
     * @return Optional (usuario) o null
     */
    @Query("SELECT u FROM Usuario u WHERE u.username = ?1")
    Optional<Usuario> buscarUsuarioPorUsername(String username);

    /**
     * Genera una consulta personalizada que permita obtener el registro de un
     * <b>usuario</b> atraves de su email.
     * @param email
     * @return Optional
     */
    @Query("SELECT u FROM Usuario u WHERE u.email = ?1")
    Optional<Usuario> buscarUsuarioPorEmail(String email);

} // fin de la clase
