package data.dao
// Importacion de dependencias
import data.Database
import model.Pedido
import java.sql.SQLException

class PedidoDao() {
    // Esta función prepara y ejecuta un SQL con los datos del objeto.
    fun insertar(pedido: Pedido) {
        try {
            Database.getConnection().use { conn ->
                val sql = "INSERT INTO Pedido (precioTotal, idUsuario) VALUES (?, ?)"
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setDouble(1, pedido.precioTotal)
                    stmt.setInt(2, pedido.idUsuario)
                    stmt.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            throw SQLException("Error al insertar el pedido")
        }
    }
}
