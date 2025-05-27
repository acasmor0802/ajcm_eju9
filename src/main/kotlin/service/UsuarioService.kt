package service
// Importacion de dependencias
import data.dao.IUsuarioDao
import data.dao.UsuarioDao
import model.Usuario
import java.sql.Connection
// Crea un nuevo usuario con el nombre y correo electrónico especificados.
class UsuarioService() : IUsuarioService {
    private val dao: IUsuarioDao = UsuarioDao()
    // Esta función construye un objeto y lo envía a DAO para insertarlo en la base de datos.
    override fun crear(nombre: String, email: String) {
        dao.insertar(Usuario(nombre = nombre, email = email))
    }

    /**
     * Elimina un usuario por nombre.
     * @throws IllegalArgumentException si no existe ningún usuario con ese nombre.
     */
    override fun eliminarPorNombre(nombre: String) {
        val filas = dao.eliminarPorNombre(nombre)
        if (filas == 0) {
            throw IllegalArgumentException("No existe ningún usuario con nombre \"$nombre\"")
        }
    }
}
