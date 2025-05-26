package service
// Importacion de dependencias
import data.dao.UsuarioDao
import model.Usuario
import java.sql.Connection
// Crea un nuevo usuario con el nombre y correo electrónico especificados.
class UsuarioService() {
    private val dao = UsuarioDao()
    // Esta función construye un objeto y lo envía a DAO para insertarlo en la base de datos.
    fun crear(nombre: String, email: String) {
        dao.insertar(Usuario(nombre = nombre, email = email))
    }
}