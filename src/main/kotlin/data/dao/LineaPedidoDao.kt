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

    /**
     * Actualiza una línea de pedido según su id, cambiando el producto y el precio.
     * @param idLinea Id de la línea de pedido a actualizar.
     * @param idProducto Nuevo id de producto para la línea.
     * @param nuevoPrecio Nuevo precio para la línea.
     * @return número de filas afectadas (debería ser 1 si existe la línea).
     * @throws SQLException Si ocurre un error de base de datos.
     */
    override fun actualizarLineaPorId(idLinea: Int, idProducto: Int, nuevoPrecio: Double): Int {
        try {
            Database.getConnection().use { connection ->
                val sql = "UPDATE LineaPedido SET idProducto = ?, precio = ? WHERE id = ?"
                connection.prepareStatement(sql).use { stmt ->
                    stmt.setInt(1, idProducto)
                    stmt.setDouble(2, nuevoPrecio)
                    stmt.setInt(3, idLinea)
                    return stmt.executeUpdate()
                }
            }
        } catch (ex: SQLException) {
            throw SQLException("Error al actualizar LineaPedido")
        }
    }

    override fun obtenerPorPedido(idPedido: Int): List<LineaPedido> {
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
