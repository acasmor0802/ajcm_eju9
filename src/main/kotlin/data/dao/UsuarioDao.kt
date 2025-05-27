package data.dao
// Importacion de dependencias
import data.Database
import model.Usuario
import java.sql.Connection
import java.sql.SQLException
import kotlin.io.use

class UsuarioDao() : IUsuarioDao {
    // Esta función prepara y ejecuta un SQL con los datos del objeto.
    override fun insertar(usuario: Usuario) {
        try {
            Database.getConnection().use { connection ->
                val sql = "INSERT INTO Usuario (nombre, email) VALUES (?, ?)"
                connection.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, usuario.nombre)
                    stmt.setString(2, usuario.email)
                    stmt.executeUpdate()
                }
            }
        } catch (ex: SQLException) {
            throw SQLException("Error al insertar usuario")
        }
    }

    /**
     * Elimina usuarios cuyo nombre coincida.
     * @return número de filas afectadas.
     */
    override fun eliminarPorNombre(nombre: String): Int {
        try {
            Database.getConnection().use { connection ->
                val sql = "DELETE FROM Usuario WHERE nombre = ?"
                connection.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, nombre)
                    return stmt.executeUpdate()
                }
            }
        } catch (ex: SQLException) {
            throw SQLException("Error al eliminar Usuario")
        }
    }
}