package data.dao
// Importacion de dependencias
import data.Database
import model.Producto
import java.sql.SQLException

class ProductoDao() {
    // Esta función prepara y ejecuta un SQL con los datos del objeto.
    fun insertar(producto: Producto) {
        try {
            Database.getConnection().use { conn ->
                val sql = "INSERT INTO Producto (nombre, precio, stock) VALUES (?, ?, ?)"
                conn.prepareStatement(sql).use { stmt ->
                    stmt.setString(1, producto.nombre)
                    stmt.setDouble(2, producto.precio)
                    stmt.setInt(3, producto.stock)
                    stmt.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            throw SQLException("Error al insertar producto.")
        }
    }
}
