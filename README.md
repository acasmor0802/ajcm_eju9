# EJERCICIOS U9

## Enunciado original

### Instalación, configuración y conexión con H2

- Instala la base de datos H2 en tu entorno de desarrollo.
- Configura la base de datos para que funcione en modo fichero.
- Cambia el PATH de la base de datos. Usa por ejemplo: `./data/tienda`.
- Crea un programa en Kotlin para establecer conexión con H2, asegurando el uso de try-catch para manejar errores correctamente.


## Resolución del ejercicio:
### Para hacer este ejercicio hice:

 - Implementar en `build.gradle.kts` las dependencias de H2.
 - Crear una bbdd dentro de este proyecto.
 - Crear un objeto que retorne una instancia de la bbdd.
 - En el main hice un try catch por si el objeto daba algún error al dar la instancia de la bbdd.

### Main
En el main, se hace un try catch por si ocurre algun problema al recibir la conexión.

```
fun main() {
    try {
        Database.getConnection()
        println("Se ha establecido conexión con la bbdd")
    }catch (e: SQLException) {
        println("Error al conecctarse con la bbdd: ${e.message}")
    }
}
```

### Database
En Database se crea la instancia de la bbdd

```
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
```
