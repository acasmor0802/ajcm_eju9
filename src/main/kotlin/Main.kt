import data.Database
import service.*
import java.sql.Connection
import java.sql.SQLException

fun main() {
    try {
        Database.getConnection().use { connection ->

            try {
                connection.autoCommit = false

                val usuarioService: IUsuarioService = UsuarioService()
                val productoService: IProductoService = ProductoService()
                val pedidoService: IPedidoService = PedidoService()
                val lineaPedidoService: ILineaPedidoService = LineaPedidoService()
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

                // Ejercicio 4: Eliminaciones con manejo de errores
                println("=== Ejercicio 4: Eliminaciones ===")

                // 1. Eliminar usuario "Cornelio Ramírez"

                usuarioService.eliminarPorNombre("Cornelio Ramírez")
                println("Usuario 'Cornelio Ramírez' eliminado.")

                // 2. Eliminar producto con precio 24.99
                val eliminadosProd = productoService.eliminarPorPrecio(25.0)
                if (eliminadosProd > 0) {
                    println("Producto(s) con precio 24.99 eliminado(s): $eliminadosProd")
                } else {
                    println("No se encontró ningún producto con precio 24.99.")
                }

                // 3. Eliminar pedido id=3 (primero sus líneas)

                pedidoService.eliminarConLineas(3)
                println("Pedido id=3 y sus líneas eliminados.")

                connection.commit()

            } catch (e: Exception) {
                try {
                    connection.rollback()
                    println("Error: ${e.message}")
                } catch (e: Exception) {
                    println("Error durante rollback")
                    println("Error original: ${e.message}")
                }
            }
        }
    } catch (e: SQLException) {
        println("Error: ${e.message}")
    }
}
