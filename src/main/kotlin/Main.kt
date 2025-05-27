// Hago una importacion de dependencias

import data.Database
import service.*
import java.sql.Connection


fun main() {
    try {
        Database.getConnection().use { conn: Connection? ->
            conn?.autoCommit = false
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

                    println("Datos insertados.")

                    // Ejercicio 3: Consultas
                    println("--- Líneas de pedido con ID 1 ---")
                    // Imprime las líneas de pedido correspondientes al pedido con ID 1.
                    // Devuelve una lista de líneas de pedido para el pedido especificado.
                    lineaPedidoService.obtenerLineasDePedido(1).forEach { println(it) }

                    println("--- Total gastado por Ataulfo Rodríguez ---")
                    // Calcula e imprime el total gastado por el usuario "Ataulfo Rodríguez".
                    // devuelve la suma total de los importes de los pedidos realizados por ese usuario.
                    val total = pedidoService.obtenerTotalGastadoPor("Ataulfo Rodríguez")
                    println("Total: $total €")

                    println("--- Usuarios que compraron un Abanico ---")
                    // Muestra los usuarios que han comprado un producto llamado "Abanico".
                    // Una lista de usuarios que realizaron compras del abanico.
                    usuarioService.obtenerUsuariosQueCompraron("Abanico").forEach { println(it) }

                    conn?.commit()
                    // Muestra un error si hay algun problema
                } catch (e: Exception) {
                    try{
                        println("Error en: ${e.message}")
                        conn?.rollback()
                    }catch (e: Exception) {
                        println("Error en rollback")
                    }
                }
        }
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
}
