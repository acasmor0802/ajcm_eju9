package service
// Importacion de dependencias
import data.dao.LineaPedidoDao
import model.LineaPedido
import java.sql.Connection

// Servicio para manejar operaciones relacionadas con líneas de pedido.
class LineaPedidoService() {
    private val dao = LineaPedidoDao()
    // Crea una nueva línea de pedido con el ID del pedido, ID del producto, cantidad y precio especificados.
    // Esta función construye un objeto y lo envía a DAO para insertarlo en la base de datos.
     fun crear(idPedido: Int, idProducto: Int, cantidad: Int, precio: Double) {
        dao.insertar(LineaPedido(cantidad = cantidad, precio = precio, idPedido = idPedido, idProducto = idProducto))
    }
    // Obtiene las líneas de pedido asociadas a un pedido específico.
    fun obtenerLineasDePedido(idPedido: Int): List<LineaPedido> {
        return dao.obtenerPorPedido(idPedido)
    }
}
