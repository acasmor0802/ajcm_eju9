package data.dao
// Importacion de dependencias
import model.Producto
import java.sql.SQLException
import javax.sql.DataSource

class ProductoDao(private val dataSource: DataSource) : IProductoDao {
    // Esta función prepara y ejecuta un SQL con los datos del objeto.
    override fun insertar(producto: Producto) {
        try {
            dataSource.connection.use { connection ->
                val sql = "INSERT INTO Producto (nombre, precio, stock) VALUES (?, ?, ?)"
                connection.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, producto.nombre)
                    stmt.setDouble(2, producto.precio)
                    stmt.setInt(3, producto.stock)
                    stmt.executeUpdate()
                }
            }
        } catch (ex: SQLException) {
            throw SQLException("Error al insertar Producto")
        }
    }

    /**
     * Elimina productos cuyo precio coincida.
     * @return número de filas afectadas.
     */

    override fun eliminarPorPrecio(precio: Double?): Int {
        if (precio == null) throw IllegalArgumentException("El precio no puede ser nulo")
        try {
            dataSource.connection.use { connection ->
                val sql = "DELETE FROM Producto WHERE precio = ?"
                connection.prepareStatement(sql).use { stmt ->
                    stmt.setDouble(1, precio)
                    return stmt.executeUpdate()
                }
            }
        } catch (ex: SQLException) {
            throw SQLException("Error al eliminar Producto")
        }
    }


    /**
     * Actualiza el precio de un producto dado su id.
     * @param idProducto Identificador del producto a modificar.
     * @param nuevoPrecio Nuevo valor para el precio.
     * @return número de filas afectadas (debería ser 1 si el producto existe).
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    override fun actualizarPrecio(idProducto: Int, nuevoPrecio: Double): Int {
        try {
            dataSource.connection.use { connection ->
                val sql = "UPDATE Producto SET precio = ? WHERE id = ?"
                connection.prepareStatement(sql).use { stmt ->
                    stmt.setDouble(1, nuevoPrecio)
                    stmt.setInt(2, idProducto)
                    return stmt.executeUpdate()
                }
            }
        } catch (ex: SQLException) {
            throw SQLException("Error al actualizar precio de Producto")
        }
    }
}
