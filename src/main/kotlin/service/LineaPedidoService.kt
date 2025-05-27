package service
// Importacion de dependencias
import data.dao.ILineaPedidoDao
import data.dao.LineaPedidoDao
import model.LineaPedido

// Servicio para manejar operaciones relacionadas con líneas de pedido.
class LineaPedidoService() : ILineaPedidoService {
    private val dao: ILineaPedidoDao = LineaPedidoDao()

    // Crea una nueva línea de pedido con el ID del pedido, ID del producto, cantidad y precio especificados.
    // Esta función construye un objeto y lo envía a DAO para insertarlo en la base de datos.
    override fun crear(idPedido: Int, idProducto: Int, cantidad: Int, precio: Double) {
        dao.insertar(LineaPedido(cantidad = cantidad, precio = precio, idPedido = idPedido, idProducto = idProducto))
    }

    /**
     * Actualiza la línea de pedido con id dado, para cambiar producto y precio.
     * @param idLinea Id de la línea a modificar.
     * @param idProducto Nuevo id de producto para la línea.
     * @param nuevoPrecio Nuevo precio a asignar.
     */
    override fun actualizarLineaPedido(idLinea: Int, idProducto: Int, nuevoPrecio: Double) {
        dao.actualizarLineaPorId(idLinea, idProducto, nuevoPrecio)
    }
}
