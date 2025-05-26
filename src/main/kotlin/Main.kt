import java.sql.SQLException

fun main() {
    try {
        Database.getConnection()
        println("Se ha establecido conexión con la bbdd")
    }catch (e: SQLException) {
        println("Error al conecctarse con la bbdd: ${e.message}")
    }
}