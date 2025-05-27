package data.dao
// Importacion de dependencias
import data.Database
import model.Pedido
import java.sql.Connection
import java.sql.SQLException

class PedidoDao() {
    // Inserta un nuevo pedido en la base de datos.
     fun insertar(pedido: Pedido) {
        try {
            Database.getConnection().use { connection ->
                val sql = "INSERT INTO Pedido (precioTotal, idUsuario) VALUES (?, ?)"
                connection.prepareStatement(sql).use { stmt ->
                    stmt.setDouble(1, pedido.precioTotal)
                    stmt.setInt(2, pedido.idUsuario)
                    stmt.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            throw SQLException("Error al insertar pedido")
        }
    }
    /* Calcula el total gastado por un usuario, usando su nombre.
        Realiza una consulta SQL que une la tabla Pedido con Usuario y suma el
        importe total de todos los pedidos realizados por el usuario especificado.*/
     fun obtenerTotalGastadoPorUsuario(nombreUsuario: String): Double {
        try {
            Database.getConnection().use { connection ->
                val sql = """
            SELECT SUM(p.precioTotal) AS total
            FROM Pedido p
            JOIN Usuario u ON p.idUsuario = u.id
            WHERE u.nombre = ?
        """.trimIndent()

                connection.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, nombreUsuario)
                    stmt.executeQuery().use { rs ->
                        if (rs.next()) {
                            return rs.getDouble("total")
                        }
                    }
                }
                return 0.0
            }
        } catch (e: SQLException) {
            throw SQLException("Error al obtenerTotalGastadoPorUsuario")
        }
    }
}
