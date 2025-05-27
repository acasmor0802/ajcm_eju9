package service
// Importacion de dependencias
import data.dao.UsuarioDao
import model.Usuario
import java.sql.Connection

class UsuarioService() {
    // Instancia del DAO de Usuario para acceder a la base de datos.
    private val daoUsuario = UsuarioDao()
    // Crea un nuevo usuario con el nombre y correo electrónico proporcionados.
     fun crear(nombre: String, email: String) {
        val usuario = Usuario(nombre = nombre, email = email)
        daoUsuario.insertar(usuario)
    }
    // Obtiene una lista de nombres de usuarios que han comprado un producto específico.
     fun obtenerUsuariosQueCompraron(producto: String): List<String> {
        return daoUsuario.obtenerUsuariosQueCompraronProducto(producto)
    }
}
