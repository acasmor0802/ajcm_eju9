package data.dao
// Importacion de dependencias
import data.Database
import model.Usuario
import java.sql.Connection
import java.sql.SQLException
import javax.sql.DataSource
import kotlin.io.use

class UsuarioDao(private val dataSource: DataSource) : IUsuarioDao {
    // Esta función prepara y ejecuta un SQL con los datos del objeto.
    override fun insertar(usuario: Usuario) {
        try {
            dataSource.connection.use { connection ->
                val sql = "INSERT INTO Usuario (nombre) VALUES (?)"
                connection.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, usuario.nombre)
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
            dataSource.connection.use { connection ->
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

    /* Obtiene una lista de nombres de usuarios que han comprado un producto específico.
   Realiza una consulta SQL que une las tablas Usuario, Pedido, LineaPedido y Producto,
   filtrando por el nombre del producto proporcionado, para devolver los usuarios que lo compraron.*/
    override fun obtenerUsuariosQueCompraronProducto(nombreProducto: String): List<String> {
        try {
            dataSource.connection.use { connection ->
                val lista = mutableListOf<String>()
                val sql = """
            SELECT DISTINCT u.nombre
            FROM Usuario u
            JOIN Pedido p ON u.id = p.idUsuario
            JOIN LineaPedido lp ON p.id = lp.idPedido
            JOIN Producto pr ON lp.idProducto = pr.id
            WHERE pr.nombre = ?
        """.trimIndent()

                connection.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, nombreProducto)
                    stmt.executeQuery().use { rs ->
                        while (rs.next()) {
                            lista.add(rs.getString("nombre"))
                        }
                    }
                }
                return lista
            }
        } catch (e: SQLException) {
            throw SQLException("Error al obtenerUsuariosQueCompraronProducto")
        }
    }
}