package app

import service.ILineaPedidoService
import service.IPedidoService
import service.IProductoService
import service.IUsuarioService
import ui.IEntradaSalida

import javax.sql.DataSource

class Controlador(
    private val servicioLinea: ILineaPedidoService,
    private val servicioPedido: IPedidoService,
    private val servicioProducto: IProductoService,
    private val servicioUsuario: IUsuarioService,
    private val consola: IEntradaSalida,
    private val dataSource: DataSource
) {
    private var curso = true // Controla si la aplicación sigue ejecutándose
    /**
     * Inicia el ciclo de vida de la aplicación.
     * Presenta el menú principal y ejecuta las opciones seleccionadas por el usuario.
     */
    fun iniciar() {

        while (curso) {
            consola.limpiar() // Limpia la pantalla
            consola.mostrar(
                """
                === MENÚ ===
                1. Crear usuarios
                2. Crear pedidos
                3. Crear Productos
                4. Crear Lineas
                5. Actualizar lineas
                6. Obtener lineas
                7. Obtener total gastado
                8. Eliminar Usuario
                9. obtener usuarios que compraron un abanico
                10. Eliminar por precio
                11. Salir
                """.trimIndent()
            )

            // Procesa la opción seleccionada por el usuario
            when (consola.leer("Opción: ")) {
                "1" -> crearUsuarios()
                "2" -> crearPedidos()
                "3" -> crearProductos()
                "4" -> crearLineas()
                "5" -> actualizarLineas()
                "6" -> obtenerLineas()
                "7" -> obtenerTotalGastadoPor()
                "8" -> eliminarNombre()
                "9" -> obtenerUsuariosCompraron()
                "10" -> eliminarxPrecio()
                "11" -> salir()
                else -> consola.mostrarError("Opción no válida.")
            }

            consola.pausar() // Pausa para que el usuario pueda ver el resultado
        }
    }
    private fun crearUsuarios(){
        try {
            servicioUsuario.crear("Facundo Pérez", "facuper@mail.com")
            servicioUsuario.crear("Ataulfo Rodríguez", "ataurod@mail.com")
            servicioUsuario.crear("Cornelio Ramírez", "Cornram@mail.com")
            servicioUsuario.crear("Reinaldo Girúndez", "reingir@mail.com")
        } catch (e: Exception){
            consola.mostrarError("Error al crear usuario:${e.message}")
        }
    }

    private fun crearPedidos(){
        try {
            servicioPedido.crear(2, 160.0)
            servicioPedido.crear(1, 20.0)
            servicioPedido.crear(2, 150.0)
        } catch (e: Exception){
        consola.mostrarError("Error al crear pedidos:${e.message}")
    }
    }

    private fun crearLineas(){
        try {
            servicioLinea.crear(1, 1, 1, 10.0)
            servicioLinea.crear(1, 2, 1, 150.0)
            servicioLinea.crear(2, 1, 2, 20.0)
            servicioLinea.crear(3, 2, 1, 150.0)
        } catch (e: Exception){
            consola.mostrarError("Error al crear lineas:${e.message}")
        }
    }

    private fun crearProductos(){
        try {
            servicioProducto.crear("Ventilador", 10.0, 2)
            servicioProducto.crear("Abanico", 150.0, 47)
            servicioProducto.crear("Estufa", 24.99, 1)
        } catch (e: Exception){
            consola.mostrarError("Error al crear productos:${e.message}")
        }
    }

    private fun actualizarLineas(){
        try {
            val idLinea = consola.leer("Id de la línea a actualizar: ").toIntOrNull()
            val idProducto = consola.leer("Nuevo id de producto: ").toIntOrNull()
            val nuevoPrecio = consola.leer("Nuevo precio: ").toDoubleOrNull()

            if (idLinea == null || idProducto == null || nuevoPrecio == null) {
                consola.mostrarError("Datos inválidos para actualización")
                return
            }
            servicioLinea.actualizarLineaPedido(idLinea, idProducto, nuevoPrecio)
            consola.mostrar("Línea actualizada correctamente.")
        } catch (e: Exception) {
            consola.mostrarError("Error al actualizar línea: ${e.message}")
        }
    }

    private fun obtenerLineas() {
        val idPedido = consola.leer("Id del pedido para obtener líneas: ").toIntOrNull()
        if (idPedido == null) {
            consola.mostrarError("Id inválido")
            return
        }
        val lineas = try {
            servicioLinea.obtenerLineasDePedido(idPedido)
        } catch (e: Exception) {
            consola.mostrarError("Error al obtener líneas: ${e.message}")
            return
        }

        if (lineas.isEmpty()) {
            consola.mostrar("No se encontraron líneas para ese pedido.")
        } else {
            lineas.forEach { lp ->
                consola.mostrar("Pedido: ${lp.idPedido}, Producto: ${lp.idProducto}, Cantidad: ${lp.cantidad}, Precio: ${lp.precio}")
            }
        }
    }



    private fun obtenerTotalGastadoPor() {
        try {
            val nombreUsuario = consola.leer("Nombre de usuario para obtener total gastado: ")
            val totalGastado = servicioPedido.obtenerTotalGastadoPor(nombreUsuario)
            consola.mostrar("El total gastado por $nombreUsuario es: $totalGastado")
        } catch (e: Exception) {
            consola.mostrarError("Error al obtener total gastado por usuario: ${e.message}")
        }
    }



    private fun eliminarNombre(){
        try {
            val nombre = consola.leer("Nombre del usuario para eliminar: ")
            servicioUsuario.eliminarPorNombre(nombre)
            consola.mostrar("Nombre eliminado con exito")
        } catch (e: Exception){
            consola.mostrarError("Error al eliminar usuario:${e.message}")
        }
    }

    private fun obtenerUsuariosCompraron() {
        try {
            val nombreProducto = consola.leer("Nombre del producto: ")
            val usuarios = servicioUsuario.obtenerUsuariosQueCompraron(nombreProducto)
            if (usuarios.isEmpty()) {
                consola.mostrar("No hay usuarios que hayan comprado ese producto.")
            } else {
                consola.mostrar("Usuarios que compraron '$nombreProducto':")
                usuarios.forEach { nombre ->
                    consola.mostrar("- $nombre")
                }
            }
        } catch (e: Exception) {
            consola.mostrarError("Error al obtener usuarios que compraron: ${e.message}")
        }
    }

        private fun eliminarxPrecio(){
        try {
            val precio = consola.leer("Nuevo precio: ").toDoubleOrNull()
            if (precio != null) {
                servicioProducto.eliminarPorPrecio(precio)
                consola.mostrar("Precio actualizado.")
            }
            else {
                consola.mostrarError("Precio inválido.")
            }
        } catch (e: Exception) {
            consola.mostrarError(("Error al eliminar por precio: ${e.message}"))
        }
    }

    private fun salir(){
        consola.mostrar("Saliendo de la aplicación.")
        curso = false
    }
}