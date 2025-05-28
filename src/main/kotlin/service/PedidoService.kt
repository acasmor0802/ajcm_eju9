package service
// Importacion de dependencias
import data.dao.ILineaPedidoDao
import data.dao.IPedidoDao
import data.dao.LineaPedidoDao
import data.dao.PedidoDao
import model.Pedido

/**
 * Servicio para manejar operaciones relacionadas con pedidos.
 */
class PedidoService(private val pedidoDao: IPedidoDao, private val lineaDao: ILineaPedidoDao) : IPedidoService {

    /**
     * Crea un nuevo pedido para el usuario indicado con el precio total dado.
     */
    override fun crear(idUsuario: Int, precioTotal: Double) {
        pedidoDao.insertar(Pedido(precioTotal = precioTotal, idUsuario = idUsuario))
    }

    /**
     * Elimina todas las líneas de pedido asociadas al pedido y luego el pedido mismo.
     * Lanza IllegalArgumentException si no existe ningún pedido con ese id.
     */
    override fun eliminarConLineas(idPedido: Int) {
        lineaDao.eliminarPorPedido(idPedido)

        // Borrar el pedido y chequear cuántos se eliminaron
        val pedidosBorrados = pedidoDao.eliminarPorId(idPedido)
        if (pedidosBorrados == 0) {
            throw IllegalArgumentException("No existe ningún pedido con id=$idPedido")
        }
    }

    override fun obtenerTotalGastadoPor(nombreUsuario: String): Double {
        return pedidoDao.obtenerTotalGastadoPorUsuario(nombreUsuario)
    }
}
