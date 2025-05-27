package data.dao
// Importacion de dependencias
import data.Database
import model.LineaPedido
import java.sql.SQLException

class LineaPedidoDao() {
    // Inserta un nuevo linea de pedido en la base de datos.
     fun insertar(linea: LineaPedido) {
        try {
            Database.getConnection().use { connection ->
                val sql = "INSERT INTO LineaPedido (cantidad, precio, idPedido, idProducto) VALUES (?, ?, ?, ?)"
                connection.prepareStatement(sql).use { stmt ->
                    stmt.setInt(1, linea.cantidad)
                    stmt.setDouble(2, linea.precio)
                    stmt.setInt(3, linea.idPedido)
                    stmt.setInt(4, linea.idProducto)
                    stmt.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            throw SQLException("Error al insertar pedido")
        }
    }
    // Obtiene todas las líneas de pedido asociadas a un pedido dado.
     fun obtenerPorPedido(idPedido: Int): List<LineaPedido> {
        try {
            Database.getConnection().use { connection ->
                val lista = mutableListOf<LineaPedido>()
                val sql = "SELECT * FROM LineaPedido WHERE idPedido = ?"
                connection.prepareStatement(sql).use { stmt ->
                    stmt.setInt(1, idPedido)
                    stmt.executeQuery().use { rs ->
                        while (rs.next()) {
                            lista.add(
                                LineaPedido(
                                    cantidad = rs.getInt("cantidad"),
                                    precio = rs.getDouble("precio"),
                                    idPedido = rs.getInt("idPedido"),
                                    idProducto = rs.getInt("idProducto")
                                )
                            )
                        }
                    }
                }
                return lista
            }
        } catch (e: SQLException) {
            throw SQLException("Error al obtener el pedido")
        }
    }
}
