package service
// Importacion de dependencias
import data.dao.IUsuarioDao
import data.dao.UsuarioDao
import model.Usuario
import java.sql.Connection
// Crea un nuevo usuario con el nombre y correo electrónico especificados.
class UsuarioService(private val usuarioDao: IUsuarioDao) : IUsuarioService {
    // Esta función construye un objeto y lo envía a DAO para insertarlo en la base de datos.
    override fun crear(nombre: String, email: String) {
        usuarioDao.insertar(Usuario(nombre = nombre, email = email))
    }

    /**
     * Elimina un usuario por nombre.
     * @throws IllegalArgumentException si no existe ningún usuario con ese nombre.
     */
    override fun eliminarPorNombre(nombre: String) {
        val filas = usuarioDao.eliminarPorNombre(nombre)
        if (filas == 0) {
            throw IllegalArgumentException("No existe ningún usuario con nombre \"$nombre\"")
        }
    }

    // Obtiene una lista de nombres de usuarios que han comprado un producto específico.
    override fun obtenerUsuariosQueCompraron(producto: String): List<String> {
        return usuarioDao.obtenerUsuariosQueCompraronProducto(producto)
    }
}
