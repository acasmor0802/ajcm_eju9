package data.dao
// Importacion de dependencias
import data.Database
import model.LineaPedido
import java.sql.SQLException

class LineaPedidoDao() : ILineaPedidoDao {
    // Esta función prepara y ejecuta un SQL con los datos del objeto.
    override fun insertar(lp: LineaPedido) {
        try {
            Database.getConnection().use { connection ->
                val sql = "INSERT INTO LineaPedido (cantidad, precio, idPedido, idProducto) VALUES (?, ?, ?, ?)"
                connection.prepareStatement(sql).use { stmt ->
                    stmt.setInt(1, lp.cantidad)
                    stmt.setDouble(2, lp.precio)
                    stmt.setInt(3, lp.idPedido)
                    stmt.setInt(4, lp.idProducto)
                    stmt.executeUpdate()
                }
            }
        }  catch (e: SQLException) {
            throw SQLException("Error insertar LineaPedido")
        }
    }
    /**
     * Elimina todas las líneas de pedido asociadas a un pedido dado.
     * @return número de filas afectadas.
     */
    override fun eliminarPorPedido(idPedido: Int): Int {
        try {
            Database.getConnection().use { connection ->
                val sql = "DELETE FROM LineaPedido WHERE idPedido = ?"
                connection.prepareStatement(sql).use { stmt ->
                    stmt.setInt(1, idPedido)
                    return stmt.executeUpdate()
                }
            }
        } catch (ex: SQLException) {
            throw SQLException("Error eliminar LineaPedido")
        }
    }
}
