import data.Database
import service.*
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

                // --- Aquí inicia la parte del nuevo ejercicio ---

                // 1. Poner producto "Abanico" (id=2) en oferta con precio 120.0
                productoService.actualizarPrecioProducto(idProducto = 2, nuevoPrecio = 120.0)
                println("Precio de producto 'Abanico' actualizado a 120.0 € (oferta)")

                // 2. Actualizar línea de pedido con id=3:
                // Cambiar producto a "Abanico" (id=2)
                // Cambiar precio al doble del precio actualizado de Abanico (2 * 120 = 240)
                lineaPedidoService.actualizarLineaPedido(idLinea = 3, idProducto = 2, nuevoPrecio = 240.0)
                println("Línea de pedido id=3 actualizada: producto cambiado a 'Abanico' y precio a 240.0 €")


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
