package data
// Importacion de dependencias
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

// devuelve una conexion abierta despues de pasarle la informacion de la db
object Database {
    fun getConnection(): Connection {
        return try {
            DriverManager.getConnection("jdbc:h2:./db", "sa", "")
        } catch (e: SQLException) {
            throw SQLException("Error al conectar con la base de datos H2")
        }
    }
}