package data.dao
// Importacion de dependencias
import data.Database
import model.Usuario
import java.sql.SQLException

class UsuarioDao() {
    // Esta función prepara y ejecuta un SQL con los datos del objeto.
    fun insertar(usuario: Usuario) {
        try {
            Database.getConnection().use { conn ->
                val sql = "INSERT INTO Usuario (nombre, email) VALUES (?, ?)"
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, usuario.nombre)
                    stmt.setString(2, usuario.email)
                    stmt.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            throw SQLException("Error al insertar usuario.")
        }
    }
}
