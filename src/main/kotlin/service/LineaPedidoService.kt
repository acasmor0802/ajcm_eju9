package service
// Importacion de dependencias
import data.dao.LineaPedidoDao
import model.LineaPedido
import java.sql.Connection

// Servicio para manejar operaciones relacionadas con líneas de pedido.
class LineaPedidoService() {
    val lpd = LineaPedidoDao()
    // Crea una nueva línea de pedido con el ID del pedido, ID del producto, cantidad y precio especificados.
    // Esta función construye un objeto y lo envía a DAO para insertarlo en la base de datos.
    fun crear(idPedido: Int, idProducto: Int, cantidad: Int, precio: Double) {
        lpd.insertar(LineaPedido(cantidad = cantidad, precio = precio, idPedido = idPedido, idProducto = idProducto))
    }
}
