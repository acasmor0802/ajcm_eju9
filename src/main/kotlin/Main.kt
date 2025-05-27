// Hago una importacion de dependencias
import data.Database
import service.*
import java.sql.Connection

/**
 * Función principal que inicializa la base de datos y realiza operaciones CRUD de ejemplo.
 *
 * El programa realiza las siguientes operaciones:
 * 1. Establece una conexión con la base de datos
 * 2. Configura la transaccionalidad (autoCommit = false)
 * 3. Crea instancias de los servicios necesarios
 * 4. Ejecuta operaciones de inserción de datos de prueba
 * 5. Maneja posibles errores con rollback
 *
 * @throws Exception Si ocurre algún error durante la ejecución de las operaciones
 */

fun main() {
    try {
        Database.getConnection().use { conn: Connection? ->
            conn?.autoCommit = false

            // Aquí se llama a AppDatabase para abrir una conexion
            try {
                val usuarioService = UsuarioService()
                val productoService = ProductoService()
                val pedidoService = PedidoService()
                val lineaPedidoService = LineaPedidoService()

                // Usuarios
                usuarioService.crear("Facundo Pérez", "facuper@mail.com")
                usuarioService.crear("Ataulfo Rodríguez", "ataurod@mail.com")
                usuarioService.crear("Cornelio Ramírez", "Cornram@mail.com")

                // Productos
                productoService.crear("Ventilador", 10.0, 2)
                productoService.crear("Abanico", 150.0, 47)
                productoService.crear("Estufa", 24.99, 1)

                // Pedidos
                pedidoService.crear(2, 160.0)
                pedidoService.crear(1, 20.0)
                pedidoService.crear(2, 150.0)

                // Líneas de Pedido
                lineaPedidoService.crear(1, 1, 1, 10.0)
                lineaPedidoService.crear(1, 2, 1, 150.0)
                lineaPedidoService.crear(2, 1, 2, 20.0)
                lineaPedidoService.crear(3, 2, 1, 150.0)

                conn?.commit()
                println("Datos insertados correctamente.")

            } catch (e: Exception) {
                try {
                    conn?.rollback()
                } catch (e: Exception) {
                    println("Error en el Rollback")
                }
                println("Error en la ejecución: ${e.message}")
            }
        }
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}