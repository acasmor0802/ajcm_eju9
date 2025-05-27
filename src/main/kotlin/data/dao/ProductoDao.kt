package data.dao
// Importacion de dependencias
import data.Database
import model.Producto
import java.sql.Connection
import java.sql.SQLException
import kotlin.io.use

class ProductoDao() : IProductoDao {
    // Esta función prepara y ejecuta un SQL con los datos del objeto.
    override fun insertar(producto: Producto) {
        try {
            Database.getConnection().use { connection ->
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

    override fun eliminarPorPrecio(precio: Double): Int {
        try {
            Database.getConnection().use { connection ->
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
}