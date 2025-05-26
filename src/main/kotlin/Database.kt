import java.sql.Connection
import java.sql.DriverManager

object Database {
    // constantes con los datos de la bbdd.
    private const val URL = "jdbc:h2:./db/eju9"
    private const val USER = "sa"
    private const val PASS = ""

    /**
     * Función para conectarse a la base de datos.
     * Para hacer la conexión a la bbdd, esta función utiliza el USER y el PASS.
     * Si ocurre un error, lanzaría un error tipo SQLException.
     * @return una instancia de Connection.
     */
    fun getConnection(): Connection {
        return DriverManager.getConnection(URL, USER, PASS)
    }
}