package data.dao
// Importacion de dependencias
import data.Database
import model.LineaPedido
import java.sql.SQLException

class LineaPedidoDao() {
    // Esta función prepara y ejecuta un SQL con los datos del objeto.
    fun insertar(lp: LineaPedido) {
        try{
            Database.getConnection().use { conn ->
                val sql = "INSERT INTO LineaPedido (cantidad, precio, idPedido, idProducto) VALUES (?, ?, ?, ?)"
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setInt(1, lp.cantidad)
                    stmt.setDouble(2, lp.precio)
                    stmt.setInt(3, lp.idPedido)
                    stmt.setInt(4, lp.idProducto)
                    stmt.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            throw SQLException("Error al insertar Linea de pedido")
        }
    }
}